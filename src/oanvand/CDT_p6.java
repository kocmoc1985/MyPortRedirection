/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oanvand;

import main.RedirectionPanelAuto;
import static oanvand.CDT_p2.BOUT__;

/**
 *
 * @author KOCMOC
 */
public class CDT_p6 extends CDT_p2 {

    public CDT_p6(int check_interval_minutes, long date_in_millis) {
        super(check_interval_minutes, date_in_millis);
    }

    @Override
    public void run() {
        BOUT__ = true;
        strb();
    }

}
