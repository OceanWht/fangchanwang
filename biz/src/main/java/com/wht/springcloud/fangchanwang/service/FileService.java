package com.wht.springcloud.fangchanwang.service;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import javafx.scene.control.Separator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Service
public class FileService {

    @Value("${file.path}")
    private String filePath;

    /**
     * 获取上传图片的相对路径 用于存库
     * @param files
     * @return
     */
    public List<String> getImgsPath(List<MultipartFile> files){
        List<String> paths = Lists.newArrayList();

        files.forEach(file ->{
            File localFile = null;
            if (!file.isEmpty()){
                try {
                    localFile = saveLocalFile(file,filePath);
                    //截取filepath之后的路径
                    String path = StringUtils.substringAfterLast(localFile.getAbsolutePath(),filePath);
                    paths.add(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        return paths;
    }

    private File saveLocalFile(MultipartFile file, String filePath) throws IOException {
        File newFile = new File(filePath+ "/"+Instant.now().getEpochSecond()+"/"+file.getOriginalFilename());
        if (!newFile.exists()){
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        Files.write(file.getBytes(),newFile);

        return newFile;
    }


}
