package pkg2ingproyi.Util;

import org.json.simple.JSONObject;
import pkg2ingproyi.Model.Vehicle;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ModelFactory {
    private static Map<Class<?>, ModelBuilder<?>> elementBuilder = new HashMap<>();

    static {
        elementBuilder.put(Vehicle.class, (ModelBuilder<Vehicle>) Vehicle::new); // because we can't pass anonymous class here because build returns generic type

    }

    public static <T> T buildModel(Type targetType, JSONObject object) throws Exception {
        if (!elementBuilder.containsKey(targetType))
            throw new IllegalArgumentException("Missing Element Factory for Type " + targetType);

        return (T) elementBuilder.get(targetType).build(object);
    }

    public interface ModelBuilder<T> {
        T build(JSONObject object) throws Exception;
    }
}

