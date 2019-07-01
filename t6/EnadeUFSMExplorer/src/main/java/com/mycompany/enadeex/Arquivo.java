/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.enadeex;

import com.opencsv.CSVReader;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Carlinhos
 */
public class Arquivo {
    public String fileName;
    public String url;
    public Arquivo(String fileName, String url){
        this.fileName = fileName;
        this.url = url;
    }
    public boolean baixaArquivo(){
        try (BufferedInputStream inputStream = new BufferedInputStream(new URL(url).openStream());  
            FileOutputStream fileOs = new FileOutputStream(fileName)) {
              byte data[] = new byte[1024];
              int byteContent;
              while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                  fileOs.write(data, 0, byteContent);
              }
          } catch (IOException e) {
              return false;
          }
        return true;
    }
    public List<List<String>> leArquivo(){
        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(
             new InputStreamReader(new FileInputStream(fileName), "UTF-8")); ) {
             String[] values = null;
             while ((values = csvReader.readNext()) != null) {
                 records.add(Arrays.asList(values));
             }
        } 
        catch (Exception e) { 
            //e.printStackTrace();
            
        }
        
    return records;
    }
}
