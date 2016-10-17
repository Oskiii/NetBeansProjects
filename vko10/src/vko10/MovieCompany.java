/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko10;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private ArrayList<Theatre> theatres;
    private String theatreURL = "http://www.finnkino.fi/xml/TheatreAreas/";
    
    private static MovieCompany instance;
    public static MovieCompany GetInstance(){
        if(instance == null){
            instance = new MovieCompany();
        }
        return instance;
    }
    
    public ArrayList GetTheaters(){
        theatres = new ArrayList<>();
        
        String content;
        try {
            content = URLReader.ReadURL(theatreURL);
            theatres = ParseTheatreData(content);
        } catch (ParserConfigurationException | SAXException | IOException ex ) {
            Logger.getLogger(MovieCompany.class.getName()).log(Level.SEVERE, null, ex);
        }
        return theatres;
    }
    
    public Theatre GetTheatreByName(String name){
        for(int i = 0; i < theatres.size (); i++){
            if(theatres.get(i).GetName().equals(name)){
                return theatres.get(i);
            }
        }
        return null;
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
        
        String name = "";
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

    public ArrayList<Showing> GetShowsAtTheatreForDay(int theatreID/*, Date date*/){
        NodeList showList;
        ArrayList sList = new ArrayList();
        
        System.out.println(theatreID);
        
        String content = null;
        try {
            content = URLReader.ReadURL("http://www.finnkino.fi/xml/Schedule/?area=" + theatreID + "&dt=" + "17.10.2016");
        } catch (IOException ex) {
            Logger.getLogger(MovieCompany.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        Document document = null;

        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MovieCompany.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            document = documentBuilder.parse(new InputSource(new ByteArrayInputStream(content.getBytes("utf-8"))));
        } catch (SAXException | IOException ex) {
            Logger.getLogger(MovieCompany.class.getName()).log(Level.SEVERE, null, ex);
        }


        document.getDocumentElement().normalize();

        System.out.println("Root element: " + document.getDocumentElement().getNodeName());
        
        Node showParent = document.getDocumentElement().getElementsByTagName("Shows").item(0);
        System.out.println(showParent.getNodeName());

        Element showParentElement = (Element) showParent;
        System.out.println("lapsia " + showParentElement.getElementsByTagName("Show").getLength());
        showList = showParentElement.getElementsByTagName("Show");
        
        String name = "";
        int ID = 0;
        System.out.println("entering for loop!");
        System.out.println(showList.getLength());
        for(int i = 1; i < showList.getLength(); i++){
            System.out.println("loopdy loop!");
            Node theatreNode = showList.item(i);
            
            System.out.println("\nCurrent Element :" + theatreNode.getNodeName());
            
            Element element = (Element) theatreNode;

            System.out.println("Title: " + element.getElementsByTagName("Title").item(0).getTextContent());
            
            name = element.getElementsByTagName("Title").item(0).getTextContent();
            //ID = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
            sList.add(new Showing(name));
        }
        
        return sList;
    }
}
