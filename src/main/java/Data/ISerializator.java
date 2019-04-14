package Data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.List;

public interface ISerializator {
    Document makeDocumentByFileNumber(String fileNumber);
    void readDataFromNodeList(NodeList nodeList, String label) throws NoSuchFieldException, IllegalAccessException;
    DeserializedDataContainer readXML(List<String> filesNumbers, String label);
    List<String> getAllFilesNumbers();
}
