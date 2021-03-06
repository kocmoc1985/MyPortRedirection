/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supplementary;

import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class TimerThread implements Runnable {

    private final ActionListener classToNotify;
    private final int timeOut;

    public TimerThread(ActionListener classImplementinAListener, int timeOut) {
        this.classToNotify = classImplementinAListener;
        this.timeOut = timeOut;
    }
    
    public void startThread(){
        Thread x = new Thread(this);
        x.start();
    }

    @Override
    public void run() {
        wait_(timeOut);
        classToNotify.actionPerformed(null);
    }

    private synchronized void wait_(int millis) {
        try {
            wait(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(TimerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
