package com.qrmg.zd.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
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
	
	
}
