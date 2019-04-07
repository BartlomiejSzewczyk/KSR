import java.util.HashMap;
import java.util.Map;

public class DeserializedDataContainer {

    private Map<String, String> deserializedData;

    public DeserializedDataContainer()
    {
        deserializedData = new HashMap<>();
    }

    public Map<String, String> getDeserializedData() {
        return deserializedData;
    }

    public void setDeserializedData(Map<String, String> deserializedData) {
        this.deserializedData = deserializedData;
    }

    public void setDeserializedData(String body, String place) {
        this.deserializedData.put(body, place);
    }
}
