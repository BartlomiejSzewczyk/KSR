package Logic;

import java.util.List;

public class FeatureVectorWithCountry {
    public String country;
    public List<Double> featureVector;

    public FeatureVectorWithCountry(String country, List<Double> featureVector)
    {
        this.country = country;
        this.featureVector = featureVector;
    }

}
