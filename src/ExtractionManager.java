import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractionManager {
    private Map<String, String> learnDataBeforeDel;
    private Map<String, String> testDataBeforeDel;

    private Map<List<String>, String> learningData;
    private Map<List<String>, String> testingData;

    public ExtractionManager(int percentToLearn)
    {
        DeserializedDataContainer dataContainer = readDataFromXml();
        splitDataToLearnAndTest(dataContainer, percentToLearn);
        learningData = deleteStopwords(learnDataBeforeDel);
        testingData = deleteStopwords(testDataBeforeDel);
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

    public DeserializedDataContainer readDataFromXml()
    {
        XmlSerializator serializator = new XmlSerializator();
        DeserializedDataContainer dataContainer = serializator.readXML(serializator.getAllFilesNumbers());

        return dataContainer;
    }

    public void splitDataToLearnAndTest(DeserializedDataContainer dataContainer, int percentToLearn)
    {
        learnDataBeforeDel = new HashMap<>();
        testDataBeforeDel = new HashMap<>();
        ExtractionDataPreparer dataPreparer= new ExtractionDataPreparer(dataContainer, percentToLearn);
        dataPreparer.splitDataToLearnAndTest(learnDataBeforeDel, testDataBeforeDel);
    }

}
