/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randompickergui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Carlinhos
 */
public class Arquivo {
    String path;
    public Arquivo(String path){
        this.path = path;
    }
    public ArrayList<String> leitor() throws IOException{
        ArrayList<String> nomes = new ArrayList<>();
        try (BufferedReader buffRead = new BufferedReader(new FileReader(path))) {
            String linha = "";
            while (true) {
                if (linha != null) {
                   if(!"".equals(linha)){
                    nomes.add(linha);
                   }
                    
                    
                } else
                    break;
                linha = buffRead.readLine();
            }
        }
        return nomes;
        
    }
}
