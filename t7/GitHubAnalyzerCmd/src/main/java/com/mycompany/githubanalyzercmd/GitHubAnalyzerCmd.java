package com.mycompany.githubanalyzercmd;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GitHubAnalyzerCmd {
    public static void main(String[] args) throws IOException{
        String filename = "teste.txt";
        ArrayList<Repositorios> repos = new ArrayList<>();
        if (args.length >= 1) {
            filename = args[0];
        }   
        Arquivo a = new Arquivo(filename);
        ArrayList<String> links = a.leitor();
        for(int i = 0; i < links.size();i++){
            Repositorios r = new Repositorios(links.get(i));
            repos.add(r);
        }
        for(int i = 0; i < links.size();i++){
            boolean teste = repos.get(i).commits();
            if(!teste){
               System.out.println("erro");
               return;
            }
         }
        String DataMaisRecente = "";
        String DataMenosRecente = "";
                    
        int maiorNumCommits = 0;
        int menorNumCommits = 0;
                    
        int indiceMaiorNumCommits = 0;
        int indiceMenorNumCommits = 0;
                    
        int indiceDataMaisRecente = 0;
        int indiceDataMenosRecente = 0;
        for(int i = 0; i < links.size();i++){
            if(i == 0){
                maiorNumCommits = Integer.parseInt(repos.get(0).numeroCommits);
                menorNumCommits = Integer.parseInt(repos.get(0).numeroCommits);
                DataMaisRecente = Repositorios.converte(repos.get(0).dataRecente);
                DataMenosRecente = Repositorios.converte(repos.get(0).dataRecente);
            }else{

                try {
                    if(Repositorios.dataCompare1(repos.get(i).dataRecente,DataMaisRecente)){
                        DataMaisRecente = Repositorios.converte(repos.get(i).dataRecente);
                        indiceDataMaisRecente = i;
                                }
                } catch (ParseException ex) {
                    Logger.getLogger(GitHubAnalyzerCmd.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if(!Repositorios.dataCompare1(repos.get(i).dataRecente,DataMenosRecente)){
                        DataMenosRecente = Repositorios.converte(repos.get(i).dataRecente);
                        indiceDataMenosRecente = i;
                                }
                } catch (ParseException ex) {
                    Logger.getLogger(GitHubAnalyzerCmd.class.getName()).log(Level.SEVERE, null, ex);
                }

                if(Integer.parseInt(repos.get(i).numeroCommits) > maiorNumCommits){
                    maiorNumCommits = Integer.parseInt(repos.get(i).numeroCommits);
                    indiceMaiorNumCommits = i;
                }
                if(Integer.parseInt(repos.get(i).numeroCommits) < menorNumCommits){
                    menorNumCommits = Integer.parseInt(repos.get(i).numeroCommits);
                    indiceMenorNumCommits = i;
                }
            }
                       
         }

        System.out.println("Repositorio com Commit mais recente(" +repos.get(indiceDataMaisRecente).dataRecente+ ") = " +links.get(indiceDataMaisRecente));
        System.out.println("Repositorio com Commit menos recente(" +repos.get(indiceDataMenosRecente).dataRecente+ ") = " +links.get(indiceDataMenosRecente));
        System.out.println("Repositorio com maior numero de commits = " +links.get(indiceMaiorNumCommits));
        System.out.println("Repositorio com menor numero de commits = " +links.get(indiceMenorNumCommits));
        
    }
}
