package com.apartment.management.service.filters;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class ResponseCorsFilter implements ContainerResponseFilter {
	@Override
	public ContainerResponse filter(ContainerRequest req,
			ContainerResponse contResp) {
       System.out.println("ResponseCorsFilter started................");
		ResponseBuilder resp = Response.fromResponse(contResp.getResponse());
		resp.header("Access-Control-Allow-Origin", "*").header(
				"Access-Control-Allow-Methods", "GET, POST, DELETE,PUT,OPTIONS");

		String reqHead = req.getHeaderValue("Access-Control-Request-Headers");

		if (null != reqHead && !reqHead.equals("")) {
			resp.header("Access-Control-Allow-Headers", reqHead);
		}

		contResp.setResponse(resp.build());
		System.out.println("ResponseCorsFilter end................");
		return contResp;
	}

}
