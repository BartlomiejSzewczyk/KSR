package Logic;

import java.util.List;

public class FeatureVectorWithLabel {
    public String label;
    public List<Double> featureVector;

    public FeatureVectorWithLabel(String label, List<Double> featureVector)
    {
        this.label = label;
        this.featureVector = featureVector;
    }

}
