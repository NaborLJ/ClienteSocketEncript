package clientesocketencript;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JOptionPane;


public class ClienteSocketEncript {

    public static void main(String[] args) throws IOException {
        System.setProperty("javax.net.ssl.trustStore", "keystore.jks");
        
        String texto = null;
        Scanner entrada = new Scanner(System.in);
        String mensaje = null;
        try {
            
            System.out.println("Obteniendo factoria de sockets cliente");
            SSLSocketFactory SocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            
            System.out.println("Creando socket cliente");
            SSLSocket clienteSocket=(SSLSocket) SocketFactory.createSocket();
            
            System.out.println("Estableciendo la conexión");
            InetSocketAddress addr = new InetSocketAddress("localhost",5555);
            clienteSocket.connect(addr);

            DataInputStream is = new DataInputStream(clienteSocket.getInputStream());
            DataOutputStream os = new DataOutputStream(clienteSocket.getOutputStream());

            System.out.println("Introducir mensaje inicial /Cliente/");
            texto = entrada.next();
            os.writeUTF(texto);
            os.flush();
            mensaje = is.readUTF();
            System.out.println("Mensaje recibido: " + (mensaje));

            while (!"Apagar".equals(mensaje)) {
                System.out.println("Introducir mensaje /Cliente/");
                texto = entrada.next();
                os.writeUTF(texto);
                os.flush();
                System.out.println("Mensaje enviado");

                mensaje = is.readUTF();
                System.out.println("Mensaje recibido: " + (mensaje));
            }

            System.out.println("Cerrando el socket cliente");

            clienteSocket.close();

            System.out.println("Terminado");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}