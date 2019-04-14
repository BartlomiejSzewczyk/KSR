package Logic;


import Data.DataNode;
import Logic.Metrics.IMetric;
import Logic.SimilarityMeasures.ISimilarityMeasure;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class KnnAlgorithm {
    private int kValue;

    public KnnAlgorithm(int kValue)
    {
        this.kValue = kValue;
    }

    public String chooseCountry(FeatureVectorWithCountry currentVector, List<FeatureVectorWithCountry> knownVectors, IMetric metric)
    {
        List<FeatureVectorWithCountry> sortedByDistance = knownVectors;
        sortedByDistance.sort(Comparator.comparingDouble(knownVector ->
                metric.calculateDistance(currentVector.featureVector, knownVector.featureVector)));

        List<FeatureVectorWithCountry> firstKVectors = sortedByDistance.stream().limit(kValue).collect(Collectors.toList());
        var sortedGroupByCountry = firstKVectors.stream().collect(Collectors.groupingBy(fv -> fv.country, Collectors.counting()));
        String chosenCountry = sortedGroupByCountry.entrySet().stream().max((e1, e2) -> e1.getValue()>e2.getValue() ? 1 : -1).get().getKey();

        return chosenCountry;
    }

    public String chooseCountry(DataNode currentNode, List<DataNode> knownNodes, List<ISimilarityMeasure> measures)
    {
        Map<DataNode, Double> measureResult = new HashMap<>();
        for(DataNode node : knownNodes)
        {
            double sumOfMeasureResult = 0;
            for(ISimilarityMeasure measure : measures)
            {
                sumOfMeasureResult += measure.countSimilarity(currentNode, (DataNode) node);
            }
            measureResult.put(node, sumOfMeasureResult);
        }
        Map<DataNode, Double> sorted = measureResult.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));

        List<DataNode> firstKNodes = sorted.keySet().stream().limit(kValue).collect(toList());

        var sortedGroupByCountry = firstKNodes.stream().collect(Collectors.groupingBy(fv -> fv.label, Collectors.counting()));
        String chosenCountry = sortedGroupByCountry.entrySet().stream().max((e1, e2) -> e1.getValue()>e2.getValue() ? 1 : -1).get().getKey();

        return chosenCountry;
    }

    private double countSimilarity(DataNode currentNode, DataNode secondNode, List<ISimilarityMeasure> measures)
    {
        double sumOfMeasureResult = 0;
        for(ISimilarityMeasure measure : measures)
        {
            sumOfMeasureResult += measure.countSimilarity(currentNode, secondNode);
        }
        return sumOfMeasureResult;
    }
}
