/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unused;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import static unused.CDT_p.BOUT__AD;

/**
 *
 * @author KOCMOC
 */
public class CDT_p3 extends CDT_p2 {

    public CDT_p3(int check_interval_minutes, long date_in_millis) {
        super(check_interval_minutes, date_in_millis);
    }

    @Override
    protected void startThread_() {
        Thread x = new Thread(new CDT_p3.NOF());
        x.start();
    }
    
    

    class NOF implements Runnable {
        //
        private int c = 0;
        //
        @Override
        public void run() {
            //
//            System.out.println("THREAD RND started");
            //
            wait_(rn(8280000, 1920000, "a")); // WILL ALWAYS WORK SOME TIME AT START-UP BETWEEN 32 min and 138 minutes
            //
            while (true) {
                //
                wait_(rn(120000, 45000, "b")); // 120000, 45000, // between 45 and 120 seconds DELAYS 
                //
                if (BOUT__AD == false) {
                    BOUT__AD = true;
                    c++;
//                    System.out.println("c: " + c);
                } else if (BOUT__AD == true) {
                    BOUT__AD = false;
                    //  
                    if (c == 23) { // About  //CHANGE-HERE // ***************************************************
                        c = 0;
                        wait_(rn(21600000, 3600000, "c")); // 21600000, 3600000, // between 6 and 1 HOURS - NO DELAYS
                    }
                }
            }
        }

        private int rn(int h, int l, String msg) {
            Random r = new Random();
            int result = r.nextInt(h - l) + l;
//            System.out.println("rst: " + result + " / " + msg);
            return result;
        }

        private synchronized void wait_(int millis) {
            try {
                wait(millis);
            } catch (InterruptedException ex) {
                Logger.getLogger(CDT_p2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
