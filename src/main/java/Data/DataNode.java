package Data;

import java.util.ArrayList;
import java.util.List;

public class DataNode {
    public String date;
    public List<String> topics;
    public List<String> people;
    public List<String> organizations;
    public List<String> exchanges;
    public String title;

    public String label;
    public String body;
    public List<String> stemmedWords;
    public List<String> keyWords;

    public DataNode(String body, String label)
    {
        this.body = body;
        this.label = label;
        topics = new ArrayList<>();
        people = new ArrayList<>();
        organizations = new ArrayList<>();
        exchanges = new ArrayList<>();
        stemmedWords = new ArrayList<>();
    }

    public DataNode()
    {
        topics = new ArrayList<>();
        people = new ArrayList<>();
        organizations = new ArrayList<>();
        exchanges = new ArrayList<>();
        stemmedWords = new ArrayList<>();
        keyWords = new ArrayList<>();
    }
}