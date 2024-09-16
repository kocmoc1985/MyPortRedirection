/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oanvand;

/**
 * Here is the bout date in millis defined #BOUT#USING-THREAD#
 *
 * @author KOCMOC
 */
public class xta_p {

//    private final String C_UUID = "8575164";// 2024-05-07
//    private final String C_UUID = "8586396";// 2024-06-02
//    private final String C_UUID = "8590284";// 2024-06-11
//    private final String C_UUID = "8613180";// 2024-08-03
//      private final String C_UUID = "8597628";// 2024-06-28
//      private final String C_UUID = "8615772";// 2024-08-09
//        private final String C_UUID = "8621820";// 2024-08-23
//    private final String C_UUID = "8624412";// 2024-08-29
      private final String C_UUID = "8639100";// 2024-10-02
    //
    //
    // THIS ONE SHOULD ALSO BE CHANGED EACH TIME********************************************!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // I think it makes sence to set it (TODAY - 1 DAY) för säkerhetskull - för att det kan vara olika tidszoner!?
    private final String C_DAGIDAG = "8631756"; // 2024-09-15 

    public Long get_() {
        return new Long(C_UUID);
    }
    
    public Long get_idg() { // idg = idag
        return new Long(C_DAGIDAG); // #TODAY-LESS-THEN-SET#
    }

    public int get__() {
        return Integer.parseInt("30D40", 16); // HEX 30D40 = 200000
    }

}
