/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unused;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class T3 {

    public static final String GREETING = "Hello I must be going.\r\n";
    
    public static void main(String[] args) {
        T3 t3 = new T3();
        try {
            t3.test();
        } catch (IOException ex) {
            Logger.getLogger(T3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(T3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void test() throws IOException, InterruptedException {
       int port = 1234; // default

    ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());
    ServerSocketChannel ssc = ServerSocketChannel.open();

    ssc.socket().bind(new InetSocketAddress(port));
    ssc.configureBlocking(false);

    while (true) {
      System.out.println("Waiting for connections");

      SocketChannel sc = ssc.accept();

      if (sc == null) {
        Thread.sleep(2000);
      } else {
        System.out.println("Incoming connection from: " + sc.socket().getRemoteSocketAddress());

        buffer.rewind();
        sc.write(buffer);
        sc.close();
      }
    }
    }
}
