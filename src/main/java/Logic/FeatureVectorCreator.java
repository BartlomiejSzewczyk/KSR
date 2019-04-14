package Logic;

import Data.DataNode;
import Logic.Features.IFeature;

import java.util.ArrayList;
import java.util.List;

public class FeatureVectorCreator {
    public List<FeatureVectorWithCountry> createListOfFeatureVectors(List<DataNode> nodes,
                                                               List<IFeature> features)
    {
        List<FeatureVectorWithCountry> vectors = new ArrayList<FeatureVectorWithCountry>();

        for(DataNode node : nodes)
        {
            FeatureVectorWithCountry vector = createFeatureVector(node, features);
            vectors.add(vector);
        }

        return vectors;
    }

    public FeatureVectorWithCountry createFeatureVector(DataNode node, List<IFeature> features)
    {
        List<Double> featureVectors = new ArrayList<>();
        for (IFeature feature : features)
        {
            featureVectors.add(feature.count(node.stemmedWords));
        }
        return new FeatureVectorWithCountry(node.place, featureVectors);
    }
}
