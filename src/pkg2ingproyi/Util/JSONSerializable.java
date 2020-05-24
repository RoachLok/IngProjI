package pkg2ingproyi.Util;

import org.json.simple.JSONObject;

public interface JSONSerializable {
    void jsonParse(JSONObject object);
}

/*
public abstract class JSONSerializable<T> {

    public JSONSerializable() {}

    public JSONSerializable(JSONObject object) { // THis is seen by the generic type
        this.jsonParse(object);
    }

    protected abstract void jsonParse(JSONObject object); // this is an implementation of the constructor
}
*/