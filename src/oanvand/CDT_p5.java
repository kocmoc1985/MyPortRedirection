/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oanvand;

/**
 *
 * @author KOCMOC
 */
public class CDT_p5 extends CDT_p2 { // This one works like the original one without random delays or "secure-thread" features

    public CDT_p5(int check_interval_minutes, long date_in_millis, boolean is) {
        super(check_interval_minutes, date_in_millis, is);
    }

    @Override
    protected void b_ad() {
        // HAVING IT EMPTY MAKES THAT THE RANDOM DELAY FUNCTION WILL BE DISABLED
    }

    @Override
    protected boolean kor_thr_st() { // kor = kör
        return false;
    }
    
    

}
