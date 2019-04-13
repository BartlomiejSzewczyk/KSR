package Logic.Classificators;

import Data.DataNode;
import Logic.FeatureVectorCreator;
import Logic.FeatureVectorWithCountry;
import Logic.Features.IFeature;
import Logic.KnnAlgorithm;
import Logic.Metrics.IMetric;

import java.util.List;

public class FeatureClassificator {
    private IMetric metric;
    private KnnAlgorithm algorithm;
    private List<IFeature> chosenFeatures;
    private List<FeatureVectorWithCountry> vectors;
    FeatureVectorCreator creator;

    public FeatureClassificator(List<DataNode> nodes, List<IFeature> chosenFeatures, int kValue, IMetric metric)
    {
        this.algorithm = new KnnAlgorithm(kValue);
        this.metric = metric;
        this.chosenFeatures = chosenFeatures;
        creator = new FeatureVectorCreator();

        makeColdStart(nodes);
    }

    public void makeColdStart(List<DataNode> nodes)
    {
        this.vectors = creator.createListOfFeatureVectors(nodes, chosenFeatures);
    }

    public String classify(DataNode node)
    {
        FeatureVectorWithCountry vector = creator.createFeatureVector(node, chosenFeatures);
        return algorithm.chooseCountry(vector, vectors, metric);
    }


}
