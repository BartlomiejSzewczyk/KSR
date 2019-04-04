import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello World!");
        List<String> bodyList = new ArrayList<>();
        Map<String, String> learningMap = new HashMap<>();
        readXML(bodyList, "00"); // number: 00-21
        readXML(learningMap, "00");
        readXML(learningMap, "01");
        readXML(learningMap, "02");
        readXML(learningMap, "03");
        //region count countries
        Map<String, Integer>tempMap = new HashMap<>();
        for(String key : learningMap.keySet()){
            tempMap.putIfAbsent(learningMap.get(key), 0);
            tempMap.put(learningMap.get(key), tempMap.get(learningMap.get(key))+1);
        }
        //endregion
        CountOwnNames ownNames = new CountOwnNames();
        ownNames.Count(learningMap);
    }
    public static void readXML(List<String> bodyList, String fileNumber){
        try {
            File fXmlFile = new File("reuters21578-xml/reut2-0" + fileNumber + ".xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("REUTERS");
            for(int i = 0; i < nList.getLength(); ++i){
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) node;
                    if(eElement.getElementsByTagName("BODY").item(0) != null){
                        bodyList.add(eElement.getElementsByTagName("BODY").item(0).getTextContent());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readXML(Map<String, String> learningMap, String fileNumber){
        try {
            File fXmlFile = new File("reuters21578-xml/reut2-0" + fileNumber + ".xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("REUTERS");
            for(int i = 0; i < nList.getLength(); ++i){
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) node;
                    String tempPlace = "";
                    if(eElement.getElementsByTagName("PLACES").item(0).getTextContent() != null && eElement.getElementsByTagName("PLACES").item(0).getTextContent() != ""){
                        tempPlace = eElement.getElementsByTagName("PLACES").item(0).getFirstChild().getTextContent();
                    }
                    if(eElement.getElementsByTagName("BODY").item(0) != null && eElement.getElementsByTagName("PLACES").item(0).getTextContent() != "" &&
                            eElement.getElementsByTagName("PLACES").item(0).getFirstChild().getTextContent() == eElement.getElementsByTagName("PLACES").item(0).getLastChild().getTextContent() &&
                            (tempPlace.equals("usa") || tempPlace.equals("france") || tempPlace.equals("west-germany") || tempPlace.equals("canada") || tempPlace.equals("uk") || tempPlace.equals("japan"))
                    ){
                        learningMap.put(eElement.getElementsByTagName("BODY").item(0).getTextContent(), eElement.getElementsByTagName("PLACES").item(0).getFirstChild().getTextContent());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
