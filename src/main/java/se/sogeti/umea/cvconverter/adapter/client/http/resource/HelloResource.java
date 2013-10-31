package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import se.sogeti.umea.configuration.InjectedConfiguration;

@RequestScoped
@Path("/hello")
public class HelloResource {

	@Inject
	@InjectedConfiguration(key = "host.name", defaultValue = "http://localhost:8080")
	private String hostName;

	@GET
	public Response sayHello() throws NamingException {

		DataSource ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/h2Database");

		return Response.ok(
				"Hello world!!! You are running at: " + hostName
						+ ". The data source was: " + (ds != null ? " Found "
						: "Not found (null).")).build();
	}
}
