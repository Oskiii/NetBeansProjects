/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko10;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
 * @author Oski
 */
public class MovieCompany {
    private ArrayList theatres;
    
    private static MovieCompany instance;
    public static MovieCompany GetInstance(){
        if(instance == null){
            instance = new MovieCompany();
        }
        return instance;
    }
    
    public ArrayList GetTheaters(String url) throws IOException, SAXException, ParserConfigurationException{
        
        theatres = new ArrayList();
        
        String content = URLReader.ReadURL(url);
        theatres = ParseTheatreData(content);
        
        return theatres;
    }

    private ArrayList ParseTheatreData(String content) throws ParserConfigurationException, SAXException, IOException{
        NodeList theatreList;
        ArrayList tList = new ArrayList();
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        Document document;

        documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.parse(new InputSource(new ByteArrayInputStream(content.getBytes("utf-8"))));
        document.getDocumentElement().normalize();

        System.out.println("Root element :" + document.getDocumentElement().getNodeName());
        
        theatreList = document.getElementsByTagName("TheatreArea");
        
        String name = "error";
        int ID = 0;
        for(int i = 1; i < theatreList.getLength(); i++){
            Node theatreNode = theatreList.item(i);
            
            System.out.println("\nCurrent Element :" + theatreNode.getNodeName());
            
            Element element = (Element) theatreNode;
            
            //System.out.println(theatreNode.getAttributes().item(0).getTextContent());
            System.out.println("Name: " + element.getElementsByTagName("Name").item(0).getTextContent());
            System.out.println("ID: " + element.getElementsByTagName("ID").item(0).getTextContent());

            name = element.getElementsByTagName("Name").item(0).getTextContent();
            ID = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
            tList.add(new Theatre(name, ID));
        }
        
        return tList;
    }
}
