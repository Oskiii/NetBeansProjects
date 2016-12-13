/*
 * Oskari Liukku
 * 0435843
 */
package harkkatyö;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Oskari Liukku
 */
public class DataBuilder {
    private static DataBuilder instance;
    public static DataBuilder GetInstance(){
        if(instance == null){
            instance = new DataBuilder();
        }
        return instance;
    }

    //read XML into document form
    public void readXML(String source){
        try {
            URL url = new URL(source);
            
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            
            String content = "";
            String line;
            
            while((line = br.readLine()) != null) {
                content += line + "\n";
            }
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            
            Document doc = dBuilder.parse(new InputSource(new StringReader(content)));
            doc.getDocumentElement().normalize();
            
            parseData(doc);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(DataBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException | SAXException ex) {
            Logger.getLogger(DataBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //parse data from document
    private void parseData(Document doc){
        //get all nodes by tag
        NodeList nodes = doc.getElementsByTagName("place");        
        System.out.println(nodes.getLength());
        
        //run through them and find their info, add that info to packagemachine list
        for(int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            Element e = (Element) node;
            
            Address add = new Address(
                                    Integer.parseInt(getValue("code",e)),
                                    getValue("city",e).toUpperCase(),
                                    getValue("address",e)
                            );
            Double lat = new Double(getValue("lat",e));
            Double lng = new Double(getValue("lng",e));
            String name = getValue("postoffice",e);
            GeoPoint gp = new GeoPoint(add, lat, lng);
            String availability = getValue("availability",e);
            PackageMachine p = new PackageMachine(name, new GeoPoint(add,lat,lng), availability);
            PackageMachineManager.GetInstance().addMachine(p);
            
        }
    }
    
    private String getValue(String tag, Element e) {
        String s = ((Element)e.getElementsByTagName(tag).item(0)).getTextContent();
        return s;
    }
    
}
