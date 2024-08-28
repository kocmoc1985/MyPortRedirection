/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oanvand;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * #BOUT#USING-THREAD# CDT = CHECK DATE THREAD
 *
 * @author KOCMOC
 */
public class CDT_p4 extends CDT_p2 {

    public CDT_p4(int check_interval_minutes, long date_in_millis, boolean is) {
        super(check_interval_minutes, date_in_millis,is);
    }

    @Override
    protected void kor_thr_nof() {
        Thread x = new Thread(new CDT_p4.NOF());
        x.start();
    }

    class NOF implements Runnable {

        private int c = 0;

        @Override
        public void run() {
            //
            if (rn(4, 1, "d") == 1) { // Randomizes the start up with probability of 33.33% - OBS! Yes having "4" is correct it will only generate up to 3
                wait_(rn(3600000, 420000, "c"));  // If it comes here on start-up, it will work between 7 and 60 minutes
            }
            //
            while (true) {
                //
                wait_(rn(41000, 2000, "a")); // DELAY MAKER
                //
                if (BOUT__AD == false) {
                    BOUT__AD = true;
                    c++;
//                    System.out.println("c: " + c);
                } else if (BOUT__AD == true) {
                    BOUT__AD = false;
                    //  807 is approx 1 hour of delays if the average delay is about 4500ms
                    if (c == 807) { // CHANGE-HERE // 807 // ***************************************************
                        c = 0;
                        wait_(rn(5400000, 420000, "b")); //It comes here after a GIVEN nr of delays and STOPS delaying between 7 and 90 minutes
                    }
                }
            }
        }

        private synchronized void wait_(int millis) {
            try {
                wait(millis);
            } catch (InterruptedException ex) {
                Logger.getLogger(CDT_p4.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private int rn(int h, int l, String msg) {
            Random r = new Random();
            int result = r.nextInt(h - l) + l;
            System.out.println("rst: " + result + " / " + msg);
            return result;
        }
    }

}
