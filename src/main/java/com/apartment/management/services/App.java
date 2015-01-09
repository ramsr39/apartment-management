package com.apartment.management.services;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.apartment.management.dao.ContactDao;
import com.apartment.management.dao.impl.ContactDaoImpl;
import com.apartment.management.dto.BuildingDTO;
import com.apartment.management.dto.CoOccupantDTO;
import com.apartment.management.dto.CommunityDTO;
import com.apartment.management.dto.EmergencyContactInfo;
import com.apartment.management.dto.FlatDTO;
import com.apartment.management.dto.UserDTO;
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
    	
    	//CommunityDetailsService service = (CommunityDetailsService)ctx.getBean("comunityDetailsService");
    	//System.out.println(service.saveCommunityDetails("myadmin@gmail.com",getCommunitypaylaod()).getEntity().toString());
    	//BuildingDetailsService bs = (BuildingDetailsService)ctx.getBean("buildingDetailsService");
    	//bs.save("38", getBuildingPaylaod());
    	ContactDao c = (ContactDao)ctx.getBean("contactDao");
    	//FlatDetailsService fs = (FlatDetailsService)ctx.getBean("contactDao");
    	//fs.save("24", getFlatpaylaod());
    	//fs.getUserResidenceDetails("abc@gmail.com");
    	
    	ManageUserService mus = (ManageUserService)ctx.getBean("managerUserService");
    	//System.out.println(mus.addUser(getUserPayload()).getEntity());
    	//System.out.println(mus.updateUser(getUserPayload()).getEntity());
   // 	System.out.println(mus.addCoOccupant(JsonUtils.parseObjectToJson(getCoOccupent())));
    	//System.out.println(service.updateCommunityDetails(getCommunitypaylaod()).getEntity());
    	
    	// String res = service.findCommunityDetails("abc@gmail.com");
    	 
    	 //String paylaod = "{emailId:msgshd@gmail.com,password:ds}";
    	//System.out.println(ds.ValidateUser(paylaod).getEntity());
    	
    //	System.out.println(service.findCommunitiesByNameAndCity("test", "VIZAG", "myadmin@gmail.com"));
    	
    	
    }
    
    private static String getUserPayload() {
		UserDTO dto = new UserDTO();
		dto.setUserId("9");
		dto.setFirstName("ramesh");
		dto.setLastName("r");
		dto.setEmailId("abc@gmail.com");
		dto.setPrimaryPhoneNumber("1234567890");
		dto.setSecondaryEmail("xyz@gmail.com");
		dto.setDateOfBirth("11/22/2014");
		dto.setBloodGroup("O+");
		dto.setUid("234567");
		dto.setUidType("PAN");
		EmergencyContactInfo contactInfo = new EmergencyContactInfo();
		contactInfo.setName("abcd");
		contactInfo.setRelation("brother");
		contactInfo.setPhoneNumber("1112233333");
		dto.setEmergencyContactInfo(contactInfo);
		return JsonUtils.parseObjectToJson(dto);
	}

	private static String getCommunitypaylaod(){
    	CommunityDTO dto = new CommunityDTO();
    	//dto.setId(32);
    	dto.setName("tests");
    	dto.setAddress1("abc,plot-33");
    	dto.setAddress2("near by sominthing");
    	dto.setCity("VIZAG");
    	dto.setState("AP");
    	dto.setPostalCode(534460);
    	dto.setCountry("India");
    	dto.setType("villa");
    	return JsonUtils.parseObjectToJson(dto);
    }
    private static String getBuildingPaylaod(){
    	BuildingDTO dto = new BuildingDTO();
    	
    	dto.setName("build66");
    	dto.setTotalFloors(11);
    	dto.setTotalUnits(20);
    	dto.setImageUrl("image");
    	dto.setCommunityName("mycommunity111");
    	return JsonUtils.parseObjectToJson(dto);
    }
    private static String getFlatpaylaod(){
    	FlatDTO dto = new FlatDTO();
    	//dto.setId(32);
    	dto.setBuildingId("24");
    	dto.setFloorNumber("222");
    	dto.setTotalBathRooms(3);
    	dto.setTotalBedRooms(3);
    	dto.setTotalRooms(6);
    	dto.setTwoWheelerParking("yes");
    	dto.setFourWheelerParking("yes");
    	dto.setResidentType("OWNER");
    	dto.setUnitNumber("222");
    	dto.setUnitSize(3);
    	dto.setTotalFourWheelerParkings(1);
    	dto.setTotalTwoWheelerParkings(2);
    	return JsonUtils.parseObjectToJson(dto);
    }
   private static String getCoOccupent(){
	   CoOccupantDTO co = new CoOccupantDTO();
	   co.setFirstName("abc");
	   co.setLastName("a");
	   co.setEmailId("abc@gmail.com");
	   co.setPhoneNumber("9000000000");
	   co.setUserId("myadmin@gmail.com");
	   co.setRelation("father");
	   //co.setDateOfBirth("10/11/1986");
	   System.out.println(JsonUtils.parseObjectToJson(co));
	   return JsonUtils.parseObjectToJson(co);
   }
}
