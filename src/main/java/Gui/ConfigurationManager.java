package Gui;

import Data.DataNode;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class ConfigurationManager {
    public Configuration configuration;

    public ConfigurationManager(Configuration configuration)
    {
        this.configuration = configuration;
    }
    public ConfigurationManager(){};

    public void saveConfigurationToFile(String fileName) {

        Map<String,String> texts = new LinkedHashMap<>();
        LinkedList<JSONObject> textList = new LinkedList<>();
        for (DataNode node : configuration.getColdStart()) {
            JSONObject textDetails = new JSONObject();
            texts.put(node.body, node.label);
            JSONObject textObject = new JSONObject();
            textObject.put("text", textDetails);
            textList.add(textObject);
        }
        try (FileWriter file = new FileWriter(fileName)) {

            Gson gson = new Gson();

            // Convert the ordered map into an ordered string.
            String json = gson.toJson(texts, LinkedHashMap.class);
            file.write(json);
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readConfigurationFromFile(String fileName){
       LinkedList<DataNode> nodes = new LinkedList<>();
       BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Map<String, String> json = gson.fromJson(bufferedReader, LinkedHashMap.class);

        for(Map.Entry e : json.entrySet())
        {
            nodes.add(new DataNode((String) e.getKey(), (String) e.getValue()));
        }
        configuration =  Configuration.builder().coldStart(nodes).build();
    }

    private DataNode parseEmployeeObject(JSONObject text)
    {
        JSONObject textObject = (JSONObject) text.get("text");
        String label = (String) textObject.get("label");
        String body = (String) textObject.get("body");

        return new DataNode(body, label);
    }
}
