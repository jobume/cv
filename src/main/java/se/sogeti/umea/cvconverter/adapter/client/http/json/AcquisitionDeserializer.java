package se.sogeti.umea.cvconverter.adapter.client.http.json;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import se.sogeti.umea.cvconverter.application.impl.cvparser.model.AcquisitionImpl;

public class AcquisitionDeserializer extends JsonDeserializer<AcquisitionImpl> {

	@Override
	public AcquisitionImpl deserialize(JsonParser parser,
			DeserializationContext context) throws IOException,
			JsonProcessingException {

		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);

		String date = node.get("date").getTextValue();
		String name = node.get("name").getTextValue();
		String location = node.get("location").getTextValue();

		return new AcquisitionImpl(date, name, location);

	}

}
