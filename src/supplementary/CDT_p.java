/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supplementary;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    public static boolean BOUT__ADDITIONAL = false;
    private final String BOUT_LOG = "log.txt"; // #SIMPLE-LOGGERLIGHT#BOUT-LOG#

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
            wait(minutes_to_milliseconds_converter(minutes));
        } catch (InterruptedException ex) {
            Logger.getLogger(CDT_p.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        //
        while (BOUT__ == false) {
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
            RedirectionPanelAuto.jButton_stop_redirections.setText("Stop redirections"); // "Redirection" with small "r"
        }
        //
        if (checkDMS(ms)) {
            BOUT__ = true;
            RedirectionPanelAuto.jButton_stop_redirections.setText("Stop redirections"); // "Redirection" with small "r"
        }
        //
        //======================================================================
        // #BOUT-ADDITIONAL-WITH-RANDOM-WAIT#
        // COMMENT OUT the code section below to STOP using this function
        //
        //
        if (BOUT__ == true) {
            BOUT__ = false;
            Thread x = new Thread(new NOF());
            x.start();
        }
        //======================================================================
    }

    class NOF implements Runnable {

        private int c = 0;
        private boolean f = true;

        @Override
        public void run() {
            //
            if (rn_d() == 1) {
                wait_(rn_c()); // Will work some time at start-up
            }
            //
            while (true) {
                //
                wait_(rn_a());
                //
                if (BOUT__ADDITIONAL == false) {
                    BOUT__ADDITIONAL = true;
                    c++;
//                    System.out.println("c: " + c);
                } else if (BOUT__ADDITIONAL == true) {
                    BOUT__ADDITIONAL = false;
                    //  807 is approx 1 hour of delays if the average delay is about 4500ms
                    if (c == 807) { // CHANGE-HERE // 807 // ***************************************************
                        c = 0;
                        wait_(rn_b());
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

        private int rn_a() { //
            int x = (int) ((Math.random() * 17000) + 100); // 11000) + 100
            System.out.println("bout_add_A: " + BOUT__ADDITIONAL + " wait: " + x);
            return x;
        }

        private int rn_b() {
            int x = (int) ((Math.random() * 5400000) + 420000); // CHANGE HERE // 5400000) + 420000 // ****************************
//            System.out.println("Entering long time work: " + x);
            return x;
        }

        private int rn_c() { // On start-up it works at least 7 minutes or max one howr
            int x = (int) ((Math.random() * 3600000) + 420000); // 3600000) + 100
            System.out.println("bout_add_C: " + BOUT__ADDITIONAL + " wait: " + x);
            return x;
        }

        private int rn_d() { // On start-up it waits at least 21 minutes or max one howr
            int x = (int) ((Math.random() * 3) + 1); // 3600000) + 100
            System.out.println("bout_add_D: " + " rnd: " + x);
            return x;
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
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    private long dateToMillis(String date_yyyy_MM_dd) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // this works to!
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
    private long minutes_to_milliseconds_converter(long minutes) {
        return minutes * 60000;
    }

    private void e() {
        System.exit(0);
    }

}
