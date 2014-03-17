package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import se.sogeti.umea.configuration.InjectedConfiguration;
import se.sogeti.umea.configuration.JndiPrinter;
import se.sogeti.umea.cvconverter.application.ConverterService;

@RequestScoped
@Path("/healthcheck")
public class HealthCheckResource {

	private final static int HEALTH_CHECK_TYPE_ALIVE = 1;

	private final static int HEALTH_CHECK_TYPE_DB = 2;

	@Inject
	@InjectedConfiguration(key = "host.name", defaultValue = "http://localhost:8080")
	private String hostName;

	@Inject
	private ConverterService service;

	@GET
	@Path("/{type}")
	public Response healthCheck(@PathParam(value = "type") int type)
			throws NamingException {
		if (HEALTH_CHECK_TYPE_ALIVE == type) {
			return Response.ok(
					"Application is running with class: " + service.getClass())
					.build();
		} else if (HEALTH_CHECK_TYPE_DB == type) {
			DataSource ds = (DataSource) new InitialContext()
					.lookup("java:jboss/datasources/MysqlDS");
			try (Connection con = ds.getConnection();
					PreparedStatement ps = con
							.prepareStatement("SELECT count(*) FROM cv")) {
				ps.execute();
			} catch (SQLException e) {
				throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
			}

			return Response.ok("Database found and is running. ").build();
		} else {
			return Response
					.ok("Application is running. No type supplied. Use 1 to check running. Use 2 to check database.")
					.build();
		}
	}

	@GET
	@Path("/jndi")
	@Produces(MediaType.TEXT_HTML)
	public String printJndiTree() {
		System.out.println("SayHtmlHello");

		try {
			String message = new JndiPrinter(new InitialContext())
					.getJndiTree();
			StringBuffer buf = new StringBuffer();
			buf.append("<html> " + "<title>" + "Hello Jersey" + "</title>"
					+ "<body><h1>" + "Hello Jersey" + "</h1>");
			buf.append(message);
			buf.append("</body>" + "</html> ");
			return buf.toString();
		} catch (NamingException e) {
			throw new IllegalStateException("Could not get InitialContext", e);
		}

	}
}
