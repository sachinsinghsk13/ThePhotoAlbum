package com.techjs.thephotoalbum.web.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/users")
public class UserResources {
	
	@GET
	public TestResource sub() {
		return new TestResource();
	}
}
