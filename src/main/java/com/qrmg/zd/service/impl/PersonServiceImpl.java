package com.qrmg.zd.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ai.frame.bean.OutputObject;
import com.qrmg.zd.dao.PersonDao;
import com.qrmg.zd.model.Person;
import com.qrmg.zd.service.PersonService;
import com.qrmg.zd.util.StringUtil;

/**
 * @Description: 用户管理
 * @ClassName: PersonServiceImpl  
 * @author zz
 * @date 2019年1月22日 下午2:47:38
 */
@Service("personService")
public class PersonServiceImpl implements PersonService {

	@Autowired  
    private PersonDao personDao; 
	
	@Override
	public void addPersonRegister(Person person) {
		//查询用户是否已经登记过，登记过则不在登记，只修改登记时间
		Map<String, String> map = new HashMap<>();
		map.put("userPhone", person.getUserPhone());
		map.put("userName", person.getUserName());
		map.put("channelCode", person.getChannelCode());
		List<Person> list = personDao.queryPersonList(map);
		if(CollectionUtils.isEmpty(list)){
			personDao.addPersonRegister(person);
		}else{
			person.setId(list.get(0).getId());
			personDao.updatePerson(person);
		}
	}

	/**
	 * 获取登记用户列表
	 */
	@Override
	public OutputObject queryPerson(Map<String, String> map) {
		OutputObject outputObject = new OutputObject();
		int total = personDao.getCountPerson(map);
		if(total < 1){
			outputObject.setReturnCode("9999");
			outputObject.setReturnMessage("没有获取到对应的用户列表！");
			return outputObject;
		}
		List<Person> list = personDao.queryPersonList(map);
		if(CollectionUtils.isEmpty(list)){
			outputObject.setReturnCode("9999");
			outputObject.setReturnMessage("没有获取到对应的用户列表！");
			return outputObject;
		}
		outputObject.getBean().put("total", String.valueOf(total));
		outputObject.setObject(list);
		outputObject.setReturnCode("0");
		outputObject.setReturnMessage("用户列表获取成功！");
		return outputObject;
	}
	
	/**
	 * @Description: 导出用户登记列表
	 * @author zz
	 * @date 2019年2月20日 下午6:20:56
	 * @return 
	 * @param
	 */
	@Override
    public void export(String[] titles, ServletOutputStream out, Map<String, String> map) {                
        try{
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet hssfSheet = workbook.createSheet("sheet1");
            HSSFRow hssfRow = hssfSheet.createRow(0);
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            HSSFCell hssfCell = null;
            for (int i = 0; i < titles.length; i++) {
                hssfCell = hssfRow.createCell(i);
                hssfCell.setCellValue(titles[i]);
                hssfCell.setCellStyle(hssfCellStyle);
            }
            List<Person> list = personDao.queryPersonList(map);
            if(list != null && !list.isEmpty()){
                for (int i = 0; i < list.size(); i++) {
                    hssfRow = hssfSheet.createRow(i+1);
                    Person person = list.get(i);
                    // 第六步，创建单元格，并设置值
                    String username = "";
                    if(StringUtil.isNotEmpty(person.getUserName())){
                        username = person.getUserName();
                    }
                    hssfRow.createCell(0).setCellValue(username);
                    String channelCode = "";
                    if(StringUtil.isNotEmpty(person.getChannelCode())){
                        channelCode = person.getChannelCode();
                    }
                    hssfRow.createCell(1).setCellValue(channelCode);
                    String phone = "";
                    if(StringUtil.isNotEmpty(person.getUserPhone())){
                    	phone = person.getUserPhone();
                    }
                    hssfRow.createCell(2).setCellValue(phone);
                    String date="";
                    if(person.getCreateTime() != null){
                    	date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(person.getCreateTime());
                    }
                    hssfRow.createCell(3).setCellValue(date);
                }
            }
            try {
                workbook.write(out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }    
    }

}
