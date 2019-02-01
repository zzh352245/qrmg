package com.qrmg.zd.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ai.frame.bean.OutputObject;
import com.qrmg.zd.model.Channel;
import com.qrmg.zd.service.impl.ChannelServiceImpl;
import com.qrmg.zd.util.QRUtil;
import com.qrmg.zd.util.StringUtil;

/**
 * @Description: 后台管理
 * @ClassName: ManagerController  
 * @author zz
 * @date 2019年1月22日 下午3:24:07
 */
@RestController
@RequestMapping(value="/front/mana")
public class ManagerController {

	@Resource(name="channelService")
	private ChannelServiceImpl channelService;
	
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
	
	/**
	 * @Description: 生成二维码
	 * @author zz
	 * @date 2019年1月25日 下午3:33:45
	 * @return 
	 * @param
	 */
	@RequestMapping(value="/createQR", method=RequestMethod.GET)
	public OutputObject createQRForChannel(HttpServletRequest request){
		OutputObject outputObj = new OutputObject();
		String channelCode = request.getParameter("channelCode");
		String url = request.getParameter("url");
		if(StringUtil.isEmpty(channelCode) || !StringUtils.startsWith(url, "http")){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("参数格式不正确！");
			return outputObj;
		}
		String fileUrl = QRUtil.createQR(url, channelCode);
		if(StringUtil.isEmpty(fileUrl)){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("二维码生成失败！");
			return outputObj;
		}
		outputObj.getBean().put("QRUrl", fileUrl);
		outputObj.setReturnCode("0");
		outputObj.setReturnMessage("二维码生成成功！");
		return outputObj;
	}
	
	/**
	 * @Description: 查询渠道列表。type=0不分页
	 * @author zz
	 * @date 2019年1月23日 下午4:26:44
	 * @return 
	 * @param
	 */
	@ResponseBody
	@RequestMapping(value="/queryChannel", method=RequestMethod.GET)
	public OutputObject queryChannel(HttpServletRequest request){
		OutputObject outputObj = new OutputObject();
		Map<String, String> map = new HashMap<>();
		String channelLevel = request.getParameter("channelLevel");
		String channelName = request.getParameter("channelName");
		String createMg = request.getParameter("createMg");
		String channelQrcodeType = request.getParameter("channelQrcodeType");
		String start = request.getParameter("start");
		String length = request.getParameter("length");
		String type = request.getParameter("type");
		if(StringUtil.isEmpty(type)){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("参数格式不正确！");
			return outputObj;
		}
		if(StringUtils.equals("0", type)){
			outputObj = channelService.queryChannel(map);
		}else{
			if(StringUtil.isEmpty(start) || StringUtil.isEmpty(length)){
				outputObj.setReturnCode("9999");
				outputObj.setReturnMessage("参数格式不正确！");
				return outputObj;
			}
			try {
				Integer.parseInt(start);
				Integer.parseInt(length);
			} catch (NumberFormatException e) {
				outputObj.setReturnCode("9999");
				outputObj.setReturnMessage("分页格式不正确！");
				return outputObj;
			}
			if(StringUtil.isNotEmpty(createMg)){
				map.put("createMg", "%" + createMg + "%");
			}
			if(StringUtil.isNotEmpty(channelName)){
				map.put("channelName", "%" + channelName + "%");
			}
			map.put("channelQrcodeType", channelQrcodeType);
			map.put("channelLevel", channelLevel);
			map.put("start", start);
			map.put("length", length);
			outputObj = channelService.queryChannelList(map);
		}
		return outputObj;
	}
	
	/**
	 * @Description: 添加渠道信息
	 * @author zz
	 * @date 2019年1月24日 下午3:31:24
	 * @return 
	 * @param
	 */
	@ResponseBody
	@RequestMapping(value="/addChannel", method=RequestMethod.GET)
	public OutputObject addChannel(HttpServletRequest request){
		OutputObject outputObj = new OutputObject();
		String channelLevel = request.getParameter("channelLevel");
		String channelParentCode = request.getParameter("channelParentCode");
		String channelName = request.getParameter("channelName");
		String channelQrcode = request.getParameter("channelQrcode");
		String channelLinkUrl = request.getParameter("channelLinkUrl");
		String createMg = request.getParameter("mgName");
		String type = request.getParameter("type");
		if(StringUtil.isEmpty(channelLevel) || StringUtil.isEmpty(channelName)
				|| StringUtil.isEmpty(createMg) || StringUtil.isEmpty(type)){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("参数格式不正确！");
			return outputObj;
		}
		Channel channel = new Channel();
		long cc = System.currentTimeMillis();
		String channelCode = String.valueOf(cc).substring(3, 11);
		if(StringUtils.equals("1", type)){
			//需要生成二维码突破
			String fileUrl = QRUtil.createQR(channelLinkUrl, channelCode);
			if(StringUtil.isEmpty(fileUrl)){
				outputObj.setReturnCode("9999");
				outputObj.setReturnMessage("二维码生成失败！");
				return outputObj;
			}
			channel.setChannelQrcode(fileUrl);
		}else{
			//已经上传了二维码图片
			channel.setChannelQrcode(channelQrcode);
		}
		channel.setCreateMg(createMg);
		channel.setChannelCode(channelCode);
		channel.setChannelLevel(channelLevel);
		channel.setChannelParentCode(channelParentCode);
		channel.setChannelName(channelName);
		channel.setChannelLinkUrl(channelLinkUrl);
		channelService.addChannel(channel);
		outputObj.setReturnCode("0");
		outputObj.setReturnMessage("添加成功！");
		return outputObj;
	}
	
