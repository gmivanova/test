package api.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RestUtils {
    public static boolean validateJsonSchema(String jsonData, String jsonSchema) throws IOException, ProcessingException {
        JsonNode schemaNode = JsonLoader.fromString(jsonSchema);
        JsonNode dataNode = JsonLoader.fromString(jsonData);

        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        JsonSchema schema = factory.getJsonSchema(schemaNode);
        ProcessingReport report = schema.validate(dataNode);

        System.out.println(report.toString());
        return report.isSuccess();
    }

    public static Object getValueOfKeyFromJson(String key, String json) throws IOException {
        HashMap<String, Object> jsonMap;
        jsonMap = new Gson().fromJson(json, new TypeToken<HashMap<String, Object>>(){}.getType());
        return jsonMap.get(key);
    }

    public static String removeKeyFromJson(String key, String json) throws IOException {
        ObjectNode object = (ObjectNode)(JsonLoader.fromString(json));
        object.remove(key);
        return object.toString();
    }

    public static String getProfileFromJson(String jsonName, String profileName) throws IOException {
        String json;
        if (jsonName.contains("schema")) {
            json = JsonLoader.fromResource("/schemas/" + jsonName).toString();
        } else {
            json = JsonLoader.fromResource("/json/" + jsonName).toString();
        }

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        Map<String, JsonElement> retMap = gson.fromJson(json, new TypeToken<HashMap<String, JsonElement>>() {
        }.getType());
        return gson.toJson(retMap.get(profileName));
    }

    public static <T> T retrieveResourceFromResponse( final Response response, final Class<T> resourceClass) {
        return new Gson().fromJson(response.getMessage(), resourceClass);
    }

}


