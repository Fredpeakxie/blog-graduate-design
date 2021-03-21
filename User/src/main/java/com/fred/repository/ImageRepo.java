package com.fred.repository;

import com.fred.entities.RetCode;
import com.fred.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Base64;
import java.util.Map;

/**
 * @auther fred
 * @create 2021-03-21 17:04
 */
@Repository
public class ImageRepo {
    public static final String PROJECT_URI = "moonker";
    public static final String PORTRAIT_URI = "/portrait/";


    public RetCode upload(Long userId, String bitmap){
        RandomAccessFile inOut = null;
        try {
            byte[] result = Base64.getMimeDecoder().decode(bitmap.trim());
            String userIdS = String.format("%05d", userId);
            // moonker/portrait/i00001.jpg
            inOut = new RandomAccessFile(PROJECT_URI + PORTRAIT_URI+"i"+userIdS+".jpg", "rw"); // r,rw,rws,rwd
            inOut.write(result);
        } catch (Exception e) {
            e.printStackTrace();
            return RetCode.USER_PORTRAIT_SAVE_FAIL;
        }finally {
            if(inOut != null){
                try {
                    inOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return RetCode.OK;
    }
}
