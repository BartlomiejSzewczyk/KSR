package Logic.Classificators;

import Data.DataNode;
import Logic.KnnAlgorithm;
import Logic.SimilarityMeasures.ISimilarityMeasure;

import java.util.List;

public class SimilarityClassificator implements  IClassificator {
    private  List<ISimilarityMeasure> measures;
    private KnnAlgorithm algorithm;
    private List<DataNode> nodes;

    public SimilarityClassificator(List<DataNode> nodes, List<ISimilarityMeasure> measures, int kValue)
    {
        this.measures = measures;
        this.algorithm = new KnnAlgorithm(kValue);
        this.nodes = nodes;
    }

    @Override
    public String classify(DataNode node) {
        String result = algorithm.chooseLabel(node, nodes, measures);
        return result;
    }
}
