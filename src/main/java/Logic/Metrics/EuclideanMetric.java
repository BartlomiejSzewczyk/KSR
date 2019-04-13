package Logic.Metrics;

import java.util.List;

public class EuclideanMetric implements IMetric{
    @Override
    public double calculateDistance(List<Double> firstVector, List<Double> secondVector) {
        if (firstVector.size() != secondVector.size())
            throw new IllegalArgumentException("Arrays have different sizes");

        double distance;
        double sum = 0.0;

        for (int i = 0; i < firstVector.size(); i++)
        {
            sum += Math.pow((firstVector.get(i) - secondVector.get(i)), 2.0);
        }
        distance = Math.sqrt(sum);
        return distance;
    }
}
