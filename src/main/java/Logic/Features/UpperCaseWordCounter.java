package Logic.Features;

import java.util.*;

public class UpperCaseWordCounter implements IFeature {

    public double count(List<String> listOfWords) {
        double howManyUpperCase = 0;
        for (String word : listOfWords) {
            if (Character.isUpperCase(word.charAt(0))) {
                howManyUpperCase++;
            }
        }

        return (double)howManyUpperCase;
    }

    public Map<String, Integer> count(Map<List<String>, String> data)
    {
        Map<String, Integer> howManyInCountry = new HashMap<>()
        {{
            put("usa", 0);
            put("canada", 0);
            put("japan", 0);
            put("uk", 0);
            put("france", 0);
            put("west-germany", 0);
        }};

        Map<String, Integer> howManyTimes = new HashMap<>()
        {{
            put("usa", 0);
            put("canada", 0);
            put("japan", 0);
            put("uk", 0);
            put("france", 0);
            put("west-germany", 0);
        }};

        double howMany = 0;
        for(List<String> key : data.keySet())
        {
            howMany = count(key);
            howManyInCountry.put(data.get(key), howManyInCountry.get(data.get(key)) + (int)howMany);
            howManyTimes.put(data.get(key), howManyTimes.get(data.get(key)) + 1);
        }

        for(String key : howManyInCountry.keySet())
        {
            howManyInCountry.put(key, howManyInCountry.get(key)/howManyTimes.get(key));
        }

        return howManyInCountry;
    }
}
