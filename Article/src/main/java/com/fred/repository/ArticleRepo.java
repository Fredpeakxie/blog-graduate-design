package com.fred.repository;

import com.fred.entities.Article;
import com.fred.entities.CommonResult;
import com.fred.entities.RetCode;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

/**
 * @auther fred
 * @create 2021-03-15 21:34
 */
@Repository
@Slf4j
public class ArticleRepo {

    public static final String PROJECT_URI = "moonker";
    public static final String BLOG_URI = "/article/";
    public static final String ARTICLE_PIC = "/pic";

    public CommonResult<Long> saveArticle(Article article,CommonResult<Long> commonResult){
        //0获取存储的位置
        String articleIdS = String.format("%05d", article.getArticleId());
        //1解析html 使pic匹配 html中的image(具体参见原有android代码和server代码)使用jsoup
        log.info("articleContent: " + article.getArticleContent());
        Document doc = Jsoup.parse(article.getArticleContent());
        Elements imgs = doc.getElementsByTag("img");
        List<String> pics = article.getPics();
        //2将图片解析上传到指定位置
        boolean picStatus = savePics(imgs,articleIdS,pics);
        if (picStatus){
            commonResult.addMessage("图片保存成功").addCode(RetCode.OK);
        }else {
            return commonResult.addMessage("图片保存失败").addCode(RetCode.ARTICLE_PIC_SAVE_FAIL);
        }
        String newHtml = doc.toString();
        //3将.html上传到指定位置
        boolean htmlStatus = saveHtml(articleIdS, newHtml);
        if (htmlStatus){
            commonResult.addMessage(commonResult.getMessage()+",文本保存成功").addCode(RetCode.OK);
        }else {
            return commonResult.addMessage(commonResult.getMessage()+",文本保存失败").addCode(RetCode.ARTICLE_HTML_SAVE_FAIL);
        }
        return commonResult;
    }

    public boolean savePics(Elements imgs,String articleIdS,List<String> pics){
        for (int i = 0; i < imgs.size(); i++){
//                        ../pic/boooXXpoX.jpg
            String picS = String.format("%02d", i+1);
            String src = ARTICLE_PIC+"/b"+articleIdS+"p"+picS+".jpg";
            imgs.get(i).attr("src",".."+src);
            boolean ok = savePic(src, pics.get(i));
            if (!ok){
                return false;
            }
        }
        return true;
    }

    private boolean savePic(String src, String pic) {
        RandomAccessFile inOut = null;
        try {
            byte[] result = Base64.getMimeDecoder().decode(pic.trim());
            inOut = new RandomAccessFile(PROJECT_URI +src, "rw"); // r,rw,rws,rwd
            inOut.write(result);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            if(inOut != null){
                try {
                    inOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private boolean saveHtml(String articleIdS, String html) {
        String path = PROJECT_URI + BLOG_URI + "mb" + articleIdS + ".html";
//        String path = "mb" + articleIdS + ".html";
        log.info("path : "+path);
        RandomAccessFile inOut = null;
        try {
            inOut = new RandomAccessFile(path,"rw");
            byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
            inOut.write(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if(inOut != null){
                try {
                    inOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

}
