package com.qrmg.zd.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import org.w3c.dom.Element;

/**
 * 工具类：生成验证码图片
 *
 * @author liuqinxiao
 * @date 2016年1月23日 下午2:37:04
 * @version 1.0
 */

public class SecurityImage {
	/**
	 * 生成验证码图片
	 * @param securityCode
	 * @return
	 */
	public static BufferedImage createImage(String securityCode) {
		int codeLength = securityCode.length();//验证码长度
		int fontSize = 15;//字体大小
		int fontWidth = fontSize+1;
		
		//图片宽高
		int width = codeLength*fontWidth+6;
		int height = fontSize*2+1;
		//图片
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		g.setColor(Color.WHITE);//设置背景色
		g.fillRect(0, 0, width, height);//填充背景
		g.setColor(Color.LIGHT_GRAY);//设置边框颜色
		g.setFont(new Font("Arial", Font.BOLD, height-2));//边框字体样式
		g.drawRect(0, 0, width-1, height-1);//绘制边框
		
		//绘制噪点
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		g.setColor(Color.LIGHT_GRAY);
		for (int i = 0; i < codeLength*6; i++) {
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);
			g.drawRect(x, y, 1, 1);//绘制1*1大小的矩形
		}
		//绘制验证码
		int codeY = height-10;
		g.setColor(new Color(19,148,246));
		g.setFont(new Font("Georgia", Font.BOLD, fontSize));
		for(int i=0;i<codeLength;i++){
			g.drawString(String.valueOf(securityCode.charAt(i)), i*16+5, codeY);
		}
		g.dispose();//关闭资源
		return image;
	}
	
	/**
	 * 将BufferedImage转换成ByteArrayinputStream
	 * @param image
	 * @return
	 */
	public static ByteArrayInputStream convertImageToStream(BufferedImage image){
		ByteArrayInputStream inputStream = null;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		
//		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outStream);
		try {
		    saveAsJPEG(100, image, 1, outStream);
//			encoder.encode(image);
			byte[] b = outStream.toByteArray();
			inputStream = new ByteArrayInputStream(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}
	
	/**
	 * 返回验证码图片的流格式
	 * @param securityCode
	 * @return
	 */
	public static ByteArrayInputStream getImageAsInputStream(String securityCode){
		BufferedImage image = createImage(securityCode);
		return convertImageToStream(image);
	}
	
	/** 
     * 以JPEG编码保存图片 
     * @param dpi  分辨率 
     * @param image_to_save  要处理的图像图片 
     * @param JPEGcompression  压缩比 
     * @param fos 文件输出流 
     * @throws IOException 
     */  
    public static void saveAsJPEG(Integer dpi ,BufferedImage image_to_save, float JPEGcompression, ByteArrayOutputStream fos) throws IOException {  
            
        //useful documentation at http://docs.oracle.com/javase/7/docs/api/javax/imageio/metadata/doc-files/jpeg_metadata.html  
        //useful example program at http://johnbokma.com/java/obtaining-image-metadata.html to output JPEG data  
        
        //old jpeg class  
        //com.sun.image.codec.jpeg.JPEGImageEncoder jpegEncoder  =  com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(fos);  
        //com.sun.image.codec.jpeg.JPEGEncodeParam jpegEncodeParam  =  jpegEncoder.getDefaultJPEGEncodeParam(image_to_save);  
        
        // Image writer  
//      JPEGImageWriter imageWriter = (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpeg").next();  
        ImageWriter imageWriter  =   ImageIO.getImageWritersBySuffix("jpg").next();  
        ImageOutputStream ios  =  ImageIO.createImageOutputStream(fos);  
        imageWriter.setOutput(ios);  
        //and metadata  
        IIOMetadata imageMetaData  =  imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image_to_save), null);  
           
         //sonarqube
        if(dpi !=  null){  
               
             //old metadata  
            //jpegEncodeParam.setDensityUnit(com.sun.image.codec.jpeg.JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);  
            //jpegEncodeParam.setXDensity(dpi);  
            //jpegEncodeParam.setYDensity(dpi);  
        
            //new metadata  
            Element tree  =  (Element) imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");  
            Element jfif  =  (Element)tree.getElementsByTagName("app0JFIF").item(0);  
            jfif.setAttribute("Xdensity", Integer.toString(dpi) );  
            jfif.setAttribute("Ydensity", Integer.toString(dpi));  
               
        }  
        
        
        if(JPEGcompression >= 0 && JPEGcompression <= 1f){  
        
            //old compression  
            //jpegEncodeParam.setQuality(JPEGcompression,false);  
        
            // new Compression  
            JPEGImageWriteParam jpegParams  =  (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();  
            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);  
            jpegParams.setCompressionQuality(JPEGcompression);  
        
        }  
        
        //old write and clean  
        //jpegEncoder.encode(image_to_save, jpegEncodeParam);  
        
        //new Write and clean up  
        imageWriter.write(imageMetaData, new IIOImage(image_to_save, null, null), null);  
        ios.close();  
        imageWriter.dispose();  
        
    }  
}
