package Logic;


import Logic.Metrics.IMetric;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KnnAlgorithm {
    private int kValue;

    public KnnAlgorithm(int kValue)
    {
        this.kValue = kValue;
    }

    public String chooseCountry(FeatureVectorWithCountry currentVector, List<FeatureVectorWithCountry> knownVectors, IMetric metric)
    {
        List<Double> distances = new ArrayList<>();
        for(FeatureVectorWithCountry knownFeatureVector : knownVectors)
        {
            distances.add(metric.calculateDistance(currentVector.featureVector, knownFeatureVector.featureVector));
        }
        List<FeatureVectorWithCountry> sortedByDistance = knownVectors;
        sortedByDistance.sort(Comparator.comparingDouble(knownVector ->
                metric.calculateDistance(currentVector.featureVector, knownVector.featureVector)));

        List<FeatureVectorWithCountry> firstKVectors = sortedByDistance.stream().limit(kValue).collect(Collectors.toList());
        for(FeatureVectorWithCountry i : firstKVectors)
        System.out.println(i.country);
        var sortedGroupByCountry = firstKVectors.stream().collect(Collectors.groupingBy(fv -> fv.country, Collectors.counting()));
        String chosenCountry = sortedGroupByCountry.entrySet().stream().max((e1, e2) -> e1.getValue()>e2.getValue() ? 1 : -1).get().getKey();

        return chosenCountry;
    }
}
