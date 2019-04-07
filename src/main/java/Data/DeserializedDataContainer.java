package Data;

import java.util.ArrayList;
import java.util.List;

public class DeserializedDataContainer {

    private List<DataNode> deserializedData;

    public DeserializedDataContainer()
    {
        deserializedData = new ArrayList<>();
    }

    public List<DataNode> getDeserializedData() {
        return deserializedData;
    }

    public void setDeserializedData(List<DataNode> deserializedData) {
        this.deserializedData = deserializedData;
    }

    public void setDeserializedData(DataNode node) {
        this.deserializedData.add(node);
    }

    public void setDeserializedData(String body, String place) {
        DataNode node = new DataNode();
        node.body = body;
        node.place = place;
        this.deserializedData.add(node);

    }
}
