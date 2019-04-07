package Logic.Extraction;

import org.tartarus.martin.Stemmer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStemmer {
    private Stemmer stemmer;

    public DataStemmer()
    {
        stemmer = new Stemmer();
    }

    public String stemmizeWord(String word)
    {
        stemmer.add(word.toCharArray(), word.length());
        stemmer.stem();
        return stemmer.toString();
    }

    public List<String> stemmizeListOfWord(List<String> list)
    {
        List<String> stemmizedList = new ArrayList<>();
        for(String word : list)
        {
            stemmizedList.add(stemmizeWord(word));
        }

        return stemmizedList;
    }

    public Map<List<String>, String> stemmizeData(Map<List<String>, String> data)
    {
        Map<List<String>, String> dataMap = new HashMap<>();
        for(List<String> list : data.keySet())
        {
            dataMap.put(stemmizeListOfWord(list), data.get(list));

        }

        return dataMap;
    }
}
