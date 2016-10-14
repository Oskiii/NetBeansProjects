package vko10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Oski
 */
public class URLReader {
    public static String ReadURL(String sourceURL) throws MalformedURLException, IOException{
        URL url = new URL(sourceURL);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        String content = "";
        String line;

        while((line = br.readLine()) != null){
            content += line + "\n";
        }
        
        return content;
    }
}
