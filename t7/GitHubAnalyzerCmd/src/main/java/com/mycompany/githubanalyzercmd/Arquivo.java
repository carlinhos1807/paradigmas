package com.mycompany.githubanalyzercmd;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Arquivo {
    String path;
    public Arquivo(String path){
        this.path = path;
    }
    public ArrayList<String> leitor() throws IOException{
        ArrayList<String> links = new ArrayList<>();
        try (BufferedReader buffRead = new BufferedReader(new FileReader(path))) {
            String linha = "";
            while (true) {
                if (linha != null) {
                   if(!"".equals(linha)){
                    links.add(linha);
                   }
                } else
                    break;
                linha = buffRead.readLine();
            }
        }
        return links;
        
    }
}

