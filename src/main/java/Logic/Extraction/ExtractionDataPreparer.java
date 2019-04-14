package Logic.Extraction;

import Data.DataNode;
import Data.DeserializedDataContainer;
import Data.LabelsTypes;
import Data.XmlSerializator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractionDataPreparer {
    private DeserializedDataContainer dataContainer;
    private int percentToLearn;

    public ExtractionDataPreparer(DeserializedDataContainer dataContainer, int percentToLearn)
    {
        this.dataContainer = dataContainer;
        this.percentToLearn = percentToLearn;
    }

    public DeserializedDataContainer getDataContainer() {
        return dataContainer;
    }

    public void splitDataToLearnAndTest(List<DataNode> learningData,
                                        List<DataNode> testingData)
    {
        Map<String, Integer> countLabels = new HashMap<>();
        for(DataNode node : dataContainer.getDeserializedData()){
            countLabels.putIfAbsent(node.label, 0);
            countLabels.put(node.label, countLabels.get(node.label)+1);
        }

        Map<String, Integer> settedLabels = new HashMap<>();
        for(String s : LabelsTypes.chosen)
        {
            settedLabels.put(s, 0);
        }

        for(DataNode node : dataContainer.getDeserializedData()){
            String label = node.label;
            if(settedLabels.get(label) < countLabels.get(label)*percentToLearn/100)
            {
                learningData.add(node);
            }
            else
            {
                testingData.add(node);
            }
            settedLabels.put(label, settedLabels.get(label)+1);
        }
    }
}
