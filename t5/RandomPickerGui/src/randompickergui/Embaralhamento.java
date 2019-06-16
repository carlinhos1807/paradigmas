/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randompickergui;

import java.util.*;

/**
 *
 * @author Carlinhos
 */
public abstract class Embaralhamento {
    protected ArrayList<String> nomes = new ArrayList<>();
    public Embaralhamento(ArrayList<String> nomes){
        this.nomes = nomes;
    }
    public void mostra(){
        Scanner scan = new Scanner(System.in);
        int i =0;
        int tam = nomes.size();
        for(i = 0; i < tam;i++){
            System.out.println(nomes.get(0));
            nomes.remove(0);
            
            System.out.print("Pressione ENTER para mostrar o proximo elemento\n");
            scan.nextLine();
       }
        System.out.println("Fim");
    }
    public static String mostraGui(ArrayList<String> lista){
        String n ="";
        for (String s : lista) {
            n+=s;
            n+="\n";
        }
          return n;
    }
}
