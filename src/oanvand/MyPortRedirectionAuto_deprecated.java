/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oanvand;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import supplementary.OUT;
import supplementary.OUT;

/**
 *
 * @author KOCMOC
 */
public class MyPortRedirectionAuto_deprecated implements Runnable {

    //WORKING TESTED BY ME (Ihtiandr)
    private int SOURCE_PORT; //1433
    private String DESTINATION_HOST; //10.87.0.2
    private int DESTINATION_PORT;//1433
    private ServerSocketChannel serverSocket;
    private Selector selector;
    private boolean acceptConnections = true;
    private ArrayList<ClientThread> clientList = new ArrayList<ClientThread>();
    private OUT out;

    public MyPortRedirectionAuto_deprecated(int srcPort, String destHost, int destPort, OUT out) {
        this.SOURCE_PORT = srcPort;
        this.DESTINATION_HOST = destHost;
        this.DESTINATION_PORT = destPort;
        this.out = out;
    }

    public void setDestHost(String host) {
        this.DESTINATION_HOST = host;
    }

    public void setDestPort(int port) {
        this.DESTINATION_PORT = port;
    }

    public int getDestPort() {
        return DESTINATION_PORT;
    }

    public void killLastClient() {
        if (clientList.size() > 0) {
            ClientThread ct = clientList.get(clientList.size() - 1);
            ct.killClient();
        }
    }

    public void killAllClients() {
        for (ClientThread clientThread : clientList) {
            clientThread.killClient();
        }
    }

    public void showActiveRedirections() {
        String msg = "\nRedirections: \n";
        //
        msg += "*****************************************\n";
        for (ClientThread clientThread : clientList) {
            msg += clientThread.getRedirectionInfo() + "\n";
        }
        //
        msg += "*****************************************\n";
        //
        out.showMessage(msg);
    }

    public void closeServerSocket() {
        //
        acceptConnections = false;
        //
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(MyPortRedirectionAuto_deprecated.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            go();
        } catch (IOException ex) {
            Logger.getLogger(MyPortRedirectionAuto_deprecated.class.getName()).log(Level.SEVERE, null, ex);
            out.showMessage("Server socket closed on port: " + SOURCE_PORT);
            out.updateStatus("down");
        }
    }

    private void go() throws IOException {
        initializeServerSocket(); //====================!!!
        //
        out.showMessage("listening for connections on port: " + SOURCE_PORT);
        //
        out.updateStatus("listening");
        //
        while (acceptConnections) {
            Socket clientSocket = accept(); //==========================!!!
            clientSocket.setOOBInline(true);
            //
            //
            out.showMessage("client connected: " + "srcPort: " + SOURCE_PORT + "  " + "destHost: " + DESTINATION_HOST + "  " + "destPort: " + DESTINATION_PORT);
            //
            ClientThread clientThread =
                    new ClientThread(clientSocket, DESTINATION_HOST, DESTINATION_PORT, clientList, out);

            clientThread.start();
            //
            //
            clientList.add(clientThread);
            //
            out.updateClientCount(clientList.size());
        }
    }

