package com.wht.springcloud.fangchanwang.controller;

import com.wht.springcloud.fangchanwang.common.result.ResultMsg;
import com.wht.springcloud.fangchanwang.constants.CommonConstants;
import com.wht.springcloud.fangchanwang.model.UserModel;
import com.wht.springcloud.fangchanwang.service.AgencyService;
import com.wht.springcloud.fangchanwang.service.UserService;
import com.wht.springcloud.fangchanwang.utils.HashUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AgencyService agencyService;

    /**
     * 注册提交：1.注册验证，2.发送邮件 3.验证失败重定向到注册页面
     * 注册获取：根据usermodel对象的用户密码是否为空
     *
     * @param userModel
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/accounts/register")
    public String register(UserModel userModel, ModelMap modelMap) {
        if (StringUtils.isEmpty(userModel.getName()) && StringUtils.isEmpty(userModel.getPasswd())) {
            return "user/accounts/register";
        }

        //用户验证
        ResultMsg resultMsg = UserHelper.validate(userModel);
        if (resultMsg.isSuccess() && userService.addAccount(userModel)) {
            modelMap.put("email", userModel.getEmail());
            //返回注册成功页
            return "user/accounts/registerSubmit";
        } else {
            //  modelMap.put("errorMsg",resultMsg.getErrorMsg());
            return "redirect: user/accounts/register?" + resultMsg.asUrlParams();
        }
    }

    @RequestMapping(value = "/accounts/verify")
    public String verify(String key) {
        boolean result = userService.enable(key);
        if (result) {
            return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
        } else {
            return "redirect:/accounts/register?" + ResultMsg.errorMsg("激活失败，请确认链接是否过期").asUrlParams();
        }
    }


    //-------------------------------------登录流程------------------------------------------------

    /**
     * 登录接口
     *
     * @return
     */
    @RequestMapping(value = "/accounts/signin")
    public String signin(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String target = request.getParameter("target");
        //判断是登录页的请求还是登录提交的请求，如果是登录页的请求，username和password为空
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            request.setAttribute("target", target);
            return "/user/accounts/signin";
        }

        //如果是登陆提交请求，则要验证用户名和密码
        UserModel userModel = userService.auth(username, password);
        if (userModel == null) {
            //验证失败
            return "redirect:/account/signin?target=" + target +
                    "&username=" + username + "&" + ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
        }else {
            //验证成功,放入session
            HttpSession session = request.getSession(true);
            session.setAttribute(CommonConstants.USER_ATTRIBUTE,userModel);
            session.setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE,userModel);

            return Strings.isNotBlank(target)?"redirect:"+target:"redirect:/index";
        }

    }

    /**
     * 登出
     * @param request
     * @return
     */
    @RequestMapping(value = "/accounts/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        //注销session
        session.invalidate();

        return "redirect:/index";
    }

    //-------------------------------------------个人信息页--------------------------------------------------

    /**
     * 提供用户信息
     * 更新用户信息
     * @param request
     * @param updateUser
     * @return
     */
    @RequestMapping(value = "/accounts/profile")
    public String profile(HttpServletRequest request,UserModel updateUser){
        //根据email是否为空判断是请求页面还是请求更新
        if (StringUtils.isEmpty(updateUser.getEmail())){
            return "/user/accounts/profile";
        }
        //否则更新用户信息，只能更新姓名，电话，和关于自己，密码另外更新
        userService.update(updateUser);
        HttpSession session = request.getSession(true);
        UserModel user = userService.getUsersByquery(updateUser);
        if (user != null){
            session.setAttribute(CommonConstants.USER_ATTRIBUTE,user);
            return "redirect:/accounts/profile?"+ResultMsg.successMsg("更新成功").asUrlParams();
        }else {
            return "redirect:/accounts/profile?"+ResultMsg.errorMsg("更新失败").asUrlParams();
        }


    }


    //-----------------------------------更新密码--------------------------------------------------
    @RequestMapping(value = "/accounts/changePassword")
    public String changePassword(UserModel updateUser,HttpServletRequest request){
        if (StringUtils.isEmpty(updateUser.getEmail())){
            return "/user/accounts/profile";
        }
        String password = request.getParameter("password");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");


        ResultMsg resultMsg = UserHelper.validateResetPassword(password,newPassword,confirmPassword);
        //表示校验通过
        if (resultMsg.isSuccess()){
            updateUser.setPasswd(HashUtils.encryPwd(newPassword));
            userService.update(updateUser);
                return "redirect:/accounts/signin?"+ResultMsg.successMsg("更新密码成功").asUrlParams();
        }else {
            return "redirect:/accounts/changePassword?"+ResultMsg.errorMsg("更新密码失败").asUrlParams();
        }
    }
}
