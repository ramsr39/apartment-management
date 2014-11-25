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
    	//System.out.println(service.saveCommunityDetails(getCommunitypaylaod()).getEntity().toString());
    	System.out.println(service.updateBuildDetails(getBuildingPaylaod(),"build1").getEntity());
    	
    	System.out.println(service.updateCommunityDetails(getCommunitypaylaod()).getEntity());

    	//String paylaod = "{emailId:msgshd@gmail.com,password:ds}";
    	//System.out.println(ds.ValidateUser(paylaod).getEntity());
    }
    
    private static String getCommunitypaylaod(){
    	CommunityDTO dto = new CommunityDTO();
    	dto.setId(32);
    	dto.setName("mycommunity-updated");
    	dto.setAddress1("abc,plot-33");
    	dto.setCity("HYD");
    	dto.setState("AP");
    	dto.setPostalCode(534460);
    	dto.setType("villa");
    	return JsonUtils.convertJavaObjectToJson(dto);
    }
    private static String getBuildingPaylaod(){
    	BuildingDTO dto = new BuildingDTO();
    	
    	dto.setName("build5");
    	dto.setTotalFloors(11);
    	dto.setTotalUnits(20);
    	dto.setImageUrl("image");
    	dto.setCommunityName("mycommunity");
    	return JsonUtils.convertJavaObjectToJson(dto);
    }
}
