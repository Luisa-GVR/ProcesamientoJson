import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class PC {
    public static void main(String[] args) {
        File xmlFile = new File("car_sales.xml");

        try {
            String json = convertXMLToJSON(xmlFile);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String convertXMLToJSON(File xmlFile) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        Element root = document.getDocumentElement();
        return convertElementToJSON(root).toString();
    }

    private static Object convertElementToJSON(Element element) {
        NodeList childNodes = element.getChildNodes();

        if (childNodes.getLength() == 1 && childNodes.item(0) instanceof org.w3c.dom.Text) {
            return childNodes.item(0).getTextContent();
        }

        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode instanceof Element) {
                Element childElement = (Element) childNode;
                String childElementName = childElement.getNodeName();
                if (jsonObject.has(childElementName)) {
                    if (!(jsonObject.get(childElementName) instanceof org.json.JSONArray)) {
                        Object existingValue = jsonObject.get(childElementName);
                        org.json.JSONArray jsonArray = new org.json.JSONArray();
                        jsonArray.put(existingValue);
                        jsonObject.put(childElementName, jsonArray);
                    }
                    org.json.JSONArray jsonArray = (org.json.JSONArray) jsonObject.get(childElementName);
                    jsonArray.put(convertElementToJSON(childElement));
                } else {
                    jsonObject.put(childElementName, convertElementToJSON(childElement));
                }
            }
        }

        return jsonObject;
    }
}
