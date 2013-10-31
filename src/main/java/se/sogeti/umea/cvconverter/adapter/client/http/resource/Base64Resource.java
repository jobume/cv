package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;

import se.sogeti.umea.cvconverter.adapter.client.http.streamutil.StreamUtil;

import com.sun.jersey.multipart.FormDataParam;

@RequestScoped
@Path("/base64")
public class Base64Resource {

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String encodeToBase64(@FormDataParam("file") InputStream file)
			throws IOException {
		byte[] bytes = StreamUtil.readStreamToBytes(file);
		return DatatypeConverter.printBase64Binary(bytes);
	}
	
}
