package eg.edu.alexu.csd.oop.db.cs58.storage;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;


public class XsdCreator {

    private final static String NS_PREFIX = "xs:";
    public  void writeSchema (String table,String path,String[] columns, String[] datatypes)  {

        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();

            Element schemaRoot = doc.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, NS_PREFIX+"schema");
            doc.appendChild(schemaRoot);

            NameTypeElementMaker elMaker = new NameTypeElementMaker(NS_PREFIX, doc);

            Element itemElement = elMaker.createElement("element", table);
            schemaRoot.appendChild(itemElement);

            Element complexRoot = elMaker.createElement("complexType");
            itemElement.appendChild(complexRoot);

            Element sequence = elMaker.createElement("sequence");
            complexRoot.appendChild(sequence);

            Element entity = elMaker.createElement("element","Entity");
            sequence.appendChild(entity);
            entity.setAttribute("minOccurs","0");
            entity.setAttribute("maxOccurs","unbounded");

            Element complexEntity = elMaker.createElement("complexType");
            entity.appendChild(complexEntity);

            Element sequenceEntity = elMaker.createElement("sequence");
            complexEntity.appendChild(sequenceEntity);

            Element e;
            for(int i=0;i<columns.length;i++){
                     e = elMaker.createElement("element",columns[i],NS_PREFIX+datatypes[i]);
                     sequenceEntity.appendChild(e);
            }




            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(doc);

            transformer.transform(domSource, new StreamResult(new File(path+"/"+table+".xsd")));
           //System.out.println("yes from xsd validator");


        }
        catch (FactoryConfigurationError | ParserConfigurationException | TransformerException e) {
            //handle exception
            System.out.println("Error in the xcd creator method");
          //  e.printStackTrace();
        }
    }

    /*
     * Class with methods to make it more convenient to create Element nodes with
     * namespace prefixed tagnames and with "name" and "type" attributes.
     */
    private static class NameTypeElementMaker {
        private String nsPrefix;
        private Document doc;

        public NameTypeElementMaker(String nsPrefix, Document doc) {
            this.nsPrefix = nsPrefix;
            this.doc = doc;
        }

        public Element createElement(String elementName, String nameAttrVal, String typeAttrVal) {
            Element element = doc.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, nsPrefix+elementName);
            if(nameAttrVal != null)
                element.setAttribute("name", nameAttrVal);
            if(typeAttrVal != null)
                element.setAttribute("type", typeAttrVal);
            return element;
        }

        public Element createElement(String elementName, String nameAttrVal) {
            return createElement(elementName, nameAttrVal, null);
        }

        public Element createElement(String elementName) {
            return createElement(elementName, null, null);
        }
    }
}