    private void initializeServerSocket() throws ClosedChannelException, IOException {
        selector = Selector.open();
        //     
        serverSocket = ServerSocketChannel.open();

        //
        serverSocket.socket().bind(new InetSocketAddress(SOURCE_PORT));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT, null);
    }

    private Socket accept() throws IOException {
        //
        while (selector.isOpen()) {
            selector.select();
            Set readyKeys = selector.selectedKeys();
            Iterator iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChanel = ssChannel.accept();
                    return socketChanel.socket();
                }
            }
        }
        return null;
    }

    private Socket accept2() throws IOException {
        while (true) {
            SocketChannel client = serverSocket.accept();
            
            if (client == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MyPortRedirectionAuto_deprecated.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Socket socket = client.socket();
                return socket;
            }
        }

    }

    /**
     * *
     * ClientThread is responsible for starting forwarding between * the client
     * and the server. It keeps track of the client and * servers sockets that
     * are both closed on input/output error * durinf the forwarding. The
     * forwarding is bidirectional and * is performed by two ForwardThread
     * instances. *
     */
    class ClientThread extends Thread {

        private Socket mClientSocket;
        private Socket mServerSocket;
        private boolean mForwardingActive = false;
        //
        private final String destHost;
        private final int destPort;
        //
        private ArrayList<ClientThread> clientList;
        private OUT out;

        public ClientThread(Socket aClientSocket, String destHost, int destPort, ArrayList<ClientThread> clientList, OUT out) {
            this.mClientSocket = aClientSocket;
            this.destHost = destHost;
            this.destPort = destPort;
            this.out = out;
            this.clientList = clientList;
        }

        public String getRedirectionInfo() {
            return mClientSocket.getInetAddress().getHostAddress()
                    + ":" + mClientSocket.getPort() + " <--> destHost: " + destHost + " / destPort: " + destPort;
        }

        public void killClient() {
            try {
                mClientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /**
         * *
         * Establishes connection to the destination server and * starts
         * bidirectional forwarding ot data between the * client and the server.
         * *
         */
        @Override
        public void run() {

            InputStream clientIn;

            OutputStream clientOut;

            InputStream serverIn;

            OutputStream serverOut;

            try {

                // Connect to the destination server 

                mServerSocket = new Socket(
                        destHost,
                        destPort);



                // Turn on keep-alive for both the sockets 

                mServerSocket.setKeepAlive(true);

                mClientSocket.setKeepAlive(true);



                // Obtain client & server input & output streams 

                clientIn = mClientSocket.getInputStream();

                clientOut = mClientSocket.getOutputStream();

                serverIn = mServerSocket.getInputStream();

                serverOut = mServerSocket.getOutputStream();

            } catch (IOException ioe) {

//            System.err.println("Can not connect to "
//                    + destHost + ":"
//                    + destPort);
                out.showMessage("Can not connect to "
                        + destHost + ":"
                        + destPort);

                connectionBroken();

                //
                //
                clientList.remove(this);
                //
                out.updateClientCount(clientList.size());

                return;

            }



            // Start forwarding data between server and client 

            mForwardingActive = true;

            ForwardThread clientForward =
                    new ForwardThread(this, clientIn, serverOut);

            clientForward.start();

            ForwardThread serverForward =
                    new ForwardThread(this, serverIn, clientOut);

            serverForward.start();

            String msg = "TCP Forwarding "
                    + mClientSocket.getInetAddress().getHostAddress()
                    + ":" + mClientSocket.getPort() + " <--> "
                    + mServerSocket.getInetAddress().getHostAddress()
                    + ":" + mServerSocket.getPort() + " started.";

//        System.out.println(msg);
            out.showMessage(msg);

        }

        /**
         * *
         * Called by some of the forwarding threads to indicate * that its
         * socket connection is brokean and both client * and server sockets
         * should be closed. Closing the client * and server sockets causes all
         * threads blocked on reading * or writing to these sockets to get an
         * exception and to * finish their execution. *
         */
        public synchronized void connectionBroken() {

            try {

                mServerSocket.close();

            } catch (Exception e) {
            }

            try {

                mClientSocket.close();
            } catch (Exception e) {
            }



            if (mForwardingActive) {

                if (mServerSocket == null) {
                    System.out.println("ServSocket = null");
                }

                if (mClientSocket == null) {
                    System.out.println("ClientSocket = null");
                }

                if (mClientSocket.getInetAddress() == null) {
                    System.out.println("IntetAddress client = null");
                }

                String msg = "TCP Forwarding "
                        + mClientSocket.getInetAddress().getHostAddress()
                        + ":" + mClientSocket.getPort() + " <--> "
                        + mServerSocket.getInetAddress().getHostAddress()
                        + ":" + mServerSocket.getPort() + " stopped.";

//            System.out.println(msg);

                out.showMessage(msg);

                mForwardingActive = false;
                //
                //
                clientList.remove(this);
                //
                out.updateClientCount(clientList.size());
            }

        }
    }

    /**
     * *
     * ForwardThread handles the TCP forwarding between a socket * input stream
     * (source) and a socket output stream (dest). * It reads the input stream
     * and forwards everything to the * output stream. If some of the streams
     * fails, the forwarding * stops and the parent is notified to close all its
     * sockets. *
     */
    class ForwardThread extends Thread {

        private static final int BUFFER_SIZE = 8192;//8192
        InputStream mInputStream;
        OutputStream mOutputStream;
        ClientThread mParent;

        /**
         * *
         * Creates a new traffic redirection thread specifying * its parent,
         * input stream and output stream. *
         */
        public ForwardThread(ClientThread aParent, InputStream aInputStream, OutputStream aOutputStream) {

            mParent = aParent;

            mInputStream = aInputStream;

            mOutputStream = aOutputStream;

        }

        /**
         * *
         * Runs the thread. Continuously reads the input stream and * writes the
         * read data to the output stream. If reading or * writing fail, exits
         * the thread and notifies the parent * about the failure. *
         */
        @Override
        public void run() {

            byte[] buffer = new byte[BUFFER_SIZE];


            try {

                while (true) {

                    int bytesRead = mInputStream.read(buffer);

                    System.out.println("FORWARD: " + new String(buffer));

                    if (bytesRead == -1) {
                        break; // End of stream is reached --> exit 
                    }
                    //
                    mOutputStream.write(buffer, 0, bytesRead);

                    mOutputStream.flush();
                }

            } catch (IOException e) {
                // Read/write failed --> connection is broken 
            }



            // Notify parent thread that the connection is broken 

            mParent.connectionBroken();

        }
    }
}
