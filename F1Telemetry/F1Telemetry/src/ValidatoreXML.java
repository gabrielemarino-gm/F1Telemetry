import java.io.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;


/**
 *
 * @author Gabriele
 */
public class ValidatoreXML 
{     
    public static boolean valida(String xml, String fileSchema, boolean file) 
    { 
        try 
        {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
            Document doc; 
            
            if(file) 
                doc = db.parse(new File(xml)); 
            else 
                doc = db.parse(new InputSource(new StringReader(xml))); 
            
            Schema s = sf.newSchema(new StreamSource(new File(((fileSchema.compareTo("log.xsd") == 0) ? "..\\" : "") + fileSchema)));
            s.newValidator().validate(new DOMSource(doc)); 
        } 
        catch(ParserConfigurationException | SAXException | IOException e) 
        {
            if(e instanceof SAXException) 
                System.err.println("Errore di validazione"); 
            
            System.err.println(e.getMessage());
            return false; 
        }
        
        return true; 
    }
}
