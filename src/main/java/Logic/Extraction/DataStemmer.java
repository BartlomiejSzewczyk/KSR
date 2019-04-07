package Logic.Extraction;

import Data.DataNode;
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

    public void stemmizeData(List<DataNode> data)
    {
        for(DataNode node : data)
        {
            node.words = stemmizeListOfWord(node.words);

        }
    }
}
