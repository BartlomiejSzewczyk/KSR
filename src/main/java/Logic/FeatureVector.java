package Logic;

import java.util.List;

public class FeatureVector{
    public String country;
    public List<Double> featureVector;

    public FeatureVector(String country, List<Double> featureVector)
    {
        this.country = country;
        this.featureVector = featureVector;
    }

}
