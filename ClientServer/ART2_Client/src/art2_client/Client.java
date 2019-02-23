/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package art2_client;

/**
 *
 * @author sander
 */
// Java implementation for a client 
// Save file as Client.java 
  
import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
  
// Client class 
public class Client  
{ 
    public static void main(String[] args) throws IOException  
    { 
        try
        { 
            Scanner scn = new Scanner(System.in); 
              
            // Ip address
            InetAddress ip = InetAddress.getByName("sanderver.com"); 
      
            // Socket in ascolto sulla port 8000
            Socket s = new Socket(ip, 8000); 
      
            // Inizializzazione di I/O
            DataInputStream dis = new DataInputStream(s.getInputStream()); 
            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
      
            // Loop tra il Client e Server
            while (true)  { 
                System.out.println(dis.readUTF()); 
                String tosend = scn.nextLine(); 
                dos.writeUTF(tosend); 
                  
                // Exit per terminare la connessione tra il Client e il Server
                if(tosend.equals("Exit")||tosend.equals("exit")||tosend.equals("EXIT")) { 
                    System.out.println("Chiusura dell buffer : " + s); 
                    s.close(); 
                    System.out.println("Connessione chiusa"); 
                    break; 
                } 
                
                // Stampo il messaggio e la data 
                String received = dis.readUTF(); 
                System.out.println(received); 
            } 
              
            // Chiusura delle risorse
            scn.close(); 
            dis.close(); 
            dos.close(); 
        } catch(Exception e){ 
            e.printStackTrace(); 
        } 
    } 
}