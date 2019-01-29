package com.qrmg.zd.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

/**
 * @Description: 二维码工具类
 * @ClassName: QRUtil  
 * @author zz
 * @date 2019年1月25日 下午3:27:42
 */
public class QRUtil {

	/**
	 * @Description: 生成二维码
	 * @author zz
	 * @date 2019年1月25日 下午3:29:08
	 * @return 
	 * @param
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String createQR(String url, String channelCode){
        int width = 400;
        int height = 400;
        String format = "png";
        String filePath = "F:/codeImg/" + channelCode + ".png";
        try {
			Hashtable hints= new Hashtable();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height,hints);
			File outputFile = new File(filePath);
			MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
			return filePath;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Description: 解析二维码
	 * @author zz
	 * @date 2019年1月25日 下午4:31:24
	 * @return 
	 * @param
	 */
	public static String readQR(String filePath){  
        BufferedImage image;
        try {
            image = ImageIO.read(new File(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码  
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
}
