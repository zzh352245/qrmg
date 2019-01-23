package com.qrmg.zd.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qrmg.zd.service.impl.ManagerServiceImpl;

/**
 * @Description: 后台管理
 * @ClassName: ManagerController  
 * @author zz
 * @date 2019年1月22日 下午3:24:07
 */
@RestController
@RequestMapping(value="/front/mana")
public class ManagerController {

	@Resource(name="managerService")
	private ManagerServiceImpl managerService;
	
	/**
	 * @Description: 生成验证码
	 * @author zz
	 * @date 2019年1月23日 上午10:43:40
	 * @return 
	 * @param
	 */
	@RequestMapping(value="/loginCode", method=RequestMethod.GET)
	public void getcode(HttpServletRequest request, HttpServletResponse response) {
		Long time=System.currentTimeMillis();
        response.setHeader("Content-type", "text/html;charset=UTF-8");  
		response.setHeader("Expires", "-1");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "-1");
		response.setDateHeader("Last-Modified", time);
		response.setDateHeader("Date", time);
		response.setDateHeader("Expires", 0);
		int height=33;
		int width=90;
		BufferedImage img=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g=img.getGraphics();
		g.drawRect(0, 0, width, height);
		g.setColor(Color.gray);
		g.fillRect(1, 1, width-2, height-2);
		g.setColor(Color.BLACK);
		Random r=new Random();
		for(int i=0;i<20;i++)
		g.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));
		g.setColor(Color.RED);
		g.setFont(new Font("微软雅黑", Font.BOLD|Font.ITALIC, 20));
		int d=15;
		StringBuffer sb=new StringBuffer();
		for(int j=0;j<4;j++){
			String code=r.nextInt(10)+"";
			sb.append(code);
			g.drawString(code+"", d, 20);
			d+=20;
		}
		request.getSession().setAttribute("LOGIN_CHECK_CODE", sb.toString());
		try {
			ImageIO.write(img, "jpg", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
