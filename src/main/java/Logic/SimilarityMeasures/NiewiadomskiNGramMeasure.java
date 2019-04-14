package Logic.SimilarityMeasures;

import Data.DataNode;

import java.util.List;

public class NiewiadomskiNGramMeasure implements ISimilarityMeasure {

    public NiewiadomskiNGramMeasure(){}

    @Override
    public double countSimilarity(DataNode first, DataNode second) {
        List<String> firstNodeWords = first.stemmedWords;
        List<String> secondNodeWords = second.stemmedWords;
        int howManyFound = 0;
        double maxLength = firstNodeWords.size() >= secondNodeWords.size() ? firstNodeWords.size() : secondNodeWords.size();
        for(int j = 1; j < firstNodeWords.size(); j++)
        {
            for (int i = 0; i < firstNodeWords.size()-j+1; i++)
            {
                List<String> subList = firstNodeWords.subList(i, i+j);
                if (checkIfContains(subList, secondNodeWords))
                {
                    howManyFound++;
                }
            }
        }

        return (double) howManyFound*2.0/ (Math.pow(maxLength, 2) + maxLength);
    }

    private boolean checkIfContains(List<String> subList, List<String> secondNodeWords)
    {
        int i = 0;
        for(String word : secondNodeWords)
        {
            if (subList.get(i).equals(word))
            {
                i++;
                if (i == subList.size())
                    return true;
            }
            else
                i = 0;
        }

        return false;
    }
}
