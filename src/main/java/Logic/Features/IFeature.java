package Logic.Features;

import java.util.List;
import java.util.Map;

public interface IFeature {
    public int count(List<String> listOfWords);
    public Map<String, Integer> count(Map<List<String>, String> data);
}
