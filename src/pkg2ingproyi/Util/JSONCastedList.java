package pkg2ingproyi.Util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class JSONCastedList<M extends JSONSerializable> extends ArrayList<M> {

    public JSONCastedList(JSONArray jsonArray, Class<M> className) throws Exception {
        for (Object o : jsonArray)
            this.add(ModelFactory.buildModel(className, (JSONObject) o));
            //className.getDeclaredConstructor(JSONObject.class).newInstance(o));
    }
}
