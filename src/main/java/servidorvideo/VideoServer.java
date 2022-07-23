/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorvideo;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 *
 *@author Julio Hernandez, Pedro sabas
 */
public class VideoServer {

    private static Vector<Socket> vectorSocket = new Vector<Socket>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            ServerSocket welcomSocket = new ServerSocket(1090);
            while (true) {
                Socket socket = welcomSocket.accept();
                vectorSocket.add(socket);
                HiloVideo hChat = new HiloVideo(socket, vectorSocket);
                Thread hilo = new Thread(hChat);
                hilo.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
