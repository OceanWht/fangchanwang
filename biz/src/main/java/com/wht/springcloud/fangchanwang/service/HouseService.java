package com.wht.springcloud.fangchanwang.service;

import com.google.common.collect.Lists;
import com.wht.springcloud.fangchanwang.mapper.CommunityModelMapper;
import com.wht.springcloud.fangchanwang.mapper.HouseModelMapper;
import com.wht.springcloud.fangchanwang.model.CommunityModel;
import com.wht.springcloud.fangchanwang.model.CommunityModelExample;
import com.wht.springcloud.fangchanwang.model.HouseModel;
import com.wht.springcloud.fangchanwang.model.HouseModelExample;
import com.wht.springcloud.fangchanwang.page.PageData;
import com.wht.springcloud.fangchanwang.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class HouseService {

    @Value("${file.prefix}")
    String prefix;

    @Autowired
    HouseModelMapper houseModelMapper;

    @Autowired
    CommunityModelMapper communityModelMapper;

    /**
     * 查询小区
     * 添加图片服务器前缀
     * 构建分页结果
     *
     * @param query
     * @param build
     */
    public PageData<HouseModel> queryHouse(HouseModel query, PageParams build) {
        List<HouseModel> houseModelList = Lists.newArrayList();
        if (!StringUtils.isEmpty(query.getName())) {
            //如果
            CommunityModelExample communityModelExample = new CommunityModelExample();
            CommunityModelExample.Criteria criteria = communityModelExample.createCriteria();
            criteria.andNameEqualTo(query.getName());
            List<CommunityModel> communityModels = communityModelMapper.selectByExample(communityModelExample);
            if (!CollectionUtils.isEmpty(communityModels)) {
                query.setCommunityId(communityModels.get(0).getId());
            }
        }
        //将查询逻辑包含进来，将返回的结果设置图片路径，需要加上nginx前缀
        houseModelList = queryAndSetImg(query, build);
        int count = houseModelMapper.countByExample(new HouseModelExample());
        return PageData.buildPage(houseModelList, build.getPageSize(), build.getPageNum(), count);
    }

    private List<HouseModel> queryAndSetImg(HouseModel query, PageParams build) {

        List<HouseModel> list = houseModelMapper.selectPageHouses(query, build);
        list.forEach(v -> {
            v.setImages(prefix + query.getImages());
            v.setImgList(v.getImgList().stream().map(img -> prefix + img).collect(Collectors.toList()));
            v.setFloorPlanList(v.getFloorPlanList().stream().map(pic -> prefix + pic).collect(Collectors.toList()));
        });

        return list;
    }
}
