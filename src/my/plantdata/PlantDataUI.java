/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.plantdata;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author AJV
 */


public class PlantDataUI extends javax.swing.JFrame {
    
    public static final String PORT = "COM17";
    DefaultTableModel model;
    static SerialPort serialPort;
    String data = "", plantA = "", plantB = "";
    Timestamp time1, time2;
    long counter1 = 0, counter2 = 0;
    boolean manual = false;
    public static final long MAX_TIME = 1000000; //Time that takes for the water tank to empty. Needs to be adjusted.
    
    
    class SerialPortReader implements SerialPortEventListener {
 
        public void serialEvent(SerialPortEvent event) {
            //Object type SerialPortEvent carries information about which event occurred and a value.
            //For example, if the data came a method event.getEventValue() returns us the number of bytes in the input buffer.
            
            if(event.isRXCHAR()){
                if(event.getEventValue() > 0){
                    try {
                        //byte buffer[] = serialPort.readString(event.getEventValue());
                        String buff = serialPort.readString(event.getEventValue());//new String(buffer);
                        //Change for a switch case!!
                        data = data + buff;
                        System.out.println(data.length());
                        //Keep the flow of data in the original packs of 8 bytes
                        if (data.length() > 7) {
                            if (data.startsWith("A")){
                                if(data.length() > 8){
                                    plantA = data.substring(0, 9);//should be (0, 9)
                                    data = data.substring(9);
                                } else {
                                    plantA = data;
                                    data = "";
                                }
                                System.out.println(plantA);
                                print(plantA);
                                //Set the moisture value to the table
                                model.setValueAt(plantA.substring(1, 4), 0, 1);
                                //Control if the valve is open or not to update the water level in the automatic mode
                                if(!manual){
                                    System.out.println(plantA.charAt(5));
                                    if(plantA.charAt(5) == '1'){
                                        System.out.println(plantA.charAt(5));
                                        if(time1 == null){
                                            System.out.println(plantA.charAt(5));
                                            time1 = new Timestamp(System.currentTimeMillis());
                                        } else {
                                            Timestamp timeaux = new Timestamp(System.currentTimeMillis());
                                            counter1 = counter1 + (timeaux.getTime() - time1.getTime());
                                            System.out.println(counter1);
                                            updateWaterLevel();
                                            time1 = timeaux;
                                        }
                                    } else if (plantA.charAt(5) == '0'){
                                        if(time1 != null){
                                            Timestamp timeaux = new Timestamp(System.currentTimeMillis());
                                            counter1 = counter1 + (timeaux.getTime() - time1.getTime());
                                            updateWaterLevel();
                                            time1 = null;
                                        }
                                    }
                                }
                            //Keep the flow of data in the original packs of 8 bytes
                            } else if (data.startsWith("C")){
                                if(data.length() > 8){
                                    plantB = data.substring(0, 9);//should be (0, 9)
                                    data = data.substring(9);
                                } else {
                                    plantB = data;
                                    data = "";
                                }
                                System.out.println(plantB);
                                print(plantB);
                                //Set the moisture value to the table
                                model.setValueAt(plantB.substring(1, 4), 0, 2);
                                //Control if the valve is open or not to update the water level in the automatic mode
                                if(!manual){    
                                    if(plantB.charAt(5) == '1'){    
                                        if(time2 == null){
                                            time2 = new Timestamp(System.currentTimeMillis());
                                        } else {
                                            Timestamp timeaux = new Timestamp(System.currentTimeMillis());
                                            counter2 = counter2 + (timeaux.getTime() - time2.getTime());
                                            updateWaterLevel();
                                            time2 = timeaux;
                                        }
                                    } else if (plantB.charAt(5) == '0'){
                                        if(time2 != null){
                                            Timestamp timeaux = new Timestamp(System.currentTimeMillis());
                                            counter2 = counter2 + (timeaux.getTime() - time2.getTime());
                                            updateWaterLevel();
                                            time2 = null;
                                        }
                                    }
                                }
                            } else {
                                    //print(buff);
                            }
                        }
                        //print(buff);
                    }
                    catch (SerialPortException ex) {
                        System.out.println(ex);
                    }
                }
            }
            /*//If the CTS line status has changed, then the method event.getEventValue() returns 1 if the line is ON and 0 if it is OFF.
            else if(event.isCTS()){
                if(event.getEventValue() == 1){
                    System.out.println("CTS - ON");
                }
                else {
                    System.out.println("CTS - OFF");
                }
            }
            else if(event.isDSR()){
                if(event.getEventValue() == 1){
                    System.out.println("DSR - ON");
                }
                else {
                    System.out.println("DSR - OFF");
                }
            }*/
        }
    }
    
