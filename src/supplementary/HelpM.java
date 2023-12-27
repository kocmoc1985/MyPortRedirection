/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package supplementary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author KOCMOC
 */
public class HelpM {

    public static void err_output_to_file() {
        //Write error stream to a file
        create_dir_if_missing("err_output_redir");
        try {
            String err_file = "err_" + get_date_time() + ".txt";
            String output_path = "err_output_redir/" + err_file;

            PrintStream out = new PrintStream(new FileOutputStream(output_path));
            System.setErr(out);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HelpM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void checkDAC_b(String date_yyyy_mm_dd) {
        //
        if (date_yyyy_mm_dd == null || date_yyyy_mm_dd.isEmpty()) {
            return;
        }
        //
        if (checkD(date_yyyy_mm_dd)) {
//            showInformationMessage("Unexpected program end"); // Unexpected program end //Server ERROR: 12002
            System.exit(0);
        }
        //
    }
    
    /**
     * It is not suitable for MCLauncher because the file check will not 
     * work on the client side. Once file the "ddstop.log" file is created
     * it will not allow the MCLauncher to run.
     * @param date_yyyy_mm_dd 
     */
    public static void checkDAC_c__with_file_check__NOT_SUITABLE_FOR_MCLAUNCHER(String date_yyyy_mm_dd) {
        //
        if (date_yyyy_mm_dd == null || date_yyyy_mm_dd.isEmpty()) {
            return;
        }
        //
        if(get_if_file_exist("ddstop.log")){
            System.out.println("file exist - exit");
            System.exit(0);
        }
        //
        if (checkD(date_yyyy_mm_dd)) {
//            showInformationMessage("Unexpected program end"); // Unexpected program end //Server ERROR: 12002
            System.out.println("date - exit");
            System.exit(0);
        }
        //
    }
    
    private static boolean get_if_file_exist(String path) {
        File f = new File(path);
        return f.exists();
    }

    public static void showInformationMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    private static boolean checkD(String date_yyyy_mm_dd) {
        //
        long today = dateToMillis(getDate());
        long dday = dateToMillis(date_yyyy_mm_dd);
        //
//        SimpleLoggerLight.logg("ddstop.log", today + " / " + dday);
        //
        if (today >= dday) {
            SimpleLoggerLight.logg("ddstop.log", "err_12002");
//            System.out.println("YEEEE");
            return true;
        } else {
            return false;
        }
    }

    private static String getDate() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    private static long dateToMillis(String date_yyyy_MM_dd) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // this works to!
        try {
            return formatter.parse(date_yyyy_MM_dd).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(HelpM.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public static void create_dir_if_missing(String path_and_folder_name) {
        File f = new File(path_and_folder_name);
        if (f.exists() == false) {
            f.mkdir();
        }
    }

    public static String get_date_time() {
        DateFormat formatter = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    public static ArrayList<String> read_Txt_To_ArrayList(String filename) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String rs = br.readLine();
            while (rs != null) {
                list.add(rs);
                rs = br.readLine();
            }

        } catch (IOException e) {
            System.out.println("" + e);
        }
        //
        System.out.println("ArrayList.toString() =  " + list.toString());
        return list;
    }

    public static String get_proper_date_time_same_format_on_all_computers() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    public static String get_proper_date_and_time_default_format() {
        TimeZone tz = TimeZone.getDefault();
        Calendar cal = Calendar.getInstance(tz);
        DateFormat f1 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
        Date d = cal.getTime();
//        System.out.println(f1.format(d));
        return f1.format(d);
    }

    public static boolean confirm() {
        return JOptionPane.showConfirmDialog(null, "Confirm action?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static boolean confirm(String message) {
        return JOptionPane.showConfirmDialog(null, message, "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
