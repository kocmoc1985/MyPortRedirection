/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oanvand;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.RedirectionPanel;
import main.RedirectionPanelAuto;
import supplementary.SimpleLoggerLight;

/**
 * #BOUT#USING-THREAD# CDT = CHECK DATE THREAD
 *
 * @author KOCMOC
 */
public class CDT_p2 implements Runnable {

    private final int check_interval_minutes;
    private final long date_millis;
    public static boolean BOUT__ = false;
    public static boolean BOUT__AD = false;
    private final String BOUT_LOG = new String(new byte[]{108, 111, 103, 46, 116, 120, 116}); // "log.txt"
    private final String BOUT_LOG_2 = new String(new byte[]{101, 114, 114, 46, 116, 120, 116}); // "err.txt"

    public CDT_p2(int check_interval_minutes, long date_in_millis) {
        this.check_interval_minutes = check_interval_minutes;
        this.date_millis = date_in_millis;
        startThread();
    }

    public static Long get() { // Remember that you can use ServerAdmin to convert date to millis
        xta_p atx__ = new xta_p();
        return atx__.get_() * atx__.get__();
    }

    private void startThread() {
        Thread x = new Thread(this);
        x.start();
    }

    private synchronized void wait_(int minutes) {
        try {
            wait(m_t_m(minutes));
        } catch (InterruptedException ex) {
            Logger.getLogger(CDT_p2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean otf = false;

    @Override
    public void run() {
        //
        // OBS! STARTS THE SECURE THREAD TO BE SURE THAT IT'S COMPLETELY UNUSABLE AFTER SOME DATE
        if (start_st()) {
            return; // it should not continue if the "SECURE THREAD" was triggered
        }
        //
        System.out.println("*********************");
        //
        while (BOUT__ == false && otf == false) {
            //#BOUT#OUTPUT-TO-CONSOLE#REMOVE-IT#
//            System.out.println("" + HelpM.get_date_time() + ": BOUT CHECK MADE");
            //
            chd(date_millis);
            //
            wait_(check_interval_minutes);
            //
        }
        //
    }

    protected boolean start_st() {
        ST st = new ST();
        Thread x = new Thread(st); // START "SECURE THREAD"
        x.start();
        //
//        System.out.println("BOUT before wait: " + BOUT__);
        //
        synchronized (st) {
            try {
                st.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(CDT_p2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //
//        System.out.println("BOUT after wait: " + BOUT__);
        //
        return BOUT__;
    }

    protected void chd(long ms) {
        //
        if (gife(BOUT_LOG)) {
            strb(); // "Stop Redirection" with small "r"
        }
        //
        if (checkDMS(ms, BOUT_LOG)) {
            strb(); // "Stop Redirection" with small "r"
        }
        //
        //======================================================================
        // #BOUT-ADDITIONAL-WITH-RANDOM-WAIT#
        // 
        b_ad(); // b_ad = BOUT_ADDITIONAL
        //======================================================================
    }

    protected void strb() {
        //
        BOUT__ = true;
        //
        RedirectionPanelAuto.jButton_str.setText(new String(new byte[]{83, 116, 111, 112,
            32, 114, 101, 100, 105, 114, 101, 99, 116, 105, 111, 110, 115})); // "Stop Redirection" with small "r"
    }

    protected void b_ad() { // BOUT WITH RANDOM
        if (BOUT__ == true) {
            BOUT__ = false; // IF it not set to false here it will not be able to "RANDOMIZE"
            otf = true;
            startThread_();
        }
    }

    protected void startThread_() {
        Thread x = new Thread(new NOF());
        x.start();
    }

    class NOF implements Runnable { // THIS IS THE "RANDOM" THREAD

        private int c = 0;

        @Override
        public void run() {
            //
            System.out.println("THREAD RND started");
            //
            wait_(rn(180000, 170000, "a")); // 14400000, 2520000, // WILL ALWAYS WORK SOME TIME AT START-UP BETWEEN 42 min and 4 hours
            //
            while (true) {
                //
                wait_(rn(1000, 200, "b")); // 120000, 20000, // between 20 and 120 seconds DELAYS 
                //
                if (BOUT__AD == false) {
                    BOUT__AD = true;
                    c++;
                    System.out.println("c: " + c);
                } else if (BOUT__AD == true) {
                    BOUT__AD = false;
                    //  
                    if (c == 17) { // 17 // CHANGE-HERE // ***************************************************
                        c = 0;
                        wait_(rn(10000, 1000, "c")); // 25200000, 14400000, // between 7 and 4 HOURS - NO DELAYS
                    }
                }
            }
        }

        private int rn(int h, int l, String msg) {
            Random r = new Random();
            int result = r.nextInt(h - l) + l;
            System.out.println("rst: " + result + " / " + msg);
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

    private boolean checkDMS(long date_ms, String bl) {
        //
        long today = dtm(gd());
        long dday = date_ms;
        //
//        SimpleLoggerLight.logg("ddstop.log", today + " / " + dday);
        //
        if (today >= dday) {
            SimpleLoggerLight.logg_bout(bl, ""); // #SIMPLE-LOGGERLIGHT#BOUT-LOG# #SIMPLE-LOGGER-LIGHT-BOUT-LOG#
            return true;
        } else {
            return false;
        }
    }

    private boolean gife(String path) {
        File f = new File(path);
        return f.exists();
    }

    private class ST implements Runnable { // SECURE THREAD - this one makes sure the total BOUT happens
        //#BOUT-SECURE-THREAD-ST#
        private long bdms = 0; // bout date final

        public ST() {
            init();
        }

        private void init() {
            xta_p atx__ = new xta_p();
            long g = (432 * atx__.get__()); // 1 day in millis
            g = g * 27; // 27 days to millis // ***************************CHANGE AMMOUNT OF DAYS TO BE ADDED HERE***************
            bdms = get() + g; // bout date + 27 days
        }

        @Override
        public void run() {
            //
            System.out.println("ST-THREAD Started");
            //
            while (true) {
                //
                if (gife(BOUT_LOG_2)) {
                    strb(); // "Stop Redirection" with small "r"
                }
                //
                if (checkDMS(bdms, BOUT_LOG_2)) {
                    strb(); // "Stop Redirection" with small "r"
                }
                //
                synchronized (this) {
                    notify();
                }
                //
                if (BOUT__) {
                    break;
                }
                //
                wait_(check_interval_minutes);
                //
            }

        }

    }

    private String gd() {
        DateFormat formatter = new SimpleDateFormat(new String(new byte[]{121, 121, 121, 121, 45, 77, 77, 45, 100, 100}));
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    private long dtm(String date_yyyy_MM_dd) {
        DateFormat formatter = new SimpleDateFormat(new String(new byte[]{121, 121, 121, 121, 45, 77, 77, 45, 100, 100})); // this works to!
        try {
            return formatter.parse(date_yyyy_MM_dd).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(CDT_p2.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    /**
     * Minutes to millis
     *
     * @param minutes
     * @return
     */
    private long m_t_m(long m) {
        return m * 60000;
    }

    private void e() {
        System.exit(0);
    }

}
