/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oanvand;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class T2 implements Runnable {

    @Override
    public void run() {
        try {
            start();
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void start() throws IOException {
        System.out.println("1433");
        //
        ServerSocket serverSocket = new ServerSocket(1433);
        serverSocket.setReuseAddress(true);
        serverSocket.accept();
        //
        System.out.println("Accepted");
    }

    public void start_2() throws IOException {
        System.out.println("1433");
        //
        ServerSocket serverSocket = new ServerSocket(1433);
        serverSocket.setReuseAddress(true);
        serverSocket.accept();
        //
        System.out.println("Accepted");
    }

    public void start_3() throws IOException {
        System.out.println("1433");
        //
        ServerSocket serverSocket = new ServerSocket(1433);
        serverSocket.setReuseAddress(true);
        serverSocket.accept();
        //
        System.out.println("Accepted");
    }

    public void start_4() throws IOException {
        System.out.println("1433");
        //
        ServerSocket serverSocket = new ServerSocket(1433);
        serverSocket.setReuseAddress(true);
        serverSocket.accept();
        //
        System.out.println("Accepted");
    }

    public void start_5() throws IOException {
        System.out.println("1433");
        //
        ServerSocket serverSocket = new ServerSocket(1433);
        serverSocket.setReuseAddress(true);
        serverSocket.accept();
        //
        System.out.println("Accepted");
    }

    public void start_6() throws IOException {
        System.out.println("1433");
        //
        ServerSocket serverSocket = new ServerSocket(1433);
        serverSocket.setReuseAddress(true);
        serverSocket.accept();
        //
        System.out.println("Accepted");
    }
}
