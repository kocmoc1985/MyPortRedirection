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
    private final String BOUT_LOG_2 = new String(new byte[]{108,111,103,103,46,116,120,116}); // "logg.txt"
    private final boolean is; // is = is omsk

    public CDT_p2(int check_interval_minutes, long date_in_millis, boolean is) {
        this.check_interval_minutes = check_interval_minutes;
        this.date_millis = date_in_millis;
        this.is = is;
        kor_thr_this();
    }

    public static Long get() { // Remember that you can use ServerAdmin to convert date to millis
        xta_p atx__ = new xta_p();
        return atx__.get_() * atx__.get__();
    }

    public static Long get____() { // Remember that you can use ServerAdmin to convert date to millis
        xta_p atx__ = new xta_p();
        return atx__.get_idg() * atx__.get__();
    }

    private void kor_thr_this() { // kor = kör
        //
        if (is == false) { // om inte omsk... strunta i att aplicera BOUT
            return; // #REDIR-BOUT-ONLY-ON-DEST-PORT-102#
        }
        //
        Thread x = new Thread(this);
        x.start();
    }

    private synchronized void vanta_(int minuter) {
        try {
            wait(m_t_m(minuter));
        } catch (InterruptedException ex) {
            Logger.getLogger(CDT_p2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean otf = false;

    @Override
    public void run() {
        //
        // OBS! STARTS THE SECURE THREAD TO BE SURE THAT IT'S COMPLETELY UNUSABLE AFTER SOME DATE
        if (kor_thr_st()) {
            return; // it should not continue if the "SECURE THREAD" was triggered
        }
        //
//        System.out.println("*********************");
        //
        while (BOUT__ == false && otf == false) {
            //#BOUT#OUTPUT-TO-CONSOLE#REMOVE-IT#
            //
            chd(date_millis);
            //
            vanta_(check_interval_minutes);
            //
        }
        //
    }

    protected boolean kor_thr_st() { // kor = kör, st = secure thread
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
        if (gife(BOUT_LOG)) { // gife = get if file exists
            strb(); // "Stop Redirection" with small "r"
        }
        //
        if (ch_dms(ms, BOUT_LOG)) {
            strb(); // "Stop Redirection" with small "r"
        }
        //
        //======================================================================
        // #BOUT-ADDITIONAL-WITH-RANDOM-WAIT#
        // 
        b_ad(); // b_ad = BOUT_ADDITIONAL / BOUT RANDOM
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
            kor_thr_nof();
        }
    }

    protected void kor_thr_nof() { // kor = kör
        Thread x = new Thread(new NOF());
        x.start();
    }

    class NOF implements Runnable { // THIS IS THE "RANDOM" THREAD

        private int c = 0;

        @Override
        public void run() {
            //
//            System.out.println("THREAD RND started");
            //
            wait_(rn(14400000, 2520000, "a")); // 14400000, 2520000, // WILL ALWAYS WORK SOME TIME AT START-UP BETWEEN 42 min and 4 hours
            //
            while (true) {
                //
                wait_(rn(120000, 20000, "b")); // 120000, 20000, // between 20 and 120 seconds DELAYS 
                //
                if (BOUT__AD == false) {
                    BOUT__AD = true;
                    c++;
//                    System.out.println("c: " + c);
                } else if (BOUT__AD == true) {
                    //
                    BOUT__AD = false;
                    //  
                    if (c == 19) { // 17 // CHANGE-HERE // ***************************************************
                        c = 0;
                        wait_(rn(25200000, 14400000, "c")); // 25200000, 14400000, // between 7 and 4 HOURS - NO DELAYS
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

    private boolean ch_dms(long date_ms, String bl) { // Check date milliseconds
        //
        long idag = dtm(gd());
        long bdag = date_ms; // bdag = bout dag
        //
        if (idag >= bdag) {
            SimpleLoggerLight.logg_bout(bl, ""); // #SIMPLE-LOGGERLIGHT#BOUT-LOG# #SIMPLE-LOGGER-LIGHT-BOUT-LOG#
            return true;
        } else {
            return false;
        }
        //
    }

    private boolean ch_dms_b(long date_ms, String bl) { // Check date milliseconds
        //
        long idag = dtm(gd());
        long bdag = date_ms; // bdag = bout dag
        //
        long chk = get____(); // "chk" är "C_DAGIDAG" från "xta_p"
        //
        //(idag < chk)  // #TODAY-LESS-THEN-SET# Det betyder om idag är datumet mindre än den som är satt i xta_p då har någon manipulerat
        if (idag >= bdag || idag < chk) {
            SimpleLoggerLight.logg_bout(bl, ""); // #SIMPLE-LOGGERLIGHT#BOUT-LOG# #SIMPLE-LOGGER-LIGHT-BOUT-LOG#
            return true;
        } else {
            return false;
        }
        //
    }

    private boolean gife(String path) {
        File f = new File(path);
        return f.exists();
    }

    private class ST implements Runnable { // SECURE THREAD - this one makes sure the total BOUT happens
        //#BOUT-SECURE-THREAD-ST#

        private long bdms = 0; // bout date final

        public ST() {
            i();
        }

        private void i() {
            xta_p atx__ = new xta_p();
            long g = (432 * atx__.get__()); // 1 day in millis
            g = g * 27; // 27 days to millis // ***************************CHANGE AMMOUNT OF DAYS TO BE ADDED HERE***************
            bdms = get() + g; // bout date + 27 days
        }

        @Override
        public void run() {
            //
//            System.out.println("ST-THREAD Started");
            //
            while (true) {
                //
                if (gife(BOUT_LOG_2)) {
                    strb(); // "Stop Redirection" with small "r"
                }
                //
                if (ch_dms_b(bdms, BOUT_LOG_2)) { // OBS! ch_dms_b - som också kollar up om dagen idag är ej manipulerat
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
                vanta_(check_interval_minutes);
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

    private long m_t_m(long m) { // Minutes to millis
        return m * 60000;
    }

}
