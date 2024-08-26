/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oanvand;

/**
 *
 * @author KOCMOC
 */
public class test {

    public static void main(String[] args) {
        Thread x = new Thread(new T1());
        x.start();
        //
        Thread x2 = new Thread(new T2());
        x2.start();
    }

   

  
}
