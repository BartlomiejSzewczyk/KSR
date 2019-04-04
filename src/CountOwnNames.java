import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CountOwnNames {
    public static void Count(Map<String, String> learningMap){
        List<Double> tempList = new ArrayList<>();
        for(String key : learningMap.keySet()){
            double cnt = 0.0;
            char previousChar = key.charAt(0);
            for(int i = 1; i < key.length(); ++i) {
                if (previousChar == ' ' && Character.isUpperCase(key.charAt(i))) {
                    ++cnt;
                }
                previousChar = key.charAt(i);
            }
            tempList.add(cnt/key.length()*100);//% own names in text
        }
        int x = 0;
        double y =0;
        double sum = 0;
        for(String key : learningMap.keySet()){
            if(learningMap.get(key).equals("usa")){
                sum += tempList.get(x);
                ++y;
            }
            ++x;
        }
        double average = sum/y;
    }
}
