package com.qrmg.zd.util;

import java.io.File;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class Test {

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception{
//        String text = "https://www.12306.cn/index/";//二维码的内容
    	String text = "http://www.baidu.com";//二维码的内容
        int width = 400;  
        int height = 400;  
        String format = "png";    
        Hashtable hints= new Hashtable();  
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);  
        File outputFile = new File("F:/codeImg/1.png");  
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile); 
        System.out.println("二维码生成成功!");
        
        System.out.println(QRUtil.readQR("F:/codeImg/1.png"));
    }
    
}