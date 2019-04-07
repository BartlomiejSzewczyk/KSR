package Logic.Extraction;

import Data.DataNode;
import Data.DeserializedDataContainer;

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
        Map<String, Integer> countCountries = new HashMap<>();
        for(DataNode node : dataContainer.getDeserializedData()){
            countCountries.putIfAbsent(node.place, 0);
            countCountries.put(node.place, countCountries.get(node.place)+1);
        }

        Map<String, Integer> settedCountries = new HashMap<>()
        {{
            put("usa", 0);
            put("canada", 0);
            put("japan", 0);
            put("uk", 0);
            put("france", 0);
            put("west-germany", 0);
        }};

        for(DataNode node : dataContainer.getDeserializedData()){
            String country = node.place;
            if(settedCountries.get(country) < countCountries.get(country)*percentToLearn/100)
            {
                learningData.add(node);
            }
            else
            {
                testingData.add(node);
            }
            settedCountries.put(country, settedCountries.get(country)+1);
        }
    }
}
