package Logic.Extraction;

import java.io.*;
import java.util.*;

public class StopwordsDeleter {

    public List<String> englishStopwords;
    static String fileWithStopwords = "src/main/java/Data/resources/english_stopwords_large.txt";

    public StopwordsDeleter()
    {
        englishStopwords = new ArrayList<>();
        loadStopwordsFromFile();
    }

    private void loadStopwordsFromFile()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(fileWithStopwords))) {
            String line;
            while ((line = br.readLine()) != null) {
                englishStopwords.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<List<String>, String> deleteStopwords(Map<String, String> dataContainer)
    {
        Map<List<String>, String> wordsWithCountries = new HashMap<>();
        for(String key : dataContainer.keySet())
        {
            ArrayList<String> listOfWords = new ArrayList<>();
            listOfWords.addAll(Arrays.asList(key.replaceAll("[\\p{Punct}]", "").split("\\s+")));
            Set<String> set1 = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            set1.addAll(listOfWords);
            Set<String> set2 = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            set2.addAll(englishStopwords);
            set1.removeAll(set2);
            listOfWords.retainAll(set1);
            //listOfWords.removeAll(englishStopwords);
            wordsWithCountries.put(listOfWords, dataContainer.get(key));
        }

        return wordsWithCountries;
    }
}
