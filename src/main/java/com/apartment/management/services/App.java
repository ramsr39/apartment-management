package com.apartment.management.services;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.apartment.management.dto.BuildingDTO;
import com.apartment.management.dto.CommunityDTO;
import com.apartment.management.utils.JsonUtils;

/**
 * Hello world!
 *
 */
public class App 
{ 
    public static void main( String[] args ) throws SQLException
    {
    	ApplicationContext ctx = new ClassPathXmlApplicationContext("application-context.xml");
    	
    	CommunityDetailsService service = (CommunityDetailsService)ctx.getBean("comunityDetailsService");
    	//System.out.println(service.saveCommunityDetails("test@gmail.com",getCommunitypaylaod()).getEntity().toString());
    	System.out.println(service.saveBuildDetails(getBuildingPaylaod()).getEntity());
    	
    	//System.out.println(service.updateCommunityDetails(getCommunitypaylaod()).getEntity());
    	
    	 String res = service.findCommunityDetails("abc@gmail.com");
    	 System.out.println(res);
    	 
    	 //String paylaod = "{emailId:msgshd@gmail.com,password:ds}";
    	//System.out.println(ds.ValidateUser(paylaod).getEntity());
    }
    
    private static String getCommunitypaylaod(){
    	CommunityDTO dto = new CommunityDTO();
    	dto.setId(32);
    	dto.setName("mycommunity111");
    	dto.setAddress1("abc,plot-33");
    	dto.setAddress2("near by sominthing");
    	dto.setCity("HYD");
    	dto.setState("Andhra Pradesh");
    	dto.setPostalCode(534460);
    	dto.setCountry("India");
    	dto.setType("villa");
    	return JsonUtils.convertJavaObjectToJson(dto);
    }
    private static String getBuildingPaylaod(){
    	BuildingDTO dto = new BuildingDTO();
    	
    	dto.setName("build66");
    	dto.setTotalFloors(11);
    	dto.setTotalUnits(20);
    	dto.setImageUrl("image");
    	dto.setCommunityName("mycommunity111");
    	return JsonUtils.convertJavaObjectToJson(dto);
    }
}
