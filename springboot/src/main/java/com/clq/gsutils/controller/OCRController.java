package com.clq.gsutils.controller;

import com.baidu.aip.ocr.AipOcr;
import com.clq.gsutils.entity.OcrEntity;
import com.clq.gsutils.utils.Base64Util;
import com.clq.gsutils.utils.FileUtil;
import com.clq.gsutils.utils.HttpUtil;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
/**
 * 文字识别OCR模块控制器
 */
@RestController
public class OCRController {
	//接口申请免费，请自行申请使用，如果学习使用可以用下
    public static final String APP_ID = "15742445";
    public static final String API_KEY = "LXrztEOzQxfef66DLIDQYpIG";
    public static final String SECRET_KEY = "gbDodnochc8jYjlAHADDgyyas9mrlmkF";
    private static  String clientId = "ynap2ZgXUTxZYxpYI8zsZIod";
    private static  String clientSecret = "bsrIrHihlgwssaaWHfHpZyZvMYTXMYsS";
    private static  String accessToken = "";
    
    @ResponseBody
    @PostMapping("ocrimg")
    public String ocrimg(@RequestParam("file") MultipartFile file,String type,String backType) throws IOException {
    	String json="";
    	System.out.println("type----------"+type);
    	System.out.println("file----------"+file);
    	if("".equals(type)) {
    		return "类型为空";
    	}
    	switch (type){
    	case "1"://1  身份证识别
    		if(file != null) {
    			file(file);
        		System.out.println("1111");
        		json = idCard(file,backType);
        		return json;
    		}else {
    			return "请上传图片";
    		}
		case "2"://驾驶证识别	
    		System.out.println("2222222222222");
    		file(file);
    		json = drivingLicense(file);
    		return json;
		case "3"://行驶证识别	
    		System.out.println("3333");
    		file(file);
    		json = vehicleLicense(file);
    		return json;
		case "4"://银行卡识别	
    		System.out.println("4444");
    		file(file);
    		json = bankCard(file);
    		return json;
    	default:
    		file(file);
    		System.out.println("55555");
    		json = accurateBasic(file);
    		return json;
    	}
        
    }
    
