package com.apartment.management.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/user")
public final class UserDetailsService {
@GET
	public Response getUserStatus() {
	  String res = "my testing";
	  return Response.ok().entity(res).build();
	}
}
