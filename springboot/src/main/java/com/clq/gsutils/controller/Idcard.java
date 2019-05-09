package com.clq.gsutils.controller;


import java.net.URLEncoder;

import com.clq.gsutils.utils.Base64Util;
import com.clq.gsutils.utils.FileUtil;
import com.clq.gsutils.utils.HttpUtil;

/**
 * 身份证识别
 */
public class Idcard {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static void main(String[] args) {
        // 身份证识别url
        String idcardIdentificate = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        // 本地图片路径
        String filePath = "D:\\53f9b5c63d4d8e314a3a5b6ce701de1.jpg";
        try {
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            // 识别身份证正面id_card_side=front;识别身份证背面id_card_side=back;
            String params = "id_card_side=back&" + URLEncoder.encode("image", "UTF-8") + "="
                    + URLEncoder.encode(imgStr, "UTF-8");
            /**
                                             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            String accessToken = "24.1e2cf2d608b18160342d9030185a4b04.2592000.1559963010.282335-16208411";
            String result = HttpUtil.post(idcardIdentificate, accessToken, params);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
