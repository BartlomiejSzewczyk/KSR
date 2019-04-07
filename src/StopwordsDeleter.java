import java.io.*;
import java.util.*;

public class StopwordsDeleter {

    public List<String> englishStopwords;
    static String fileWithStopwords = "resources/english_stopwords_large.txt";

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
        ArrayList<String> listOfWords = new ArrayList<>();
        for(String key : dataContainer.keySet())
        {
            listOfWords.clear();
            listOfWords.addAll(Arrays.asList(key.split("\\s+")));
            listOfWords.removeAll(englishStopwords);
            wordsWithCountries.put(listOfWords, dataContainer.get(key));
        }

        return wordsWithCountries;
    }
}
