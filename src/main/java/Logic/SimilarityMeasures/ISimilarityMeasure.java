package Logic.SimilarityMeasures;

import Data.DataNode;

public interface ISimilarityMeasure {
    double countSimilarity(DataNode first, DataNode second);
}
