package Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataNode {
    public String date;
    public List<String> topics;
    public String place;
    public List<String> people;
    public List<String> organizations;
    public List<String> exchanges;
    public String title;
    public String body;
    public List<String> words;

    public DataNode(String body, String place)
    {
        this.body = body;
        this.place = place;
        topics = new ArrayList<>();
        people = new ArrayList<>();
        organizations = new ArrayList<>();
        exchanges = new ArrayList<>();
        words = new ArrayList<>();
    }

    public DataNode()
    {
        topics = new ArrayList<>();
        people = new ArrayList<>();
        organizations = new ArrayList<>();
        exchanges = new ArrayList<>();
        words = new ArrayList<>();
    }
}
