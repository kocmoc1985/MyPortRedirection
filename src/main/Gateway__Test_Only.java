/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 * This idea didn't work :/
 * @author KOCMOC
 */
public class Gateway__Test_Only extends RedirectionPanelAuto{

    @Override
    public void startRedirections() {
        for (int i = 0; i < 65535; i++) {
            showMessage(i + " --> " + "192.168.2.1" + ": " + i);
            //
            MyPortRedirection mpr = new MyPortRedirection(i, "192.168.2.1", i, this);
            portRedirectionList.add(mpr);
            //
            Thread x = new Thread(mpr);
            x.start();
        }
    }
    
    public static void main(String[] args) {
         java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Gateway__Test_Only().setVisible(true);
            }
        });
    }
    
}
