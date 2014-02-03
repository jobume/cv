package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import se.sogeti.umea.configuration.InjectedConfiguration;
import se.sogeti.umea.configuration.JndiPrinter;

@RequestScoped
@Path("/hello")
public class HelloResource {

	@Inject
	@InjectedConfiguration(key = "host.name", defaultValue = "http://localhost:8080")
	private String hostName;

	@GET
	public Response sayHello() throws NamingException {

		// DataSource ds = (DataSource) new
		// InitialContext().lookup("java:comp/env/jdbc/h2Database");
		DataSource ds = (DataSource) new InitialContext()
				.lookup("java:jboss/datasources/MysqlDS");

		return Response.ok(
				"Hello world!!! You are running at: " + hostName
						+ ". The data source was: "
						+ (ds != null ? " Found " : "Not found (null)"))
				.build();
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
