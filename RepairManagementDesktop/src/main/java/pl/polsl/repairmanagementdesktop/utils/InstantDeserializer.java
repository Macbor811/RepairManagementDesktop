package pl.polsl.repairmanagementdesktop.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;

public class InstantDeserializer extends StdDeserializer<Instant> {

    public InstantDeserializer() {
        super(Instant.class);
    }

    @Override
    public Instant deserialize(JsonParser json, DeserializationContext context)
            throws IOException, JsonProcessingException {
            //DateFormat df = StdDateFormat.getBlueprintISO8601Format();
        return Instant.parse(json.getText());

    }

}
