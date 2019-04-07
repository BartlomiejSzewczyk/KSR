import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractionDataPreparer {
    private DeserializedDataContainer dataContainer;
    private int percentToLearn;

    public ExtractionDataPreparer(DeserializedDataContainer dataContainer, Map<String, String> learningData,
                                  Map<String, String> testingData, int percentToLearn)
    {
        this.dataContainer = dataContainer;
        this.percentToLearn = percentToLearn;
        splitDataToLearnAndTest(dataContainer, learningData, testingData, percentToLearn);
    }

    public DeserializedDataContainer getDataContainer() {
        return dataContainer;
    }

    public void splitDataToLearnAndTest(DeserializedDataContainer dataContainer, Map<String, String> learningData,
                                        Map<String, String> testingData, int percent)
    {
        Map<String, Integer> countCountries = new HashMap<>();
        for(String key : dataContainer.getDeserializedData().keySet()){
            countCountries.putIfAbsent(dataContainer.getDeserializedData().get(key), 0);
            countCountries.put(dataContainer.getDeserializedData().get(key),
                    countCountries.get(dataContainer.getDeserializedData().get(key))+1);
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

        for(String key : dataContainer.getDeserializedData().keySet()){
            String country = dataContainer.getDeserializedData().get(key);
            if(settedCountries.get(country) < countCountries.get(country)*percent/100)
            {
                learningData.put(key, country);
            }
            else
            {
                testingData.put(key, country);
            }
            settedCountries.put(country, settedCountries.get(country)+1);
        }
    }
}
