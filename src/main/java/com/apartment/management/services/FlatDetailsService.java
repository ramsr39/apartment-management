package com.apartment.management.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.apartment.management.dao.FlatDao;
import com.apartment.management.dto.FlatDTO;
import com.apartment.management.utils.JsonUtils;

@Path("/")
public class FlatDetailsService {

	private FlatDao dao;

@POST
@Path("/building/{buildingId}/flat")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response save(@PathParam("buildingId") final String buildingId,
		final String flatDetilsPayload){
		assetId(buildingId,"building id should not null or empty");
	long buildId=Long.parseLong(buildingId);
	FlatDTO flatDTO = JsonUtils.parseJsonToObject(flatDetilsPayload, FlatDTO.class);
	if(null==flatDTO){
		throw new RuntimeException("FlatDTO should not be null");
	}
	flatDTO.setBuildingId(buildId);
	long flatId = dao.save(flatDTO);
	flatDTO.setId(flatId);
	return Response.ok().entity(JsonUtils.parseObjectToJson(flatDTO)).build();
}

@PUT
@Path("/building/{buildingId}/flat/{flatId}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response update(@PathParam("flatId") final String flatId,
		final String flatDetilsPayload){
	assetId(flatId,"flatId should not null");
	long id=Long.parseLong(flatId);
	FlatDTO flatDTO = JsonUtils.parseJsonToObject(flatDetilsPayload, FlatDTO.class);
	if(null==flatDTO){
		throw new RuntimeException("FlatDTO should not be null");
	}
	flatDTO.setId(id);
	dao.update(flatDTO);
	return Response.ok().entity(JsonUtils.parseObjectToJson(flatDTO)).build();
}

@DELETE
@Path("/building/{buildingId}/flat/{flatId}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response delete(@PathParam("flatId") final String flatId){
	assetId(flatId, "flatId should not null or empty");
	long id=Long.parseLong(flatId);
	dao.delete(id);
	return Response.ok().build();
}

private void assetId(final String id,final String message) {
	if (StringUtils.isBlank(id)) {
		throw new IllegalArgumentException(message);
	}
}

public void setDao(final FlatDao dao) {
	this.dao = dao;
}

}
