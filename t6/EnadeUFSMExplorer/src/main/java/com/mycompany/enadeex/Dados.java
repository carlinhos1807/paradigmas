/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.enadeex;

import java.util.List;
import javafx.collections.ObservableList;

public class Dados{
    public List<List<String>> records;
    public ObservableList<Enade> data;
    public String url;
    
    public Dados(String url){
        this.url = url;
    }
    
    
}
