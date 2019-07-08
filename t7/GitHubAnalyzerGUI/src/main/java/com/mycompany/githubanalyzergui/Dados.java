package com.mycompany.githubanalyzergui;

public class Dados {
        public String url;
        public String numeroCommits;
        public String tamMedioMensagens;

    public Dados(String url, String numeroCommits, String tamMedioMensagens) {
        this.url = url;
        this.numeroCommits = numeroCommits;
        this.tamMedioMensagens = tamMedioMensagens;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNumeroCommits() {
        return numeroCommits;
    }

    public void setNumeroCommits(String numeroCommits) {
        this.numeroCommits = numeroCommits;
    }

    public String getTamMedioMensagens() {
        return tamMedioMensagens;
    }

    public void setTamMedioMensagens(String tamMedioMensagens) {
        this.tamMedioMensagens = tamMedioMensagens;
    }

    
    
}
