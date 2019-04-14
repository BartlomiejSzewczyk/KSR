package Logic;

import Data.DataNode;
import Logic.Features.IFeature;

import java.util.ArrayList;
import java.util.List;

public class FeatureVectorCreator {
    public List<FeatureVectorWithLabel> createListOfFeatureVectors(List<DataNode> nodes,
                                                                   List<IFeature> features)
    {
        List<FeatureVectorWithLabel> vectors = new ArrayList<FeatureVectorWithLabel>();

        for(DataNode node : nodes)
        {
            FeatureVectorWithLabel vector = createFeatureVector(node, features);
            vectors.add(vector);
        }

        return vectors;
    }

    public FeatureVectorWithLabel createFeatureVector(DataNode node, List<IFeature> features)
    {
        List<Double> featureVectors = new ArrayList<>();
        for (IFeature feature : features)
        {
            featureVectors.add(feature.count(node.stemmedWords));
        }
        return new FeatureVectorWithLabel(node.label, featureVectors);
    }
}
