/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randompickergui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 *
 * @author Carlinhos
 */
public class EmbaralhamentoOnline extends Embaralhamento {
    public EmbaralhamentoOnline(ArrayList<String> nomes){
        super(nomes);
    }
    public boolean embaralha(){
        
     try {
      
        String urlstr = "https://www.random.org/lists/?mode=advanced";

        URL url = new URL(urlstr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setDoOutput(true);
        String data = "list=";
        data+=nomes.get(0);
        int i = 0;
        for(i = 1; i < nomes.size();i++){
            data+= "%0D%0A";
            data+=nomes.get(i);
        }
        data+= "&format=plain&rnd=new";
        nomes.clear();
        // Envia dados pela conexão aberta
        con.getOutputStream().write(data.getBytes("UTF-8"));
        if(con.getResponseCode() != 200){
            return false;
        }

        // Cria objeto que fará leitura da resposta pela conexão aberta
        BufferedReader in = new BufferedReader(
          new InputStreamReader(con.getInputStream()));

        // Lê resposta, linha por linha
        String responseLine;
        while ((responseLine = in.readLine()) != null) {
            nomes.add(responseLine);
        }
        // Mostra resposta

        in.close();
    } catch (IOException e) {
    	e.printStackTrace();
    }
        return true;
    }
}
