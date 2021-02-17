package myapp.api.deserialiazer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import myapp.api.ResponseContainer;


/// UNUSED NIEUŻYWANY NA RAZIE !! NIE USUWAC !!
public class ResponseDeserialazer implements JsonDeserializer<ResponseContainer<Object>> {

    @Override
    public ResponseContainer<Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return new ResponseContainer<Object>(json.getAsString(), json.getAsBoolean(), json.getAsString(),
                json.getAsJsonObject());
    }
}
