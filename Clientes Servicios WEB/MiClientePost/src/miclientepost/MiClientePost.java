/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miclientepost;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author faranzabe
 */
public class MiClientePost {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //En este ejemplo alternamos la llamada entre post2, post3 y post4.
            //URL url = new URL("http://172.17.212.128:8080/EjemploGF/app/pruebaPost/post2");
            //URL url = new URL("http://172.17.212.128:8080/EjemploGF/app/pruebaPost/post3");
            URL url = new URL("http://172.17.212.128:8080/EjemploGF/app/pruebaPost/post4");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            String input = "{\"num\":100,\"texto\":\"Elemento cien\"}";//En formato JSON con caracteres de escape. Si lo copiamos y pegamos en NB se ponen automáticamente.
            System.out.println("1 Datos que pasamos al servicio en POST: " + input);
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());

            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println("2 Recibido del servidor: " + output);
                //A continuación comentar o descomentar cada bloque si el servicio WEB devolviera algo en formato JSON.

//                Un objeto //--> Para este ejemplo consumimos el post3 de pruebaPost en el proyecto EjemploGF.
//                Gson gson = new Gson();
//                Datos dato = gson.fromJson(output, Datos.class); //Si consumimos post2 falla porque ese servicio devuelve una cadena que no está en formato JSON.
//                System.out.println(dato);                        //Si consumimos post4 falla porque ese servicio devuelve una colección JSON.
                
                
                //Lista de objetos //--> Para el ejemplo de consumir desde post4.
                Gson gson = new Gson();
                List<Datos> listDatos = gson.fromJson(output, new TypeToken<List<Datos>>() {
                }.getType());
                if (listDatos != null) {
                    for (Datos object : listDatos) {
                        System.out.println("Dato : " + object.getNum() + " " + object.getTexto());
                    }
                }
            }
            conn.disconnect();

        } catch (MalformedURLException ex) {
            Logger.getLogger(MiClientePost.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MiClientePost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
