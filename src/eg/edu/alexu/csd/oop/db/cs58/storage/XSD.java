package eg.edu.alexu.csd.oop.db.cs58.storage;

import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XSD implements XsdValidation {

     XsdCreator x=new XsdCreator();
//    public void checks(){
//        boolean isValid = isValidTable("D://xml/src/schema2.xml", "D://xml/NewFile.xsd");
//        if (isValid) {
//            System.out.println("NewFile.xml" + " is valid against " + "schema2.xsd");
//        } else {
//            System.out.println("NewFile.xml" + " is not valid against " + "schema2.xsd");
//        }
//    }

    @Override
    public void createValidationFile(String tableName, String path, String[] columns, String[] datatypes) {
       for(int i =  0 ; i< datatypes.length ; i++){
           if(datatypes[i].equalsIgnoreCase("varchar")){
               datatypes[i] =  "string";
           }
        }
        x.writeSchema( tableName,  path,  columns,  datatypes);
    }

   @Override
    public boolean isValidTable(String xmlFile , String validationFile){
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(validationFile));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlFile)));
        } catch (IOException e){
            System.out.println("Exception from xsd class: "+e.getMessage());
            return false;
        }catch(SAXException e1) {
            System.out.println("SAX Exception from xsd class: " + e1.getMessage());
            return false;
        }catch (NullPointerException e){
            System.out.println("Caught null pointer exception in the xsd class ");
            return false;
        }catch (Exception e)
        {
            System.out.println("an exception occurred in the xsd class ");
            return false;
        }

        return true;

    }
}
