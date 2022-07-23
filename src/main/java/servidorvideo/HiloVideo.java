/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorvideo;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 *@author Julio Hernandez, Pedro sabas
 */
public class HiloVideo implements Runnable {

    private Socket socket;
    private Vector<Socket> sockets;
    private DataOutputStream netOut;
    private boolean validacion = false;

    HiloVideo(Socket socket, Vector<Socket> vectorSocket) {
        this.socket = socket;
        this.sockets = vectorSocket;
    }

    @Override
    public void run() {
        try {
            DataInputStream netIn = new DataInputStream(socket.getInputStream());

            while (true) {
                String Peticion = netIn.readUTF();
                System.out.println(Peticion);
                StringTokenizer st = new StringTokenizer(Peticion, "/");
                System.out.println(st.countTokens());
                String peticion = null;
                if (st.countTokens() == 3) {
                    netOut = new DataOutputStream(socket.getOutputStream());

                    peticion = netIn.readUTF();
                    if (peticion != null) {

                        validacion = true;
                        netOut.writeBoolean(validacion);
                        netOut.writeUTF(peticion);
                        EnviaVideo(peticion);
                    }

                }

            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Error!");

        }

    }

    private void EnviaVideo(String pp) {
        String peticion = pp;
        try {
            netOut = new DataOutputStream(socket.getOutputStream());
            File file = new File(peticion);
            if (file.exists()) {
                //int longitud = (int) file.length();
                byte[] buffer = new byte[1024];
                FileInputStream fr = new FileInputStream(peticion);
                int longitud = fr.read(buffer, 0, 1024);
                while (longitud > 0) {
                    netOut.write(buffer, 0, longitud);
                    longitud = fr.read(buffer, 0, 1024);
                }
                fr.close();
                socket.close();
            }

        } catch (IOException io) {
            io.printStackTrace();
        }

    }
    /*private void Video() throws FileNotFoundException, IOException {
            int bytes_archivo[];
	        try {
                    String Array[] = new String[3];
                    Array[0]="C:\\Users\\Julio\\Pictures\\a.mp4";
                    Array[1]="C:\\Users\\Julio\\Pictures\\a.mp4";
                    Array[2]="C:\\Users\\Julio\\Pictures\\a.mp4";
                    //Abre el stream de datos.
	            FileInputStream archivo_lectura = new FileInputStream(Array[0]);
	            
	            //Almacena el número de bytes.
	            BufferedInputStream memoriaAux = 
	                    new BufferedInputStream(archivo_lectura);
	            //saber el tamaño del archivo
	            int tamañoBuffer = memoriaAux.available();
	            
	            //Establece el tamaño del arreglo.
	             bytes_archivo = new int[tamañoBuffer];
	            
	           
	            
	            //Almacena en el arreglo los bytes.
	            for (int i = 0; i < bytes_archivo.length; i++) {
	                bytes_archivo[i] = memoriaAux.read();
	                
	                //Imprimiendo para verificar.
	                System.out.println(bytes_archivo[i]+"  "+i);
	                
	            }
	            
	            //Se cierra el Stream
	            archivo_lectura.close();
	            
	            System.out.printf("El número de bytes en el archivo es: %d\n", 
	                    tamañoBuffer);
                    DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
                    salida.writeInt(tamañoBuffer);
	           CreaVideo(bytes_archivo);
	        } catch (FileNotFoundException ex) {
	            
	            System.out.println("No se encuentra el archivo.");
	        } catch (IOException ex) {
	            System.out.println("Problema de memoria");
	        }
	        
    }
     void CreaVideo (int datos[]){
        try{
            DataOutputStream VideoNuevo = new DataOutputStream(socket.getOutputStream());
            for(int i=0;i<datos.length;i++){
                VideoNuevo.writeInt(datos[i]);
            }
             
            VideoNuevo.close();
        }catch(IOException e){}
            System.out.println("Error al crear Video");
        }*/
}
