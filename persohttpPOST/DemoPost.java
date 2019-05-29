import java.net.*;
import java.io.IOException;

public class DemoPost {

  public static void main(String[] args) throws IOException {

    String urlstr = "https://script.google.com/a/inf.ufsm.br/macros/s/AKfycbwKrcRHm08L9bIxBIATDI65zWj7tb244VOq4kcPog/exec?";
    URL url = new URL(urlstr);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("POST");
    con.setDoOutput(true);

    String data = "luckynumber=(200!)^200!&nccid=casantos&msg=uma mensagem aqui";
    con.getOutputStream().write(data.getBytes("UTF-8"));
    System.out.println(con.getClass());

    System.out.println("Response code: " + con.getResponseCode());
  }

}