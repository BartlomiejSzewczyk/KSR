package Logic.Extraction;

import Data.DataNode;
import Data.DeserializedDataContainer;
import Data.XmlSerializator;

import java.util.ArrayList;
import java.util.List;

public class ExtractionManager {
    private List<DataNode> learningData;
    private List<DataNode> testingData;

    public ExtractionManager(int percentToLearn)
    {
        learningData = new ArrayList<>();
        testingData = new ArrayList<>();
        DeserializedDataContainer dataContainer = readDataFromXml();
        splitDataToLearnAndTest(dataContainer, percentToLearn);
        deleteStopwords(learningData);
        deleteStopwords(testingData);

        DataStemmer stemmer = new DataStemmer();
        stemmer.stemmizeData(learningData);
        stemmer.stemmizeData(testingData);

    }

    public List<DataNode> getLearningData() {
        return learningData;
    }

    public List<DataNode> getTestingData() {
        return testingData;
    }

    public void deleteStopwords(List<DataNode> dataContainer)
    {
        StopwordsDeleter deleter = new StopwordsDeleter();

        deleter.deleteStopwords(dataContainer);

    }

    public DeserializedDataContainer readDataFromXml()
    {
        XmlSerializator serializator = new XmlSerializator();
        DeserializedDataContainer dataContainer = serializator.readXML(serializator.getAllFilesNumbers());

        return dataContainer;
    }

    public void splitDataToLearnAndTest(DeserializedDataContainer dataContainer, int percentToLearn)
    {
        ExtractionDataPreparer dataPreparer= new ExtractionDataPreparer(dataContainer, percentToLearn);
        dataPreparer.splitDataToLearnAndTest(learningData, testingData);
    }

}
