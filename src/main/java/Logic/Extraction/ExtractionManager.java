package Logic.Extraction;

import Data.DataNode;
import Data.DeserializedDataContainer;
import Data.XmlSerializator;

import java.util.ArrayList;
import java.util.List;

public class ExtractionManager {
    private List<DataNode> learningData;
    private List<DataNode> testingData;
    private List<List<String>> learningDataWords;

    public List<List<String>> getKeyWords() {
        return keyWords;
    }

    private List<List<String>> keyWords;

    public ExtractionManager(int percentToLearn)
    {
        learningData = new ArrayList<>();
        testingData = new ArrayList<>();
        learningDataWords = new ArrayList<>();
        keyWords = new ArrayList<>();
        DeserializedDataContainer dataContainer = readDataFromXml();
        splitDataToLearnAndTest(dataContainer, percentToLearn);
        deleteStopwords(learningData);
        deleteStopwords(testingData);

        DataStemmer stemmer = new DataStemmer();
        stemmer.stemmizeData(learningData);
        stemmer.stemmizeData(testingData);
        AssignKeyWords();

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

    public void createLearningDataWords(String country)
    {
        learningDataWords.clear();
        for(int i = 0; i < learningData.size(); ++i){
            if(learningData.get(i).label.equals(country)){
                List<String> temp = new ArrayList<>(learningData.get(i).stemmedWords);
                learningDataWords.add(temp);
            }
        }
    }

    public void CreateKeyWordsList(List<String> wordList){
        List<String> tempList = new ArrayList<>(wordList);
        keyWords.add(tempList);
    }

    public void AssignKeyWords(){
        for(int i = 0; i < learningData.size(); ++i){
            learningData.get(i).keyWords = this.keyWords;
        }
        for(int i = 0; i < testingData.size(); ++i){
            testingData.get(i).keyWords = this.keyWords;
        }
    }

}
