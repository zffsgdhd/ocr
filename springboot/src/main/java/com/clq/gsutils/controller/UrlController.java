package com.clq.gsutils.controller;

import java.io.IOException;
    import java.io.OutputStreamWriter;
    import java.net.HttpURLConnection;
    import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
    import java.io.InputStreamReader;
    
    import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;

    import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
    
    @Controller
    public class UrlController {
        final static String CREATE_API = "http://api.t.sina.com.cn/short_url/shorten.json";
        final static String TOKEN = "75558737"; // TODO:设置Token
    
        class UrlResponse {
            private int code = 1;
    
            private String errMsg ="成功";
    
            @SerializedName("url_long")
            private String longUrl;
    
            @SerializedName("url_short")
            private String shortUrl;
    
            public int getCode() {
                return code;
            }
    
            public void setCode(int code) {
                this.code = code;
            }
    
            public String getErrMsg() {
                return errMsg;
            }
    
            public void setErrMsg(String errMsg) {
                this.errMsg = errMsg;
            }
    
            public String getLongUrl() {
                return longUrl;
            }
    
            public void setLongUrl(String longUrl) {
                this.longUrl = longUrl;
            }
    
            public String getShortUrl() {
                return shortUrl;
            }
    
            public void setShortUrl(String shortUrl) {
                this.shortUrl = shortUrl;
            }
        }

        @GetMapping("/index")  
        String index() {  
            //逻辑处理  
            return "index";  
        }
        
        @GetMapping("ocrScan")  
        String test() {  
            //逻辑处理  
            return "ocrScan";  
        }
    
    
        /**
         * 创建短网址
         *
         * @param longUrl
         *            长网址：即原网址
         * @return  成功：短网址
         *          失败：返回空字符串
         */
        @ResponseBody
        @PostMapping("toShort")
        public  UrlResponse createShortUrl(String longUrl) {
            String params = URLEncoder.encode(longUrl);
            
            BufferedReader reader = null;
            try {
                String getUrl = CREATE_API + "?source=" + TOKEN +"&url_long=" + params;
                // 创建连接
                URL url = new URL(getUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.setInstanceFollowRedirects(true);
                connection.setRequestMethod("GET"); // 设置请求方式
                connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
    
                // 发起请求
                connection.connect();
                // 读取响应
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line;
                String res = "";
                while ((line = reader.readLine()) != null) {
                    res += line;
                }
                reader.close();
                JsonParser parser = new JsonParser();
                //将JSON的String 转成一个JsonArray对象
                JsonArray jsonArray = parser.parse(res).getAsJsonArray();

                // 抽取生成短网址
                UrlResponse urlResponse = new Gson().fromJson(jsonArray.get(0), UrlResponse.class);
                
                return urlResponse; // TODO：自定义错误信息
            } catch (IOException e) {
                // TODO
                e.printStackTrace();
                UrlResponse urlResponse = new UrlResponse();
                urlResponse.setCode(-1);
                urlResponse.setErrMsg("未知异常");
                return urlResponse; // TODO：自定义错误信息
            }
        }
    
    }