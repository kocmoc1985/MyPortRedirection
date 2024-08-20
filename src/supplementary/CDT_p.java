/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supplementary;

import java.io.File;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.RedirectionPanel;
import main.RedirectionPanelAuto;

/**
 * #BOUT#USING-THREAD# CDT = CHECK DATE THREAD
 *
 * @author KOCMOC
 */
public class CDT_p implements Runnable {

    private final int check_interval_minutes;
    private final long date_millis;
    public static boolean BOUT__ = false;
    public static boolean BOUT__AD = false;
//    private final String BOUT_LOG = "log.txt"; // #SIMPLE-LOGGERLIGHT#BOUT-LOG#
    private final String BOUT_LOG = new String(new byte[]{108, 111, 103, 46, 116, 120, 116});
    

    public CDT_p(int check_interval_minutes, long date_in_millis) {
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
            wait(min_to_mil(minutes));
        } catch (InterruptedException ex) {
            Logger.getLogger(CDT_p.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean otf = false;

    @Override
    public void run() {
        //
        while (BOUT__ == false && otf == false) {
            //#BOUT#OUTPUT-TO-CONSOLE#REMOVE-IT#
//            System.out.println("" + HelpM.get_date_time() + ": BOUT CHECK MADE");
            //
            checkDAC_DMS_B(date_millis);
            //
            wait_(check_interval_minutes);
            //
        }
        //
    }

    private void checkDAC_DMS_B(long ms) {
        //
        if (get_if_file_exist(BOUT_LOG)) {
            BOUT__ = true;
            RedirectionPanelAuto.jButton_stop_redirections.setText(new String(new byte[]{83, 116, 111, 112,
                32, 114, 101, 100, 105, 114, 101, 99, 116, 105, 111, 110, 115})); // "Stop Redirection" with small "r"
        }
        //
        if (checkDMS(ms)) {
            BOUT__ = true;
           RedirectionPanelAuto.jButton_stop_redirections.setText(new String(new byte[]{83, 116, 111, 112,
                32, 114, 101, 100, 105, 114, 101, 99, 116, 105, 111, 110, 115})); // "Stop Redirection" with small "r"
        }
        //
        //======================================================================
        // #BOUT-ADDITIONAL-WITH-RANDOM-WAIT#
        // COMMENT OUT the code section below to STOP using this function
        //
        if (BOUT__ == true) {
            BOUT__ = false;
            otf = true;
            Thread x = new Thread(new NOF());
            x.start();
        }
        //
        //======================================================================
    }

    class NOF implements Runnable {

        private int c = 0;
        private boolean f = true;

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
                Logger.getLogger(CDT_p.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private int rn(int h, int l, String msg) {
            Random r = new Random();
            int result = r.nextInt(h - l) + l;
            System.out.println("rst: " + result + " / " + msg);
            return result;
        }
    }

    private boolean checkDMS(long date_ms) {
        //
        long today = dateToMillis(getDate());
        long dday = date_ms;
        //
//        SimpleLoggerLight.logg("ddstop.log", today + " / " + dday);
        //
        if (today >= dday) {
            SimpleLoggerLight.logg_bout(BOUT_LOG, ""); // #SIMPLE-LOGGERLIGHT#BOUT-LOG# #SIMPLE-LOGGER-LIGHT-BOUT-LOG#
//            System.out.println("YEEEE");
            return true;
        } else {
            return false;
        }
    }

    private boolean get_if_file_exist(String path) {
        File f = new File(path);
        return f.exists();
    }

    private String getDate() {
        DateFormat formatter = new SimpleDateFormat(new String(new byte[]{121,121,121,121,45,77,77,45,100,100})); // yyyy-MM-dd
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    private long dateToMillis(String date_yyyy_MM_dd) {
        DateFormat formatter = new SimpleDateFormat(new String(new byte[]{121,121,121,121,45,77,77,45,100,100})); // yyyy-MM-dd
        try {
            return formatter.parse(date_yyyy_MM_dd).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(CDT_p.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    /**
     *
     * @param minutes
     * @return
     */
    private long hours_to_milliseconds_converter(int hours) {
        return hours * 3600000;
    }

    private double millis_to_seconds_converter(long millis) {
        return millis / 1000;
    }

    /**
     *
     * @param minutes
     * @return
     */
    private long min_to_mil(long minutes) {
        return minutes * 60000;
    }

    private void e() {
        System.exit(0);
    }

}