	/**
	 * @Description: 修改渠道信息
	 * @author zz
	 * @date 2019年1月24日 下午3:31:24
	 * @return 
	 * @param
	 */
	@ResponseBody
	@RequestMapping(value="/updateChannel", method=RequestMethod.GET)
	public OutputObject updateChannel(HttpServletRequest request){
		OutputObject outputObj = new OutputObject();
		String id = request.getParameter("id");
		String channelParentCode = request.getParameter("channelParentCode");
		String channelName = request.getParameter("channelName");
		String channelQrcode = request.getParameter("channelQrcode");
		String channelLevel = request.getParameter("channelLevel");
		String channelLinkUrl = request.getParameter("channelLinkUrl");
		String channelCode = request.getParameter("channelCode");
		String updateMg = request.getParameter("updateMg");
		String type = request.getParameter("type");//1-修改了链接地址 0-未修改链接地址
		if(StringUtil.isEmpty(id) || StringUtil.isEmpty(updateMg)
				|| StringUtil.isEmpty(channelQrcode) || StringUtil.isEmpty(channelName)
				|| StringUtil.isEmpty(channelLevel) || StringUtil.isEmpty(channelCode)
				|| StringUtil.isEmpty(type) || StringUtil.isEmpty(channelLinkUrl)){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("参数格式不正确！");
			return outputObj;
		}
		Channel channel = new Channel();
		try {
			channel.setId(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("没有获取到要修改的渠道！");
			return outputObj;
		}
		if(StringUtils.equals("1", type)){
			//修改了链接地址，重新生成二维码
			String fileUrl = QRUtil.createQR(channelLinkUrl, channelCode);
			if(StringUtil.isEmpty(fileUrl)){
				outputObj.setReturnCode("9999");
				outputObj.setReturnMessage("二维码生成失败！");
				return outputObj;
			}
			channel.setChannelQrcode(fileUrl);
		}else{
			channel.setChannelQrcode(channelQrcode);
		}
		channel.setChannelLevel(channelLevel);
		channel.setChannelParentCode(channelParentCode);
		channel.setChannelName(channelName);
		channel.setChannelLinkUrl(channelLinkUrl);
		channel.setUpdateMg(updateMg);
		channel.setChannelCode(channelCode);
		channelService.updateChannel(channel);
		outputObj.setReturnCode("0");
		outputObj.setReturnMessage("修改成功！");
		return outputObj;
	}
	
	@RequestMapping("/upload")
	public OutputObject upload(MultipartFile file,HttpServletRequest request,Model model) throws Exception{
		OutputObject outputObj = new OutputObject();
		//保存数据库的路径
		String sqlPath = null; 
		//定义文件保存的本地路径
		String localPath="F:\\codeImg\\";
		//定义 文件名
		String filename=null;  
		if(!file.isEmpty()){  
			//生成uuid作为文件名称  
			String uuid = UUID.randomUUID().toString().replaceAll("-","");  
			//获得文件类型（可以判断如果不是图片，禁止上传）  
			String contentType=file.getContentType();  
			//获得文件后缀名 
			String suffixName=contentType.substring(contentType.indexOf("/")+1);
			//得到 文件名
			filename=uuid+"."+suffixName; 
			//文件保存路径
			file.transferTo(new File(localPath+filename));  
		}
		//把图片的相对路径保存至数据库
		sqlPath = "/codeImg/"+filename;
		System.out.println(sqlPath);
		String linkUrl = QRUtil.readQR(localPath+filename);
		Map<String, String> map = new HashMap<>();
		map.put("imagePath", localPath+filename);
		map.put("linkUrl", linkUrl);
		outputObj.setBean(map);
		System.out.println(map);
		return outputObj;
	}
	
}
