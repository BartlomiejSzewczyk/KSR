import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractionManager {
    private Map<List<String>, String> learningData;
    private Map<List<String>, String> testingData;
    private ExtractionDataPreparer dataPreparer;

    public ExtractionManager(int percentToLearn)
    {
        Map<String, String> learningDataBefore = new HashMap<>();
        Map<String, String> testingDataBefore = new HashMap<>();
        XmlSerializator serializator = new XmlSerializator();
        DeserializedDataContainer dataContainer = serializator.readXML(serializator.getAllFilesNumbers());
        this.dataPreparer= new ExtractionDataPreparer(dataContainer, learningDataBefore, testingDataBefore, percentToLearn);

        learningData = deleteStopwords(learningDataBefore);
        testingData = deleteStopwords(testingDataBefore);
    }

    public ExtractionDataPreparer getDataPreparer() {
        return dataPreparer;
    }

    public Map<List<String>, String> getLearningData() {
        return learningData;
    }

    public Map<List<String>, String> getTestingData() {
        return testingData;
    }

    public Map<List<String>, String> deleteStopwords(Map<String, String> dataContainer)
    {
        StopwordsDeleter deleter = new StopwordsDeleter();

        return deleter.deleteStopwords(dataContainer);

    }

}