    /**
     * Creates new form PlantDataUI
     */
    public PlantDataUI() {
        initComponents();
        serialPort = new SerialPort(PORT);
        model = (DefaultTableModel) jTable1.getModel();
        
        super.setLocationRelativeTo(null);
        
        //Creating combo box
        /*String[] a = {"aaa", "sss", "ddd", "fff", "ggg", "hhh"};
        javax.swing.JComboBox c = new javax.swing.JComboBox(a);
        jTable1.getRowModel().getRow(2).setCellEditor(new DefaultCellEditor(c));*/
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jProgressBar2 = new javax.swing.JProgressBar();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jToggleButton3 = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Plant Data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jToggleButton1.setText("Plant 1");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton2.setText("Plant 2");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Information from plants:");

        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Moisture", null, null},
                {"Time", null, null},
                {"Plant type", null, null}
            },
            new String [] {
                "", "Plant 1", "Plant 2"
            }
        ));
        jTable1.setAutoscrolls(false);
        jScrollPane2.setViewportView(jTable1);

        jProgressBar2.setToolTipText("");
        jProgressBar2.setValue(100);
        jProgressBar2.setStringPainted(true);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Water tank:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Manually open valves:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jToggleButton3.setText("Manual");
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                            .addComponent(jProgressBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addGap(41, 41, 41)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jToggleButton1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jToggleButton2))
                                    .addComponent(jToggleButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jToggleButton1)
                            .addComponent(jToggleButton2))
                        .addGap(18, 18, 18)
                        .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(jLabel3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(jButton2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jProgressBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Connect");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Disconnect");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //Exit button
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    //Connection button
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        serialPort = new SerialPort(PORT); 
        try {
            serialPort.openPort();
            serialPort.setParams(38400, 8, 1, 0);
            //Preparing a mask. In a mask, we need to specify the types of events that we want to track.
            //Well, for example, we need to know what came some data, thus in the mask must have the
            //following value: MASK_RXCHAR. If we, for example, still need to know about changes in states 
            //of lines CTS and DSR, the mask has to look like this: SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR
            int mask = SerialPort.MASK_RXCHAR;
            //Set the prepared mask
            serialPort.setEventsMask(mask);
            //Add an interface through which we will receive information about events
            serialPort.addEventListener(new SerialPortReader());
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    //Clear Button
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jTextArea1.setText(null);
        //jProgressBar2.setValue(50); //Set the value of the progress bar of the tank
    }//GEN-LAST:event_jButton2ActionPerformed

    //Manually open valve plant 1
    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        if(serialPort.isOpened()){
            if(jToggleButton1.isSelected()){
                try {
                    serialPort.writeString("1");
                    //print("1");
                    if(manual){
                        time1 = new Timestamp(System.currentTimeMillis());
                    }
                } catch (SerialPortException ex) {
                    Logger.getLogger(PlantDataUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    serialPort.writeString("2");
                    //print("2");
                    if(manual){
                        Timestamp timeaux = new Timestamp(System.currentTimeMillis());
                        counter1 = counter1 + (timeaux.getTime() - time1.getTime());
                        updateWaterLevel();
                        time1 = null;
                    }
                } catch (SerialPortException ex) {
                    Logger.getLogger(PlantDataUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            jTextArea1.setText("Connection not stablished");
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    //Manually open valve plant 2
    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        if(serialPort.isOpened()){
            if(jToggleButton2.isSelected()){
                try {
                    serialPort.writeString("3");
                    //print("3");
                    if(manual){
                        time2 = new Timestamp(System.currentTimeMillis());
                    }
                } catch (SerialPortException ex) {
                    Logger.getLogger(PlantDataUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    serialPort.writeString("4");
                    //print("4");
                    if(manual){
                        Timestamp timeaux = new Timestamp(System.currentTimeMillis());
                        counter2 = counter2 + (timeaux.getTime() - time2.getTime());
                        updateWaterLevel();
                        time2 = null;
                    }
                } catch (SerialPortException ex) {
                    Logger.getLogger(PlantDataUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            jTextArea1.setText("Connection not stablished");
        }
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    //Disconnect button
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            serialPort.closePort();
        } catch (SerialPortException ex) {
            Logger.getLogger(PlantDataUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
        if(serialPort.isOpened()){
            if(jToggleButton3.isSelected()){
                try {
                    serialPort.writeString("5");
                    //print("5");
                    jToggleButton3.setText("Automatic");
                    manual = true;
                    Timestamp timeaux = new Timestamp(System.currentTimeMillis());
                    if(time1 != null){
                        counter1 = counter1 + (timeaux.getTime() - time1.getTime());
                    }
                    if(time2 != null){
                        counter2 = counter2 + (timeaux.getTime() - time2.getTime());
                    }                    
                    updateWaterLevel();
                    time2 = null;
                    time1 = null;
                    
                } catch (SerialPortException ex) {
                    Logger.getLogger(PlantDataUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    serialPort.writeString("6");
                    //print("6");
                    jToggleButton3.setText("Manual");
                    manual = false;
                } catch (SerialPortException ex) {
                    Logger.getLogger(PlantDataUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            jTextArea1.setText("Connection not stablished");
        }
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    //function that prints everything to the Text Area
    private void print(String s){
        jTextArea1.append(s);
    }
    
    //function that updates the waterlevel
    public void updateWaterLevel(){
        //int level = (int)((MAX_TIME-(counter1+counter2))/MAX_TIME)*100;
        
        long level = (counter1+counter2);
        if(level < MAX_TIME){
            level = MAX_TIME - level;
            double lev = (double)level/(double)MAX_TIME;
            lev = lev*100;
            int aux = (int)lev;
            jProgressBar2.setValue(aux);
        } else {
            jProgressBar2.setValue(0);
        }
    }
    
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
            java.util.logging.Logger.getLogger(PlantDataUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PlantDataUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PlantDataUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlantDataUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PlantDataUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    // End of variables declaration//GEN-END:variables
}
