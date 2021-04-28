package com.fred.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fred.entities.ArticleDetail;
import com.fred.entities.Comment;
import com.fred.entities.CommonResult;


import java.util.List;

public class JsonTools {



    public static <T> CommonResult<T> toCommonResult(JSONObject jsonObject, Class<T> classOfT){
        CommonResult toCommonResult = JSON.parseObject(jsonObject.toString(), CommonResult.class);
        if(toCommonResult.getData()==null){
            return toCommonResult;
        }
        T t;
        if (classOfT.equals(String.class)){
            t = (T) toCommonResult.getData().toString();
        }else {
            t = JSON.parseObject(toCommonResult.getData().toString(), classOfT);
        }
        return new CommonResult<T>().addData(t)
                .addCode(toCommonResult.getCode())
                .addMessage(toCommonResult.getMessage());
    }

    public static CommonResult<List<ArticleDetail>> toADListCommonResult(String jsonObjectString){
        CommonResult commonResult = JSON.parseObject(jsonObjectString, CommonResult.class);
        String listAd = JSONArray.toJSONString(commonResult.getData());
        List<ArticleDetail> articleDetailList = com.alibaba.fastjson.JSONObject.parseArray(listAd, ArticleDetail.class);
        return new CommonResult<List<ArticleDetail>>().addData(articleDetailList)
                .addCode(commonResult.getCode())
                .addMessage(commonResult.getMessage());
    }

    public static CommonResult<List<Comment>> toCommentListCommonResult(String jsonObjectString){
        CommonResult commonResult = JSON.parseObject(jsonObjectString, CommonResult.class);
        String listAd = JSONArray.toJSONString(commonResult.getData());
        List<Comment> commentList = com.alibaba.fastjson.JSONObject.parseArray(listAd, Comment.class);
        return new CommonResult<List<Comment>>().addData(commentList)
                .addCode(commonResult.getCode())
                .addMessage(commonResult.getMessage());
    }



}
