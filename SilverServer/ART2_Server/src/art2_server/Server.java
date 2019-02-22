/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package art2_server;

/**
 *
 * @author sander
 */
// Java implementation of  Server side 
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 
  
import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 
  
// Server class 
public class Server  { 
    public static void main(String[] args) throws IOException  { 
        // server is listening on port 8000
        ServerSocket ss = new ServerSocket(8000); 
          
        // running infinite loop for getting 
        // client request 
        while (true)  { 
            Socket s = null; 
              
            try { 
                // socket object to receive incoming client requests 
                s = ss.accept(); 
                  
                System.out.println("Client connesso : " + s); 
                  
                // obtaining input and out streams 
                DataInputStream dis = new DataInputStream(s.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
                  
                System.out.println("Assegnazione del thread al Client"); 
  
                // create a new thread object 
                Thread t = new ClientHandler(s, dis, dos); 
  
                // Invoking the start() method 
                t.start(); 
                  
            } 
            catch (Exception e){ 
                s.close(); 
                e.printStackTrace(); 
            } 
        } 
    } 
} 
  
// ClientHandler class 
class ClientHandler extends Thread  { 
    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd"); 
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss"); 
    final DataInputStream dis; 
    final DataOutputStream dos; 
    final Socket s; 
      
  
    // Constructor 
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)  { 
        this.s = s; 
        this.dis = dis; 
        this.dos = dos; 
    } 
  
    @Override
    public void run()  { 
        String received; 
        String data ;
        String time ;
        while (true)  { 
            try { 
  
                // Ask user what he wants 
                dos.writeUTF("Inserisci il messaggio\n"+ 
                            "Per terminare inserisci 'Exit'\n"); 
                  
                // receive the answer from client 
                received = dis.readUTF(); 
                  
                if(received.equals("Exit")) {  
                    System.out.println("Client " + this.s + " ha terminato la sessione..."); 
                    System.out.println("Chiusura del buffer."); 
                    this.s.close(); 
                    System.out.println("Connessione terminata"); 
                    break; 
                } 
                  
                // creating Date object 
                Date date = new Date();
                Date tim = new Date();
                dos.writeUTF(received);
                data = fordate.format(date); 
                dos.writeUTF(data);
                //time = fortime.format(tim); 
                //dos.writeUTF(time);
                  
                // write on output stream based on the 
                // answer from the client 
                /*switch (received) { 
                  
                    case "Date" : 
                        toreturn = "Ciao"; 
                        dos.writeUTF(toreturn); 
                        break; 
                          
                    case "Time" : 
                        toreturn = fortime.format(date); 
                        dos.writeUTF(toreturn); 
                        break; 
                          
                    default: 
                        dos.writeUTF("Invalid input"); 
                        break; 
                } */
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
        
        try { 
            // chiusura delle risorse
            this.dis.close(); 
            this.dos.close(); 
              
        } catch(IOException e) { 
            e.printStackTrace(); 
        } 
    } 
}