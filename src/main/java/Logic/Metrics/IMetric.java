package Logic.Metrics;

import java.util.List;

public interface IMetric {

    double calculateDistance(List<Integer> firstVector, List<Integer> secondVector);
}
