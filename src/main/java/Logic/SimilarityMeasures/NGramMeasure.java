package Logic.SimilarityMeasures;

import Data.DataNode;

import java.util.List;

public class NGramMeasure implements ISimilarityMeasure{
    private int nValue;

    public NGramMeasure(int nValue)
    {
        this.nValue = nValue;
    }

    @Override
    public double countSimilarity(DataNode first, DataNode second) {
            List<String> firstNodeWords = first.stemmedWords;
            List<String> secondNodeWords = second.stemmedWords;
            int howManyFound = 0;

            int nLength = firstNodeWords.size() - nValue;
            for (int i = 0; i < nLength; i++)
            {
                List<String> subList = firstNodeWords.subList(i, i+nValue);
                if (checkIfContains(subList, secondNodeWords))
                {
                    howManyFound++;
                }
            }

            return (double) howManyFound / (nLength - nValue + 1);
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
