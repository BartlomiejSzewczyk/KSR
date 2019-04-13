package Logic.Features;

import java.util.List;
import java.util.Map;

public interface IFeature {
    double count(List<String> listOfWords);
    Map<String, Integer> count(Map<List<String>, String> data);
}
