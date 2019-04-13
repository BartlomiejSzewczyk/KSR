package Logic.Metrics;

import java.util.List;

public class ChebyshevMetric implements IMetric {
    @Override
    public double calculateDistance(List<Double> firstVector, List<Double> secondVector) {
        if (firstVector.size() != secondVector.size())
            throw new IllegalArgumentException("Arrays have different sizes");

        double distance = 0.0;
        for (int i = 0; i < firstVector.size(); i++) {
            double tempDistance = Math.abs(firstVector.get(i) - secondVector.get(i));
            if (distance < tempDistance)
                distance = tempDistance;
        }

        return distance;
    }
}