    /**
     * 身份证识别	
     * @param file
     * @param type
     * @return
     */
    private String idCard(MultipartFile file,String type) {
    	 // 身份证识别url
        String idcardIdentificate = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        // 本地图片路径
//        String filePath = "D:\\53f9b5c63d4d8e314a3a5b6ce701de1.jpg";
        try {
//            byte[] imgData = FileUtil.readFileByBytes(filePath);
            byte[] imgData = file.getBytes();
            String imgStr = Base64Util.encode(imgData);
            // 识别身份证正面id_card_side=front;识别身份证背面id_card_side=back;
            String params ="";
            if("1".equals(type)) {
            	  params = "id_card_side=front&" + URLEncoder.encode("image", "UTF-8") + "="
                         + URLEncoder.encode(imgStr, "UTF-8");
            }else {
            	 params = "id_card_side=back&" + URLEncoder.encode("image", "UTF-8") + "="
                        + URLEncoder.encode(imgStr, "UTF-8");
            }
           
            /**
                                             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            if("".equals(accessToken)) {
            	accessToken = getAuth(clientId,clientSecret);
            }
            String result = HttpUtil.post(idcardIdentificate, accessToken, params);
            System.out.println("idCard-------------"+result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return  null;
    }
    
    /**
     * 驾驶证
     * @return
     */
	@SuppressWarnings("unused")
	private String drivingLicense(MultipartFile file) {
    	 // 驾驶证识别url
        String idcardIdentificate = "https://aip.baidubce.com/rest/2.0/ocr/v1/driving_license";
        // 本地图片路径
//        String filePath = "D:\\53f9b5c63d4d8e314a3a5b6ce701de1.jpg";
        try {
//            byte[] imgData = FileUtil.readFileByBytes(filePath);
            byte[] imgData = file.getBytes();
            String imgStr = Base64Util.encode(imgData);
            String params ="";
            	 params = URLEncoder.encode("image", "UTF-8") + "="
                        + URLEncoder.encode(imgStr, "UTF-8");
            /**
                                             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            if("".equals(accessToken)) {
            	accessToken = getAuth(clientId,clientSecret);
            }
            String result = HttpUtil.post(idcardIdentificate, accessToken, params);
            System.out.println("drivingLicense----------"+result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	
	 /**
     * 行驶证
     * @return
     */
	@SuppressWarnings("unused")
	private String vehicleLicense(MultipartFile file) {
    	 // 行驶证识别url
        String idcardIdentificate = "https://aip.baidubce.com/rest/2.0/ocr/v1/vehicle_license";
        // 本地图片路径
//        String filePath = "D:\\53f9b5c63d4d8e314a3a5b6ce701de1.jpg";
        try {
//            byte[] imgData = FileUtil.readFileByBytes(filePath);
            byte[] imgData = file.getBytes();
            String imgStr = Base64Util.encode(imgData);
            String params ="";
            	 params = URLEncoder.encode("image", "UTF-8") + "="
                        + URLEncoder.encode(imgStr, "UTF-8");
            /**
                                             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            if("".equals(accessToken)) {
            	accessToken = getAuth(clientId,clientSecret);
            }
            String result = HttpUtil.post(idcardIdentificate, accessToken, params);
            System.out.println("drivingLicense----------"+result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	 /**
     * 银行卡
     * @return
     */
	@SuppressWarnings("unused")
	private String bankCard(MultipartFile file) {
    	 // 银行卡url
        String idcardIdentificate = "https://aip.baidubce.com/rest/2.0/ocr/v1/bankcard";
        // 本地图片路径
//        String filePath = "D:\\53f9b5c63d4d8e314a3a5b6ce701de1.jpg";
        try {
//            byte[] imgData = FileUtil.readFileByBytes(filePath);
            byte[] imgData = file.getBytes();
            String imgStr = Base64Util.encode(imgData);
            String params ="";
            	 params = URLEncoder.encode("image", "UTF-8") + "="
                        + URLEncoder.encode(imgStr, "UTF-8");
            /**
                                             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            if("".equals(accessToken)) {
            	accessToken = getAuth(clientId,clientSecret);
            }
            String result = HttpUtil.post(idcardIdentificate, accessToken, params);
            System.out.println("drivingLicense----------"+result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	//https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic
	 /**
     * 通用识别
     * @return
     */
	@SuppressWarnings("unused")
	private String accurateBasic(MultipartFile file) {
    	 // 银行卡url
        String idcardIdentificate = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";
        // 本地图片路径
//        String filePath = "D:\\53f9b5c63d4d8e314a3a5b6ce701de1.jpg";
        try {
//            byte[] imgData = FileUtil.readFileByBytes(filePath);
            byte[] imgData = file.getBytes();
            String imgStr = Base64Util.encode(imgData);
            String params ="";
            	 params = URLEncoder.encode("image", "UTF-8") + "="
                        + URLEncoder.encode(imgStr, "UTF-8");
            /**
                                             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            if("".equals(accessToken)) {
            	accessToken = getAuth(clientId,clientSecret);
            }
            String result = HttpUtil.post(idcardIdentificate, accessToken, params);
            System.out.println("drivingLicense----------"+result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    private  String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }
    
    
    private void file(MultipartFile file) {
    	if(file != null) {
			try {    
                /*   
                 	* 这段代码执行完毕之后，图片上传到了工程的跟路径； 大家自己扩散下思维，如果我们想把图片上传到   
                 * d:/files大家是否能实现呢？ 等等;   
                 	* 这里只是简单一个例子,请自行参考，融入到实际中可能需要大家自己做一些思考，比如： 1、文件路径； 2、文件名；   
                 * 3、文件格式; 4、文件大小的限制;   
                 */    
                BufferedOutputStream out = new BufferedOutputStream(    
                        new FileOutputStream(new File(    
                                file.getOriginalFilename())));    
                out.write(file.getBytes());    
                out.flush();    
                out.close();    
            } catch (FileNotFoundException e) {    
                e.printStackTrace();    
            } catch (IOException e) {    
                e.printStackTrace();    
            }    
		}
    }

}