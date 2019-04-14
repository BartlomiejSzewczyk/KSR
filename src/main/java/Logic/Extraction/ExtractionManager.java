package Logic.Extraction;

import Data.DataNode;
import Data.DeserializedDataContainer;
import Data.XmlSerializator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExtractionManager {
    private List<DataNode> learningData;
    private List<DataNode> testingData;
    private List<List<String>> learningDataWords;

    public ExtractionManager(int percentToLearn)
    {
        learningData = new ArrayList<>();
        testingData = new ArrayList<>();
        learningDataWords = new ArrayList<>();
        DeserializedDataContainer dataContainer = readDataFromXml();
        splitDataToLearnAndTest(dataContainer, percentToLearn);
        deleteStopwords(learningData);
        deleteStopwords(testingData);

        DataStemmer stemmer = new DataStemmer();
        stemmer.stemmizeData(learningData);
        stemmer.stemmizeData(testingData);
        createLearningDataWords();

    }

    public List<DataNode> getLearningData() {
        return learningData;
    }

    public List<DataNode> getTestingData() {
        return testingData;
    }

    public List<List<String>> getLearningDataWords(){
        return learningDataWords;
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

    public void createLearningDataWords()
    {
        for(int i = 0; i < learningData.size(); ++i){
            List<String> temp = new ArrayList<>(learningData.get(i).words);
            learningDataWords.add(temp);
        }
    }

}
