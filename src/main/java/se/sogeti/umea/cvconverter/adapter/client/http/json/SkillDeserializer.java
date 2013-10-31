package se.sogeti.umea.cvconverter.adapter.client.http.json;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import se.sogeti.umea.cvconverter.application.Skill;
import se.sogeti.umea.cvconverter.application.impl.cvparser.model.SkillImpl;

public class SkillDeserializer extends JsonDeserializer<SkillImpl> {

	@Override
	public SkillImpl deserialize(JsonParser parser,
			DeserializationContext context) throws IOException,
			JsonProcessingException {

		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);

		String name = node.get("name").getTextValue();
		Skill.Level level = Skill.Level.valueOf(node.get("level")
				.getTextValue());

		SkillImpl skillImpl = new SkillImpl(name, level);
		
		skillImpl.setImportant(node.get("important").asBoolean());
		
		return skillImpl;
	}

}
