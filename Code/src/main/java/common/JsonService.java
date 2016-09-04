package common;

import common.interfaces.JsonConvertable;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import java.util.Collection;

public class JsonService {

    public static <T extends JsonConvertable> JsonArray getListAsJsonArray(Collection<T> list){
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (T entry : list){
            arrayBuilder.add(entry.toJson());
        }

        return arrayBuilder.build();
    }
}
