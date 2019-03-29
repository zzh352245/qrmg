package com.qrmg.zd.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
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
		String qrurl = request.getScheme() +"://" + request.getServerName();
		
		outputObj.getBean().put("QRUrl", qrurl + "/qrmg/QRCode/" + channelCode + ".png");
		outputObj.setReturnCode("0");
		outputObj.setReturnMessage("二维码生成成功！");
		return outputObj;
	}
	
	/**
	 * @Description: 查询渠道列表
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
		try{
			String channelLevel = request.getParameter("channelLevel");
			String channelName = 
					URLDecoder.decode(URLDecoder.decode(request.getParameter("channelName") == null ? "" : request.getParameter("channelName"), "UTF-8"), "UTF-8");
			String createMg = 
					URLDecoder.decode(URLDecoder.decode(request.getParameter("createMg") == null ? "" : request.getParameter("createMg"), "UTF-8"), "UTF-8");
			String channelQrcodeType = request.getParameter("channelQrcodeType");
			if(StringUtil.isNotEmpty(createMg)){
				map.put("createMg", "%" + createMg + "%");
			}
			if(StringUtil.isNotEmpty(channelName)){
				map.put("channelName", "%" + channelName + "%");
			}
			map.put("channelQrcodeType", channelQrcodeType);
			map.put("channelLevel", channelLevel);
			outputObj = channelService.queryChannelList(map);
		}catch (Exception e){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("获取失败！");
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
		try{
			String channelParentCode = request.getParameter("channelParentCode");
			String channelName = 
					URLDecoder.decode(URLDecoder.decode(request.getParameter("channelName"), "UTF-8"), "UTF-8");
			String channelLinkUrl = request.getParameter("channelLinkUrl");
			String createMg = 
					URLDecoder.decode(URLDecoder.decode(request.getParameter("mgName"), "UTF-8"), "UTF-8");
			if(StringUtil.isEmpty(channelName)
					|| StringUtil.isEmpty(createMg)){
				outputObj.setReturnCode("9999");
				outputObj.setReturnMessage("参数格式不正确！");
				return outputObj;
			}
			Channel channel = new Channel();
			String channelLevel;
			if(StringUtil.isEmpty(channelParentCode)){
				channelLevel="1";
			}else{
				channelLevel="2";
			}
			long cc = System.currentTimeMillis();
			String channelCode = String.valueOf(cc).substring(3, 11);
			//一级渠道，输入链接地址，生成二维码。二级渠道，只需要选择对应的一级渠道，自动生成链接地址和二维码
			if(StringUtils.equals(channelLevel, "2")){
				if(StringUtil.isEmpty(channelParentCode)){
					outputObj.setReturnCode("9999");
					outputObj.setReturnMessage("请选择一级渠道！");
					return outputObj;
				}
				//二级渠道统一跳转自己做的页面，h5页面地址拼上渠道编码生成新二维码
				String u = request.getScheme() +"://" + request.getServerName();
				channelLinkUrl = u+"/qrmg/person/resChannelCode?channelCode=" + channelCode;
			}
			if(StringUtil.isEmpty(channelLinkUrl)){
				outputObj.setReturnCode("9999");
				outputObj.setReturnMessage("请输入渠道链接地址！");
				return outputObj;
			}
			//需要生成二维码
			String fileUrl = QRUtil.createQR(channelLinkUrl, channelCode);
			if(StringUtil.isEmpty(fileUrl)){
				outputObj.setReturnCode("9999");
				outputObj.setReturnMessage("二维码生成失败！");
				return outputObj;
			}
			channel.setChannelQrcode(fileUrl);
			channel.setCreateMg(createMg);
			channel.setChannelCode(channelCode);
			channel.setChannelLevel(channelLevel);
			channel.setChannelParentCode(channelParentCode);
			channel.setChannelName(channelName);
			channel.setChannelLinkUrl(channelLinkUrl);
			channelService.addChannel(channel);
			outputObj.setReturnCode("0");
			outputObj.setReturnMessage("添加成功！");
		}catch (Exception e){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("添加失败！");
		}
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
		try{
			String id = request.getParameter("id");
			String channelParentCode = request.getParameter("channelParentCode");
			String channelName = 
					URLDecoder.decode(URLDecoder.decode(request.getParameter("channelName") == null ? "" : request.getParameter("channelName"), "UTF-8"), "UTF-8");
			String channelLinkUrl = request.getParameter("channelLinkUrl");
			String channelCode = request.getParameter("channelCode");
			String updateMg = 
					URLDecoder.decode(URLDecoder.decode(request.getParameter("updateMg"), "UTF-8"), "UTF-8");
			String channelQrcodeType = request.getParameter("channelQrcodeType");//二维码开启状态
			String type=request.getParameter("type");//1-开启/关闭二维码 0-修改渠道信息
			if(StringUtil.isEmpty(id) || StringUtil.isEmpty(updateMg)){
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
			if(StringUtils.equals(type, "0")){
				channel.setChannelParentCode(channelParentCode);
				if(StringUtil.isEmpty(channelParentCode)){
					channel.setChannelLevel("1");
					channel.setChannelParentCode("");
				}else{
					channel.setChannelLevel("2");
					//二级渠道统一跳转自己做的页面，h5页面地址拼上渠道编码生成新二维码
					String u = request.getScheme() +"://" + request.getServerName();
					channelLinkUrl = u+"/qrmg/person/resChannelCode?channelCode=" + channelCode;
				}
				//重新生成二维码
				String fileUrl = QRUtil.createQR(channelLinkUrl, channelCode);
				if(StringUtil.isEmpty(fileUrl)){
					outputObj.setReturnCode("9999");
					outputObj.setReturnMessage("二维码生成失败！");
					return outputObj;
				}
				channel.setChannelQrcode(fileUrl);
				channel.setChannelName(channelName);
				channel.setChannelLinkUrl(channelLinkUrl);
				channel.setUpdateMg(updateMg);
				channel.setChannelCode(channelCode);
			}else{
				channel.setChannelQrcodeType(channelQrcodeType);
				channel.setUpdateMg(updateMg);
			}
			channelService.updateChannel(channel);
			outputObj.setReturnCode("0");
			outputObj.setReturnMessage("修改成功！");
		}catch (Exception e){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("修改失败！");
		}
		return outputObj;
	}
	
	@RequestMapping("/upload")
	public OutputObject upload(MultipartFile file,HttpServletRequest request,Model model) throws Exception{
		OutputObject outputObj = new OutputObject();
		//保存数据库的路径
		String sqlPath = null; 
		//定义文件保存的本地路径
		String localPath=request.getSession().getServletContext().getRealPath("/QRCode");
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
	
	/**
	 * @Description: 查询渠道列表
	 * @author zz
	 * @date 2019年1月23日 下午4:26:44
	 * @return 
	 * @param
	 */
	@ResponseBody
	@RequestMapping(value="/queryChannel1", method=RequestMethod.GET)
	public OutputObject queryChannel1(HttpServletRequest request){
		OutputObject outputObj = new OutputObject();
		Map<String, String> map = new HashMap<>();
		try{
			String channelLevel = request.getParameter("channelLevel");
			String channelName = 
					URLDecoder.decode(URLDecoder.decode(request.getParameter("channelName") == null ? "" : request.getParameter("channelName"), "UTF-8"), "UTF-8");
			String createMg = 
					URLDecoder.decode(URLDecoder.decode(request.getParameter("createMg") == null ? "" : request.getParameter("createMg"), "UTF-8"), "UTF-8");
			String channelQrcodeType = request.getParameter("channelQrcodeType");
			if(StringUtil.isNotEmpty(createMg)){
				map.put("createMg", "%" + createMg + "%");
			}
			if(StringUtil.isNotEmpty(channelName)){
				map.put("channelName", "%" + channelName + "%");
			}
			map.put("channelQrcodeType", channelQrcodeType);
			map.put("channelLevel", channelLevel);
			outputObj = channelService.queryChannel(map);
		}catch (Exception e){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("获取失败！");
		}
		return outputObj;
	}
	
}
