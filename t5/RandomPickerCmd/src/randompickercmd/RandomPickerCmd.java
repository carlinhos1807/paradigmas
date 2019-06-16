/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randompickercmd;
import java.io.IOException;
import java.util.*;


/**
 *
 * @author Carlinhos
 */
public class RandomPickerCmd {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Arquivo a = new Arquivo(args[0]);
        ArrayList<String> nomes = a.leitor();
        if(nomes.size() > 100){
            EmbaralhamentoOnline e = new EmbaralhamentoOnline(nomes);
            boolean teste = e.embaralha();
            if(!teste){
              EmbaralhamentoOffline e2 = new EmbaralhamentoOffline(nomes);
              e2.embaralha();
              e2.mostra();
            }else{
                e.mostra();
            }
        }else{
            EmbaralhamentoOffline e2 = new EmbaralhamentoOffline(nomes);
            e2.embaralha();
            e2.mostra();
        }
    }
    
}
