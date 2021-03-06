package Logic.Metrics;

import java.util.List;

public class ManhattanMetric implements IMetric {
    @Override
    public double calculateDistance(List<Double> firstVector, List<Double> secondVector) {
        if (firstVector.size() != secondVector.size())
            throw new IllegalArgumentException("Arrays have different sizes");

        double distance = 0.0;

        for (int i = 0; i < firstVector.size(); i++)
        {
            distance += Math.abs(firstVector.get(i) - secondVector.get(i));
        }

        return distance;
    }
}
