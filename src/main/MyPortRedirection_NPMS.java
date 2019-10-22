/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import supplementary.HelpM;
import supplementary.OUT;
import supplementary.SimpleLoggerLight;
import supplementary.TimerThread;

/**
 * This one is used by "MCRemote" - The server part - NPMS
 * can trigger stand alone redirection channels
 * @author KOCMOC
 */
public class MyPortRedirection_NPMS implements Runnable, ActionListener {

    //WORKING TESTED BY ME (Ihtiandr)
    private int SOURCE_PORT; //1433
    private String DESTINATION_HOST; //10.87.0.2
    private int DESTINATION_PORT;//1433
    private ServerSocket serverSocket;
    private boolean acceptConnections = true;
    private ArrayList<ClientThread> clientList = new ArrayList<ClientThread>();
    private OUT out;
    private String ALLOWED_IP;
    private boolean USED_BY_NMPS = false;

    public MyPortRedirection_NPMS(int srcPort, String destHost, int destPort, OUT out) {
        this.SOURCE_PORT = srcPort;
        this.DESTINATION_HOST = destHost;
        this.DESTINATION_PORT = destPort;
        this.out = out;
    }

    /**
     * This constructor is used by NMPS when it receives 
     * "RDP_REDIRECTION_CMD_ADVANCED" from the RDPComm client
     * @param srcPort
     * @param destHost
     * @param destPort
     * @param allowedIP
     * @param out 
     */
    public MyPortRedirection_NPMS(int srcPort, String destHost, int destPort, String allowedIP, OUT out) {
        this.SOURCE_PORT = srcPort;
        this.DESTINATION_HOST = destHost;
        this.DESTINATION_PORT = destPort;
        this.ALLOWED_IP = allowedIP;
        this.out = out;
        this.USED_BY_NMPS = true;
    }

    public void start() {
        Thread x = new Thread(this);
        x.start();
    }

    public static void main(String[] args) {
        //
        // The compiled module shall be called "redir.jar"
        //
        final String LOG_FILE = "redirx.log";
        //
        HelpM.err_output_to_file();
        //
        String argStr = null;
        //
        // awaiting string of format:
        // [allowed client ip]#[redirection cmd]
        // [85.227.250.31]#[localhost 65520 10.87.0.5 3389] -> no brackets i real string
        //  85.227.250.31#localhost 65520 10.87.0.5 3389
        try {
            argStr = args[0];
        } catch (Exception ex) {
            Logger.getLogger(MyPortRedirection_NPMS.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        //
        String srcHost;
        String srcPort;
        String destHost;
        String destPort;
        //
        try {
            if (argStr != null && argStr.isEmpty() == false) {
                String[] arr_a = argStr.split("#");
                String allowed_ip = arr_a[0];
                String redir_cmd = arr_a[1];
                //
                String[] arr_b = redir_cmd.split(" ");
                srcHost = arr_b[0];
                srcPort = arr_b[1];
                destHost = arr_b[2];
                destPort = arr_b[3];
                //
                SimpleLoggerLight.logg("redirx.log", "ip: " + allowed_ip + " / " + redir_cmd);
                //
                MyPortRedirection_NPMS mpr = new MyPortRedirection_NPMS(Integer.parseInt(srcPort), destHost, Integer.parseInt(destPort),allowed_ip, new OUT() {
                    @Override
                    public void showMessage(String msg) {
                        SimpleLoggerLight.logg(LOG_FILE, msg);
                    }

                    @Override
                    public void updateStatus(String status) {
                        SimpleLoggerLight.logg(LOG_FILE, status);
                    }

                    @Override
                    public void updateClientCount(int clients) {
                        SimpleLoggerLight.logg(LOG_FILE, "" + clients);
                    }
                });
                //
                mpr.start();
                //
            }
        } catch (Exception ex) {
            Logger.getLogger(MyPortRedirection_NPMS.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        //
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
            Logger.getLogger(MyPortRedirection_NPMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            startTimerThread();
            go();
        } catch (IOException ex) {
            Logger.getLogger(MyPortRedirection_NPMS.class.getName()).log(Level.SEVERE, null, ex);
            if(acceptConnections){
              out.showMessage("Server socket, cannot bind port: " + SOURCE_PORT);  
            }
//            out.updateStatus("down");
        }
    }
    
    /**
     * To kill the listening server socket after some time
     */
    private void startTimerThread(){
        TimerThread tt = new TimerThread(this);
        tt.startThread();
    }

    private void go() throws IOException {
        //
        serverSocket = new ServerSocket(SOURCE_PORT);
        //
        out.showMessage("listening for connections on port: " + SOURCE_PORT);
        //
        while (acceptConnections) {
            //
            Socket clientSocket = serverSocket.accept();
            //
            out.showMessage("client connected: " + "srcPort: " + SOURCE_PORT + "  " + "destHost: " + DESTINATION_HOST + "  " + "destPort: " + DESTINATION_PORT);
            //
            String clientIp = clientSocket.getInetAddress().getHostAddress();
            //
            if(ALLOWED_IP != null && USED_BY_NMPS){
                //
                if(clientIp.equals(ALLOWED_IP) == false){
                    out.showMessage("ClienSocket closed, connection from a none listed ip: " + clientIp + " / allowed ip: " + ALLOWED_IP);
                    clientSocket.close();
                    break;
                }
                //
            }
            //
            //
            ClientThread clientThread
                    = new ClientThread(clientSocket, DESTINATION_HOST, DESTINATION_PORT, clientList, out);
            //
            clientThread.start();
            //
            clientList.add(clientThread);
            //
            out.updateClientCount(clientList.size());
            //
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        out.showMessage("Redirection destroyed");
        closeServerSocket();
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

            ForwardThread clientForward
                    = new ForwardThread(this, clientIn, serverOut);

            clientForward.start();

            ForwardThread serverForward
                    = new ForwardThread(this, serverIn, clientOut);

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

        private static final int BUFFER_SIZE = 8192;
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

//                    System.out.println("FORWARD: " + new String(buffer));
                    if (bytesRead == -1) {
                        break; // End of stream is reached --> exit 
                    }
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
