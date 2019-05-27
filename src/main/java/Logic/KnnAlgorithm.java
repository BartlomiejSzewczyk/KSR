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

    public String chooseLabel(FeatureVectorWithLabel currentVector, List<FeatureVectorWithLabel> knownVectors, IMetric metric)
    {
        List<FeatureVectorWithLabel> sortedByDistance = knownVectors;
        sortedByDistance.sort(Comparator.comparingDouble(knownVector ->
                metric.calculateDistance(currentVector.featureVector, knownVector.featureVector)));

        List<FeatureVectorWithLabel> firstKVectors = sortedByDistance.stream().limit(kValue).collect(Collectors.toList());
        var sortedGroupByLabel = firstKVectors.stream().collect(Collectors.groupingBy(fv -> fv.label, Collectors.counting()));
        String chosenLabel = sortedGroupByLabel.entrySet().stream().max((e1, e2) -> e1.getValue()>e2.getValue() ? 1 : -1).get().getKey();

        return chosenLabel;
    }

    public String chooseLabel(DataNode currentNode, List<DataNode> knownNodes, List<ISimilarityMeasure> measures)
    {
        Map<DataNode, Double> measureResult = new LinkedHashMap<>();
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

        var sortedGroupByLabel = firstKNodes.stream().collect(Collectors.groupingBy(fv -> fv.label, Collectors.counting()));
        String chosenLabel = sortedGroupByLabel.entrySet().stream().max((e1, e2) -> e1.getValue()>e2.getValue() ? 1 : -1).get().getKey();

        return chosenLabel;
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
