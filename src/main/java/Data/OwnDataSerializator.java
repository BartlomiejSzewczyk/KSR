package Data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class OwnDataSerializator implements ISerializator {
    public DeserializedDataContainer dataContainer;

    public OwnDataSerializator()
    {
        dataContainer = new DeserializedDataContainer();
    }

    @Override
    public Document makeDocumentByFileNumber(String fileNumber) {
        Document doc = null;
        try {
            File fXmlFile = new File("src/main/java/Data/resources/ownData/data" + fileNumber + ".xml");
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

    public boolean checkIfElementIsCorrect(Element element, String label) throws NoSuchFieldException, IllegalAccessException {
        LabelsTypes lt = new LabelsTypes();
        Field f = lt.getClass().getDeclaredField(label);
        f.setAccessible(true);
        String tempLabel = "";
        var labelValue = element.getElementsByTagName(label).item(0);

        if(labelValue.getTextContent() != null && labelValue.getTextContent() != ""){
            tempLabel = labelValue.getFirstChild().getTextContent();
        }

        if(element.getElementsByTagName("BODY").item(0) != null && labelValue.getTextContent() != "" &&
                labelValue.getFirstChild().getTextContent() == labelValue.getLastChild().getTextContent() &&
                ((List<String>)f.get(lt)).contains(tempLabel)
        )
            return true;

        return false;
    }

    @Override
    public void readDataFromNodeList(NodeList nodeList, String label) throws NoSuchFieldException, IllegalAccessException{
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (checkIfElementIsCorrect(element, label)) {
                    DataNode dataNode = new DataNode();
                    dataNode.label = element.getElementsByTagName(label).item(0).getFirstChild().getTextContent();
                    dataNode.body = element.getElementsByTagName("BODY").item(0).getTextContent();

                    dataContainer.setDeserializedData(dataNode);
                }
            }
        }
    }

    public DeserializedDataContainer readXML(String fileNumber, String label){
        try {
            Document doc = makeDocumentByFileNumber(fileNumber);
            NodeList nodeList = doc.getElementsByTagName("REUTERS");
            readDataFromNodeList(nodeList, label);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  this.dataContainer;
    }

    @Override
    public DeserializedDataContainer readXML(List<String> filesNumbers, String label) {
        for (String file : filesNumbers)
        {
            this.dataContainer.setDeserializedData(readXML(file, label).getDeserializedData());
        }
        return this.dataContainer;
    }

    @Override
    public List<String> getAllFilesNumbers() {
        List<String> list = new ArrayList<>();
        list.add("");
        return list;
    }
}
