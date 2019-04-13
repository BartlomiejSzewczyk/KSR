package Logic.Metrics;

import java.util.List;

public interface IMetric {

    double calculateDistance(List<Double> firstVector, List<Double> secondVector);
}
