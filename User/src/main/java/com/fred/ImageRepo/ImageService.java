package com.fred.ImageRepo;

import com.fred.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther fred
 * @create 2021-03-13 12:10
 */
@Slf4j
@RestController
@RequestMapping(value = "/img/upload")
public class ImageService {

    public static final String PROJECT_URI = "E:\\android_server_test";
    public static final String IMAGE_URI = "/image/";
    public static final String BLOG_URI = "/blogs/";
    public static final String ARTICLE_PIC_URI = "/blogpic/";

    @PostMapping
    public User upload(@RequestBody Map<String,Object> map)throws Exception{
        String filePath = (String) map.get("filePath");
        String bitmap = (String)map.get("bitmap");
        log.info("filePath:"+filePath);
        log.info("bitmap:"+bitmap);


        RandomAccessFile inOut = null;
        try {
        byte[] result = Base64.getMimeDecoder().decode(bitmap.trim());
        String userId = "1";
        String userIdS = String.format("%05d", Integer.parseInt(userId));
        inOut = new RandomAccessFile(PROJECT_URI + IMAGE_URI+"i"+userIdS+".jpg", "rw"); // r,rw,rws,rwd
            inOut.write(result);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(inOut != null){
                try {
                    inOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        User user = new User();
        user.setNickname("fred");
        return user;
    }

}
