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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Calendar;
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
    
    private ArrayList<String> searchExcludes = new ArrayList<>(asList("Pääkaupunkiseutu", "Espoo", "Helsinki", "Tampere"));
    
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
    
    public Theatre GetTheatreByID(int ID){
        for(int i = 0; i < theatres.size (); i++){
            if(theatres.get(i).GetID() == ID){
                return theatres.get(i);
            }
        }
        return null;
    }
    
    public Theatre GetTheatreByListPosition(int pos){
        return theatres.get(pos);
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

        //System.out.println("Root element :" + document.getDocumentElement().getNodeName());
        
        theatreList = document.getElementsByTagName("TheatreArea");
        
        String name = "";
        int ID = 0;
        for(int i = 1; i < theatreList.getLength(); i++){
            Node theatreNode = theatreList.item(i);
            
            //System.out.println("\nCurrent Element :" + theatreNode.getNodeName());
            
            Element element = (Element) theatreNode;

            //System.out.println(theatreNode.getAttributes().item(0).getTextContent());
            //System.out.println("Name: " + element.getElementsByTagName("Name").item(0).getTextContent());
            //System.out.println("ID: " + element.getElementsByTagName("ID").item(0).getTextContent());

            name = element.getElementsByTagName("Name").item(0).getTextContent();
            ID = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
            tList.add(new Theatre(name, ID));
        }
        
        return tList;
    }

    public ArrayList<Showing> GetShowsAtTheatreForDayThatRunBetweenTimes(Theatre theatre, Date date, Date startTime, Date endTime){
        NodeList showList;
        ArrayList sList = new ArrayList();
        
        int theatreID = theatre.GetID();
        
        System.out.println(theatreID);
        
        SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
        String sdate = ft.format(date);
        
        String content = null;
        try {
            content = URLReader.ReadURL("http://www.finnkino.fi/xml/Schedule/?area=" + theatreID + "&dt=" + sdate);
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

        //System.out.println("Root element: " + document.getDocumentElement().getNodeName());
        
        Node showParent = document.getDocumentElement().getElementsByTagName("Shows").item(0);
        //System.out.println(showParent.getNodeName());

        Element showParentElement = (Element) showParent;
        //System.out.println("lapsia " + showParentElement.getElementsByTagName("Show").getLength());
        showList = showParentElement.getElementsByTagName("Show");
        
        
        SimpleDateFormat XMLDate = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date showStartDate = null;
        Date showEndDate = null;
        
        String name = "";
        //System.out.println("entering for loop!");
        //System.out.println(showList.getLength());
        for(int i = 1; i < showList.getLength(); i++){
            //System.out.println("loopdy loop!");
            Node theatreNode = showList.item(i);
            
            //System.out.println("\nCurrent Element :" + theatreNode.getNodeName());
            
            Element element = (Element) theatreNode;

            //System.out.println("Title: " + element.getElementsByTagName("Title").item(0).getTextContent());
            
            name = element.getElementsByTagName("Title").item(0).getTextContent();
            try {
                showStartDate = XMLDate.parse(element.getElementsByTagName("dttmShowStart").item(0).getTextContent());
                showEndDate = XMLDate.parse(element.getElementsByTagName("dttmShowEnd").item(0).getTextContent());
            } catch (ParseException ex) {
                Logger.getLogger(MovieCompany.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(showStartDate.after(startTime) && showEndDate.before(endTime)){
                sList.add(new Showing(name, theatre, showStartDate, showEndDate));
            }
        }
        
        return sList;
    }
    
    public ArrayList<Showing> GetShowsAtTheatreForDayThatStartBetweenTimes(Theatre theatre, Date date, Date startTime, Date endTime){
        NodeList showList;
        ArrayList sList = new ArrayList();
        
        int theatreID = theatre.GetID();
        
        System.out.println(theatreID);
        
        SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
        String sdate = ft.format(date);
        
        String content = null;
        try {
            content = URLReader.ReadURL("http://www.finnkino.fi/xml/Schedule/?area=" + theatreID + "&dt=" + sdate);
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

        //System.out.println("Root element: " + document.getDocumentElement().getNodeName());
        
        Node showParent = document.getDocumentElement().getElementsByTagName("Shows").item(0);
        //System.out.println(showParent.getNodeName());

        Element showParentElement = (Element) showParent;
        //System.out.println("lapsia " + showParentElement.getElementsByTagName("Show").getLength());
        showList = showParentElement.getElementsByTagName("Show");
        
        
        SimpleDateFormat XMLDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date showStartDate = null;
        Date showEndDate = null;
        
        String name = "";
        //System.out.println("entering for loop!");
        //System.out.println(showList.getLength());
        for(int i = 1; i < showList.getLength(); i++){
            //System.out.println("loopdy loop!");
            Node theatreNode = showList.item(i);
            
            //System.out.println("\nCurrent Element :" + theatreNode.getNodeName());
            
            Element element = (Element) theatreNode;

            //System.out.println("Title: " + element.getElementsByTagName("Title").item(0).getTextContent());
            
            name = element.getElementsByTagName("Title").item(0).getTextContent();
            try {
                showStartDate = XMLDate.parse(element.getElementsByTagName("dttmShowStart").item(0).getTextContent());
            } catch (ParseException ex) {
                Logger.getLogger(MovieCompany.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(showStartDate.after(startTime) && showStartDate.before(endTime)){
                sList.add(new Showing(name, theatre, showStartDate, showEndDate));
            }
        }
        
        return sList;
    }
    
    public ArrayList<Showing> GetShowingsInfoByMovieName(String movieName){
        int dateCounter;
        int showCounter;
        int daysIntoFuture = 0;
        Calendar c = Calendar.getInstance();
        Showing show;
        
        ArrayList<Showing> showingList = new ArrayList<>();
        
        //loop through next x days
        for(dateCounter = 0; dateCounter < daysIntoFuture + 1; dateCounter++){

            c.setTime(new Date());
            c.add(Calendar.DATE, dateCounter);  // number of days to add
            
            //showingList.add(new Showing(null, null, new Date(), null));
            //System.out.println("looking at date: " + c.getTime());
            //loop through all theatres
            for(Theatre t : theatres){
                
                if(searchExcludes.contains(t.GetName())){
                    continue;
                }
                ArrayList<Showing> showsAtTheatre = GetShowsAtTheatreForDay(t, c.getTime());
                
                //System.out.println("looking at theatre: " + t.GetName());
                //loop through all shows at theatre
                for(showCounter = 0; showCounter < showsAtTheatre.size(); showCounter++){
                    //System.out.println("looking at show: " + showsAtTheatre.get(showCounter).GetTitle());

                    show = showsAtTheatre.get(showCounter);
                    if(show.GetTitle().equals(movieName)){
                        showingList.add(show);
                    }
                }
                for(showCounter = showsAtTheatre.size() - 1; showCounter >= 0; showCounter--){
                    //System.out.println("looking at show: " + showsAtTheatre.get(showCounter).GetTitle());

                    show = showsAtTheatre.get(showCounter);
                    if(show.GetTitle().equals(movieName)){
                        showingList.add(show);
                    }
                } 
            }
        }
        return showingList;
    }
    
    public ArrayList<Showing> GetShowsAtTheatreForDay(Theatre theatre, Date date){
        NodeList showList;
        ArrayList sList = new ArrayList();
        
        int theatreID = theatre.GetID();
        
        SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
        String sdate = ft.format(date);
        
        String content = null;
        try {
            content = URLReader.ReadURL("http://www.finnkino.fi/xml/Schedule/?area=" + theatreID + "&dt=" + sdate);
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

        //System.out.println("Root element: " + document.getDocumentElement().getNodeName());
        
        Node showParent = document.getDocumentElement().getElementsByTagName("Shows").item(0);
        //System.out.println(showParent.getNodeName());

        Element showParentElement = (Element) showParent;
        //System.out.println("lapsia " + showParentElement.getElementsByTagName("Show").getLength());
        showList = showParentElement.getElementsByTagName("Show");
        
        
        SimpleDateFormat XMLDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date showStartDate = null;
        Date showEndDate = null;
        
        String name = "";
        int ID = 0;
        //System.out.println("entering for loop!");
        //System.out.println(showList.getLength());
        for(int i = 1; i < showList.getLength(); i++){
            //System.out.println("loopdy loop!");
            Node theatreNode = showList.item(i);
            Element element = (Element) theatreNode;
            
            name = element.getElementsByTagName("Title").item(0).getTextContent();
            try {
                showStartDate = XMLDate.parse(element.getElementsByTagName("dttmShowStart").item(0).getTextContent());
                showEndDate = XMLDate.parse(element.getElementsByTagName("dttmShowEnd").item(0).getTextContent());
            } catch (ParseException ex) {
                Logger.getLogger(MovieCompany.class.getName()).log(Level.SEVERE, null, ex);
            }

            sList.add(new Showing(name, theatre, showStartDate, showEndDate));
        }
        
        return sList;
    }
}
