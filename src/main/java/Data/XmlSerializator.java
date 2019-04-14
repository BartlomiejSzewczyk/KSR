package Data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XmlSerializator {

    public DeserializedDataContainer dataContainer;

    public XmlSerializator()
    {
        dataContainer = new DeserializedDataContainer();
    }

    public Document makeDocumentByFileNumber(String fileNumber)
    {
        Document doc = null;
        try {
            File fXmlFile = new File("src/main/java/Data/resources/reuters21578-xml/reut2-0" + fileNumber + ".xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    public boolean checkIfElementIsCorrect(Element element)
    {
        ArrayList<String> places = new ArrayList<>(Arrays.asList("usa", "france", "west-germany", "canada", "uk", "japan"));
        String tempPlace = "";
        var placeValue = element.getElementsByTagName("PLACES").item(0);

        if(placeValue.getTextContent() != null && placeValue.getTextContent() != ""){
            tempPlace = placeValue.getFirstChild().getTextContent();
        }

        if(element.getElementsByTagName("BODY").item(0) != null && placeValue.getTextContent() != "" &&
                placeValue.getFirstChild().getTextContent() == placeValue.getLastChild().getTextContent() &&
                places.contains(tempPlace)
        )
            return true;

        return false;
    }

    public void readDataFromNodeList(NodeList nodeList)
    {
        for(int i = 0; i < nodeList.getLength(); ++i)
        {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {

                Element element = (Element) node;
                if(checkIfElementIsCorrect(element))
                {
                    DataNode dataNode = new DataNode();
                    dataNode.date = element.getElementsByTagName("DATE").item(0).getTextContent();
                    for(int j=0; j<element.getElementsByTagName("TOPICS").getLength(); j++)
                        dataNode.topics.add(element.getElementsByTagName("TOPICS").item(j).getTextContent());

                    dataNode.label = element.getElementsByTagName("PLACES").item(0).getFirstChild().getTextContent();

                    for(int j=0; j<element.getElementsByTagName("PEOPLE").getLength(); j++)
                        dataNode.people.add(element.getElementsByTagName("PEOPLE").item(j).getTextContent());

                    for(int j=0; j<element.getElementsByTagName("ORGS").getLength(); j++)
                        dataNode.organizations.add(element.getElementsByTagName("ORGS").item(j).getTextContent());

                    for(int j=0; j<element.getElementsByTagName("EXCHANGES").getLength(); j++)
                        dataNode.exchanges.add(element.getElementsByTagName("EXCHANGES").item(j).getTextContent());

                    dataNode.title = element.getElementsByTagName("TITLE").item(0).getTextContent();

                    dataNode.body = element.getElementsByTagName("BODY").item(0).getTextContent();

                    dataContainer.setDeserializedData(dataNode);
                }
            }
        }
    }

    public DeserializedDataContainer readXML(String fileNumber){
        try {
                Document doc = makeDocumentByFileNumber(fileNumber);
                NodeList nodeList = doc.getElementsByTagName("REUTERS");
                readDataFromNodeList(nodeList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  this.dataContainer;
    }

    public DeserializedDataContainer readXML(List<String> filesNumbers){

        for (String file : filesNumbers)
        {
            this.dataContainer.setDeserializedData(readXML(file).getDeserializedData());
        }

        return this.dataContainer;
    }

    public List<String> getAllFilesNumbers()
    {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 22; i++)
        {
            if(i<=9)
                list.add("0" + i);
            else
                list.add(Integer.toString(i));
        }

        return list;
    }


}
