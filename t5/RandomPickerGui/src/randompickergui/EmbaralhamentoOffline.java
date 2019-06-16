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
public class EmbaralhamentoOffline extends Embaralhamento {
    public EmbaralhamentoOffline(ArrayList<String> nomes){
        super(nomes);
    }
    public void embaralha(){
        Collections.shuffle(nomes);
    }
    
}
