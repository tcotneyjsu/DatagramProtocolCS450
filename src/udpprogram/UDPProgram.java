/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpprogram;

import java.util.Random;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.Timer;
import java.net.*;
import java.net.DatagramSocket;//Had to import both because of constructor errors
import java.net.DatagramPacket;//Had to import both because of constructor errors


/**@Author Travis Cotney, Aaron Branham, Morgan Sweatman
 *
 * 
 */
public class UDPProgram extends javax.swing.JFrame {
    private DatagramSocket socket;
    private byte[] buffer = new byte[256];
    private Random random;
    long startTime;
    long stopTime;
    long waitTime;
    long totalDuration;
    long[] pings;
    int clientSent = 1;
    public boolean received;
    /**
     * Creates new form UDPProgram
     */
    public UDPProgram() {
        initComponents();
    }
    class UDServer extends Thread{
        //@Override
        public void run(){

            try{
                
                    
                // Create a datagram socket, bound to the specific port 2000
                DatagramSocket socket = new DatagramSocket(2000);
                serverText.append("Bound to local port " + socket.getLocalPort()+"\n");
                while(true){ 
                    // Create a datagram packet, containing a maximum buffer of 256 byte 
                DatagramPacket packet = new DatagramPacket( new byte[256], 256 );

                // Receive a packet - remember by default this is a blocking operation
                
                socket.receive(packet);
                if(random.nextInt(3) != 1){
                    //received = true;
                    serverText.append("Packet " + clientSent + " received at " + new Date( )+"\n");
                    // Display packet information
                    InetAddress remote_addr = packet.getAddress();
                    serverText.append("Sender: " + remote_addr.getHostAddress( )+"\n" );
                    serverText.append("from Port: " + packet.getPort()+"\n");

                    // Display packet contents, by reading from byte array
                    ByteArrayInputStream bin = new ByteArrayInputStream(packet.getData());

                    // Display only up to the length of the original UDP packet
                    for (int i=0; i < packet.getLength(); i++)  {
                            int data = bin.read();
                            if (data == -1) break;

                            else serverText.append((char) data+"");
                            
                    }
                    serverText.append("\n\n");
                    try{
                        int whatislove = random.nextInt(201);
                        Thread.sleep(whatislove);
                    }
                    catch(Exception e){}
                    socket.send(packet);
                }
                else{
                    serverText.append("Packet " + clientSent
                            + " was lost, now Hangry c(-_-c), " +"\n"
                            + "continuing to next iterration."+"\n\n");
                    
                    //clientSent++;
                    
                }
                
            }
            }   
            catch (IOException e) 	{
                    serverText.append("Error - " + e);
            }
            
        }
    }
    class UDClient extends Thread{
        //use localhost to experiment on a standalone computer
        //@Override
        public UDClient(){}
        public void run(){
        String hostname="localhost";    String message = "HELLO USING UDP!";
        while(clientSent < 11){
            startTime = System.currentTimeMillis();
            try {
		// Create a datagram socket, look for the first available port
		DatagramSocket socket = new DatagramSocket();

		clientText.append("Using local port: " + socket.getLocalPort()+"\n");
                ByteArrayOutputStream bOut = new ByteArrayOutputStream();
                PrintStream pOut = new PrintStream(bOut);
                pOut.print(message);
                //convert printstream to byte array
                byte [ ] bArray = bOut.toByteArray();
		//Create a datagram packet, containing a maximum buffer of 256 bytes
		DatagramPacket packet=new DatagramPacket( bArray, bArray.length );

                clientText.append("Looking for hostname " + hostname+"\n");
                    //get the InetAddress object
                //InetAddress remote_addr = InetAddress.getByName(hostname);
                byte[] ipAddr = new byte[]{(byte)192, (byte)168, (byte)1, (byte)98};
                InetAddress remote_addr = InetAddress.getByAddress(ipAddr);
                //check its IP number
                clientText.append("Hostname has IP address = " + remote_addr.getHostAddress()+"\n");
                        //configure the DataGramPacket
                        packet.setAddress(remote_addr);
                        packet.setPort(2000);
                        //send the packet
                        socket.send(packet);
		clientText.append("Packet sent at: " + new Date()+"\n");

		// Display packet information
		clientText.append("Sent by  : " + remote_addr.getHostAddress()+"\n");
        		clientText.append("Send from: " + packet.getPort()+"\n");
                packetStatus status = new packetStatus(socket, packet);
                status.start();
                long elapsedTime=0;
                while((elapsedTime < 300) && !received){
                    waitTime = System.currentTimeMillis();
                    elapsedTime = waitTime - startTime;
                
                }
                status.interrupt();
                System.out.println("test2");
                if(received){
                    //socket.receive(packet);
                    stopTime = System.currentTimeMillis();
                    totalDuration = stopTime - startTime;
                    clientText.append("Host acknowledged packet " + clientSent + " after " +
                            totalDuration + " ms"+"\n\n");
                    received = false;
                    clientSent++;
                    
                }
                else {clientText.append("Packet was"
                        + " not acknowleged by the host!\n\n");
                        received = false;
                        clientSent++;
                }
  
		}
                catch (UnknownHostException ue){
                        clientText.append("Unknown host "+hostname+"\n");
                }
		catch (IOException e){
			clientText.append("Error - " + e+"\n");
		}
            }
        }
    }
    class packetStatus extends Thread{
        public DatagramSocket s;
        public DatagramPacket p;
        public packetStatus(DatagramSocket socket, DatagramPacket packet) throws IOException{
            s = socket;
            p = packet;
        }
        @Override
        public void run(){
            try{
                s.receive(p);
                System.out.println("test");
                received = true;
            }
            catch(Exception e){}
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

        jTextField2 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        pingButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        serverText = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        clientText = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        serverCheck = new javax.swing.JCheckBox();
        IPField = new javax.swing.JTextField();

        jTextField2.setText("jTextField2");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pingButton.setText("Ping");
        pingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pingButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("UDP Simulator");

        serverText.setEditable(false);
        serverText.setColumns(20);
        serverText.setRows(5);
        jScrollPane2.setViewportView(serverText);

        clientText.setEditable(false);
        clientText.setColumns(20);
        clientText.setRows(5);
        jScrollPane1.setViewportView(clientText);

        jLabel2.setText("Server");

        jLabel3.setText("Client");

        serverCheck.setText("Server Mode");

        IPField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IPFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 4, Short.MAX_VALUE)
                                .addComponent(pingButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(205, 205, 205)
                                .addComponent(jLabel2)))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(132, 132, 132))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(serverCheck)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(88, 88, 88)
                        .addComponent(IPField, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(IPField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(serverCheck))
                        .addGap(72, 72, 72)
                        .addComponent(pingButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pingButtonActionPerformed
        // TODO add your handling code here:
        random = new Random();
        if(serverCheck.isSelected()){
            Thread thread_1 = new UDServer();
            thread_1.start();
        }
        else{
            Thread thread_2 = new UDClient();
            thread_2.start();  
        }
        
    }//GEN-LAST:event_pingButtonActionPerformed

    private void IPFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IPFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IPFieldActionPerformed

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
            java.util.logging.Logger.getLogger(UDPProgram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UDPProgram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UDPProgram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UDPProgram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UDPProgram().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField IPField;
    private javax.swing.JTextArea clientText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JButton pingButton;
    private javax.swing.JCheckBox serverCheck;
    private javax.swing.JTextArea serverText;
    // End of variables declaration//GEN-END:variables
}
