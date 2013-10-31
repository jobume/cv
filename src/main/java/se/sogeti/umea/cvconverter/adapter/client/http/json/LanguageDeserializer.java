package se.sogeti.umea.cvconverter.adapter.client.http.json;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import se.sogeti.umea.cvconverter.application.Language;
import se.sogeti.umea.cvconverter.application.impl.cvparser.model.LanguageImpl;

public class LanguageDeserializer extends JsonDeserializer<LanguageImpl> {

	@Override
	public LanguageImpl deserialize(JsonParser parser,
			DeserializationContext context) throws IOException,
			JsonProcessingException {

		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);

		String name = node.get("name").getTextValue();
		Language.Level level = Language.Level.valueOf(node.get("level")
				.getTextValue().toUpperCase());

		return new LanguageImpl(name, level);
	}

}
