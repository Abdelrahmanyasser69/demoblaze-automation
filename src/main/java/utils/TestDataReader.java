
package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class TestDataReader {
    public static JSONObject getTestData(String keyPath) {
        JSONObject data = null;
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("src/main/java/testData.json"));
            JSONObject jsonObject = (JSONObject) obj;

            String[] keys = keyPath.split("\\.");
            for (String key : keys) {
                Object value = jsonObject.get(key);
                if (value instanceof JSONObject) {
                    jsonObject = (JSONObject) value;
                } else {
                    data = new JSONObject();
                    data.put(key, value);
                    break;
                }
            }
            if (data == null) {
                data = jsonObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
