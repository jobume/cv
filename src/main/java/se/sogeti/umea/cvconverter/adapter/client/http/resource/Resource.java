package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import se.sogeti.umea.cvconverter.application.ConverterService;

@RequestScoped
abstract class Resource {
	
	@Inject
	ConverterService service;

	
	@Context
	UriInfo uriInfo;
	
}
