
package servidormulti;

import java.io.* ;
import java.net.* ;
import java.lang.Thread;

class clientHandler implements Runnable{
    
    private Socket socket;
    Thread t;
    
    public clientHandler(Socket socket){
        this.socket = socket;
        this.t = new Thread(this);
        t.start();
    }
    
    @Override
    public void run(){
        int num[];
        int res;
        
        try {
            InputStream istream = socket.getInputStream();
            ObjectInput in = new ObjectInputStream(istream);
            System.out.println("Objeto llego");
            System.out.println("Mandando mensaje al cliente");
            num = (int[]) in.readObject();
            res = num[0] + num[1];
            
          DataOutputStream ostream = new DataOutputStream(socket.getOutputStream());
          ostream.writeInt(res);
          ostream.flush();
          socket.close();
          
        } catch (Exception e){
            System.err.println("Error al operar con el cliente");
        }
        
    } //run
    
    
} //client handler


public class ServidorMulti {

    
    public static void main(String[] args) {
        
        ServerSocket serverAddr = null;
        Socket sc = null;
        
        try {
            serverAddr = new ServerSocket(2500);
            while (true) {
                
                sc = serverAddr.accept(); // esperando conexión
                new clientHandler(sc);
            }
                
            } catch (Exception e) {
                    System.err.println("excepcion" + e.toString());
                    e.printStackTrace();
            }
        } //main
        
    }// servidor
    
