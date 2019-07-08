package com.mycompany.githubanalyzergui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlinhos
 */
public class Repositorios {
    public String url2;
    public String numeroCommits;
    public String tamMedioMensagens;
    public int acumuladorTamMensagem = 0;
    public String dataRecente;
    
    public Repositorios(String url2){
        this.url2 = url2;
    }
    public static String converte(String Data1){
        String substring = Data1.substring(1,11);
        return substring;
    }
    public static boolean dataCompare1(String Data1, String Data2) throws ParseException{
        String substring1 = converte(Data1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(substring1);
        Date date2 = sdf.parse(Data2);
        
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        if (cal1.after(cal2)) {
            return true;
        }else if (cal1.before(cal2)) {
            return false;
        }else{
            return false;
        }
    }
    public boolean commits(){
        String parametro = url2 + "/commits?page=";
        int contador = 1;
        String index = Integer.toString(contador);
        String request = parametro + index;
        int controle = 1;
        int numCommits = 0;
        while(controle != 0){
            //System.out.println(request);
            controle = getCommits(request, contador);
            if(controle == -1){
                return false;
            }
            numCommits += controle;
            contador++;
            request = parametro + Integer.toString(contador);
        }
        this.numeroCommits = Integer.toString(numCommits);
        int media = acumuladorTamMensagem / numCommits;
        //System.out.println(acumuladorTamMensagem);
        this.tamMedioMensagens = Integer.toString(media);
        request = parametro + Integer.toString(contador-2);
        getCommits(request, contador-2);
        return true;
    }
    public int getCommits(String url2, int contador){
        String urlstr = url2;
        URL url = null;
          try {
              url = new URL(urlstr);
          } catch (MalformedURLException ex) {
              Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
              return -1;
          }
        HttpURLConnection con = null;
          try {
              con = (HttpURLConnection) url.openConnection();
          } catch (IOException ex) {
              Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
              return -1;
          }
          try {
              con.setRequestMethod("GET");
          } catch (ProtocolException ex) {
              Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
              return -1;
          }
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
          try {
              System.out.println("Response code: " + con.getResponseCode());
          } catch (IOException ex) {
              Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
              return -1;
          }

        BufferedReader in = null;
          try {
              in = new BufferedReader(
                      new InputStreamReader(con.getInputStream()));
          } catch (IOException ex) {
              Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
              return -1;
          }

        // Response header (includes pagination links)
        //System.out.println(con.getHeaderFields().get("Link").get(0));

        // Parse a nested JSON response using Gson
        JsonParser parser = new JsonParser();
        JsonArray results = null;
          try {
              results = parser.parse(in.readLine()).getAsJsonArray();
          } catch (IOException ex) {
              Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
              return -1;
          }

        System.out.println("Size: "+ results.size());
        if(results.size() == 0){
            return 0;
        }
        if(contador == 1){
            this.dataRecente = results.get(0).getAsJsonObject().get("commit").getAsJsonObject().get("author").getAsJsonObject().get("date").toString();
        }
        for (JsonElement e : results) {
          String n = e.getAsJsonObject().get("commit").getAsJsonObject().get("message").toString();
          this.acumuladorTamMensagem += n.length();

        }
          try {
              in.close();
          } catch (IOException ex) {
              Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
          }
          return results.size();
    }
    
}


