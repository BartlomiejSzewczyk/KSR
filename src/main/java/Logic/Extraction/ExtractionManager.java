package Logic.Extraction;

import Data.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ExtractionManager {
    private List<DataNode> learningData;
    private List<DataNode> testingData;
    private List<List<String>> learningDataWords;

    public ExtractionManager(int percentToLearn, ISerializator serializator, String label) throws NoSuchFieldException, IllegalAccessException {
        learningData = new ArrayList<>();
        testingData = new ArrayList<>();
        learningDataWords = new ArrayList<>();
        keyWords = new ArrayList<>();

        DeserializedDataContainer dataContainer = readDataFromXml(serializator, label);
        splitDataToLearnAndTest(dataContainer, percentToLearn);
        deleteStopwords(learningData);
        deleteStopwords(testingData);

        DataStemmer stemmer = new DataStemmer();
        stemmer.stemmizeData(learningData);
        stemmer.stemmizeData(testingData);
    }
    public List<List<String>> getKeyWords() {
        return keyWords;
    }

    private List<List<String>> keyWords;

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

    public DeserializedDataContainer readDataFromXml(ISerializator serializator, String label) throws NoSuchFieldException, IllegalAccessException {
        LabelsTypes lt = new LabelsTypes();
        Field f = lt.getClass().getDeclaredField(label);
        f.setAccessible(true);
        LabelsTypes.chosen = (List<String>)f.get(lt);

        DeserializedDataContainer dataContainer = serializator.readXML(serializator.getAllFilesNumbers(), label);

        return dataContainer;
    }

    public void splitDataToLearnAndTest(DeserializedDataContainer dataContainer, int percentToLearn)
    {
        ExtractionDataPreparer dataPreparer= new ExtractionDataPreparer(dataContainer, percentToLearn);
        dataPreparer.splitDataToLearnAndTest(learningData, testingData);
    }

    public void createLearningDataWords(String label)
    {
        learningDataWords.clear();
        for(int i = 0; i < learningData.size(); ++i){
            if(learningData.get(i).label.equals(label)){
                List<String> temp = new ArrayList<>(learningData.get(i).stemmedWords);
                learningDataWords.add(temp);
            }
        }
    }

    public void CreateKeyWordsList(List<String> wordList){
        List<String> tempList = new ArrayList<>(wordList);
        keyWords.add(tempList);
    }
}