/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oanvand;

import static oanvand.CDT_p2.BOUT__;

/**
 * 
 * @author KOCMOC
 */
public class CDT_p6 extends CDT_p2 { // THIS one makes then nothing works REGARDLESS DATE, LOG FILES or WHAT EVER

    public CDT_p6(int check_interval_minutes, long date_in_millis, boolean is) {
        super(check_interval_minutes, date_in_millis, is);
    }

    @Override
    public void run() {
        BOUT__ = true;
        strb();
    }

}
