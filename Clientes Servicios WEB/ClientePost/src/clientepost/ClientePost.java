/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientepost;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Este cliente es para el servicio WEB del proyecto EjemploGF (pruebaPost).
 * Ruta de acceso:
 * @author faranzabe
 */
public class ClientePost {

    /**
     * @param args the command line arguments
     * {"num":2,"texto":"dos"}
     */
    public static void main(String[] args) {
         try {//En este ejemplo alternamos la llamada entre post y post2
             //http://ip:8080/EjemploGF/app/pruebaPost/post
		URL url = new URL("http://172.17.212.128:8080/EjemploGF/app/pruebaPost/post2");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");

		String input = "{\"num\":200,\"texto\":\"Elemento doscientos\"}";//En formato JSON con caracteres de escape. Si lo copiamos y pegamos en NB se ponen automáticamente.
                System.out.println("1 Datos pasados al servicio en POST: " + input);
		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());


		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		while ((output = br.readLine()) != null) {
			System.out.println("2 Recibido del servidor: " + output);
		}

		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	 }

    }
    
}
