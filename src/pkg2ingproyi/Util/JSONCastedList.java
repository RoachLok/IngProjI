package pkg2ingproyi.Util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class JSONCastedList<M extends JSONSerializable> extends ArrayList<M> {

    public JSONCastedList(String rawData, Class<M> className) throws Exception {
        JSONParser  jsonParser  = new JSONParser();
        JSONObject  jsonObject  = (JSONObject) jsonParser.parse(rawData);
        JSONArray   jsonArray   = (JSONArray ) jsonObject.get("items");

        for (Object o : jsonArray)
            this.add(ModelFactory.buildModel(className, (JSONObject) o));
    }
}
