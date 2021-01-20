package eg.edu.alexu.csd.oop.db.cs58.storage;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import java.io.*;

public class XML {

    private void Datatype(String dataBase, String table, String[] columnsName, String[] datatypeName) {
        CreateNewFile File =  new CreateNewFile();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement(table);
            doc.appendChild(rootElement);

            Element datatype = doc.createElement("Datatype");
            rootElement.appendChild(datatype);
            for(int i=0; i<datatypeName.length; i++){
                Element element = doc.createElement(columnsName[i]);
                element.appendChild(doc.createTextNode(datatypeName[i]));
                datatype.appendChild(element);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(File.fileCreated(dataBase, table+"-DT"));
            transformer.transform(source, result);


            //Output on console
            /*StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);*/
        } catch (Exception e) {
            System.out.println("Error in dataType method in the XML ");
           // e.printStackTrace();
        }
    }

    public void Write(String dataBase, String table, Object Array[][], String[] columnsName, String[] datatypeName) {
        CreateNewFile File =  new CreateNewFile();
        Datatype(dataBase,table,columnsName,datatypeName);

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement(table);
            doc.appendChild(rootElement);
            for(int i=0; i<Array.length; i++){
                Element entity = doc.createElement("Entity");
                rootElement.appendChild(entity);
                for(int j=0; j<Array[i].length; j++){
                    Element element = doc.createElement(columnsName[j]);
                    if(Array[i][j]==(null)){
                        element.appendChild(doc.createTextNode("null"));
                    }else{
                        element.appendChild(doc.createTextNode(Array[i][j].toString()));
                    }
                    entity.appendChild(element);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(File.fileCreated(dataBase, table));
            transformer.transform(source, result);


            //Output on console
            /*StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);*/
        } catch (Exception e) {
            System.out.println("Exception : Error in writing the xml file ");
          //  e.printStackTrace();
        }
    }
    public Object[][] Read(String path) {
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            int columnCount = 0;
            NodeList rows = doc.getElementsByTagName("Entity");
            Node tempC = rows.item(0);
            if(tempC == null){
                columnCount = 0;
            }else{
                NodeList columns = tempC.getChildNodes();
                columnCount = columns.getLength();
            }

            Object Array [][]= new Object[rows.getLength()][columnCount];

            for (int i = 0; i < rows.getLength(); i++) {
                Node nNode = rows.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList elements = nNode.getChildNodes();
                    for(int j = 0; j<elements.getLength(); j++){
                        Node info = elements.item(j);
                        Array[i][j]=info.getTextContent();
                    }
                }
            }
            return Array;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] getColumnsName(String path) {

        return readOperation(path,0);

    }

    public String[] getDataType(String path){
        return readOperation(path,1);
    }

    private String[] readOperation (String path,int i){
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList rows = doc.getElementsByTagName("Datatype");
            Node tempC = rows.item(0);
            NodeList columns = tempC.getChildNodes();

            String Array []= new String[columns.getLength()];

            Node nNode = rows.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                NodeList elements = nNode.getChildNodes();
                for(int j = 0; j<elements.getLength(); j++){
                    Node info = elements.item(j);
                    if (i==0) Array[j]=info.getNodeName();
                    else Array[j]=info.getTextContent();
                }
            }
            return Array;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
