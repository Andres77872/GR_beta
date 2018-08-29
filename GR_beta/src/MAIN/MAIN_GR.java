/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MAIN;

/**
 *
 * @author Andres
 */
public class MAIN_GR extends javax.swing.JFrame {

    /**
     * Creates new form MAIN_GR
     */
    public MAIN_GR() {
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField_Nodes = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField_Arcs = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField_Tech = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField_Lines = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField_Years = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField_Fuels = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField_TS = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("DT"));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel2.setLayout(new java.awt.GridLayout(0, 2));

        jLabel1.setBackground(new java.awt.Color(153, 153, 153));
        jLabel1.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Nodes");
        jPanel2.add(jLabel1);

        jTextField_Nodes.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jTextField_Nodes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_Nodes.setText("53");
        jTextField_Nodes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_NodesFocusGained(evt);
            }
        });
        jPanel2.add(jTextField_Nodes);

        jLabel2.setBackground(new java.awt.Color(153, 153, 153));
        jLabel2.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Arcs");
        jPanel2.add(jLabel2);

        jTextField_Arcs.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jTextField_Arcs.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_Arcs.setText("A");
        jTextField_Arcs.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_ArcsFocusGained(evt);
            }
        });
        jPanel2.add(jTextField_Arcs);

        jLabel3.setBackground(new java.awt.Color(153, 153, 153));
        jLabel3.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Technologies");
        jPanel2.add(jLabel3);

        jTextField_Tech.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jTextField_Tech.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_Tech.setText("14");
        jTextField_Tech.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_TechFocusGained(evt);
            }
        });
        jPanel2.add(jTextField_Tech);

        jLabel4.setBackground(new java.awt.Color(153, 153, 153));
        jLabel4.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Lines");
        jPanel2.add(jLabel4);

        jTextField_Lines.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jTextField_Lines.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_Lines.setText("L");
        jTextField_Lines.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_LinesFocusGained(evt);
            }
        });
        jPanel2.add(jTextField_Lines);

        jLabel5.setBackground(new java.awt.Color(153, 153, 153));
        jLabel5.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Years");
        jPanel2.add(jLabel5);

        jTextField_Years.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jTextField_Years.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_Years.setText("1");
        jTextField_Years.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_YearsFocusGained(evt);
            }
        });
        jPanel2.add(jTextField_Years);

        jLabel6.setBackground(new java.awt.Color(153, 153, 153));
        jLabel6.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Fuels");
        jPanel2.add(jLabel6);

        jTextField_Fuels.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jTextField_Fuels.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_Fuels.setText("9");
        jTextField_Fuels.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_FuelsFocusGained(evt);
            }
        });
        jPanel2.add(jTextField_Fuels);

        jLabel7.setBackground(new java.awt.Color(153, 153, 153));
        jLabel7.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Time slice");
        jPanel2.add(jLabel7);

        jTextField_TS.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jTextField_TS.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_TS.setText("8784");
        jTextField_TS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_TSFocusGained(evt);
            }
        });
        jPanel2.add(jTextField_TS);

        jButton1.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jButton1.setText("Continue");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Frame.OpenCountryData(
                Integer.parseInt(jTextField_Nodes.getText()),
                Integer.parseInt(jTextField_Tech.getText()),
                Integer.parseInt(jTextField_TS.getText()),
                Integer.parseInt(jTextField_Years.getText()),
                Integer.parseInt(jTextField_Fuels.getText())).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField_NodesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_NodesFocusGained
        jTextArea1.setText("NODES");
    }//GEN-LAST:event_jTextField_NodesFocusGained

    private void jTextField_ArcsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_ArcsFocusGained
        jTextArea1.setText("ARCS");
    }//GEN-LAST:event_jTextField_ArcsFocusGained

    private void jTextField_TechFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_TechFocusGained
        jTextArea1.setText("TECHNOLOGIES");
    }//GEN-LAST:event_jTextField_TechFocusGained

    private void jTextField_LinesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_LinesFocusGained
        jTextArea1.setText("LINES");
    }//GEN-LAST:event_jTextField_LinesFocusGained

    private void jTextField_FuelsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_FuelsFocusGained
        jTextArea1.setText("FUELS");
    }//GEN-LAST:event_jTextField_FuelsFocusGained

    private void jTextField_YearsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_YearsFocusGained
        jTextArea1.setText("YEARS");
    }//GEN-LAST:event_jTextField_YearsFocusGained

    private void jTextField_TSFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_TSFocusGained
        jTextArea1.setText("TS");
    }//GEN-LAST:event_jTextField_TSFocusGained

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
            java.util.logging.Logger.getLogger(MAIN_GR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MAIN_GR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MAIN_GR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MAIN_GR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MAIN_GR().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField_Arcs;
    private javax.swing.JTextField jTextField_Fuels;
    private javax.swing.JTextField jTextField_Lines;
    private javax.swing.JTextField jTextField_Nodes;
    private javax.swing.JTextField jTextField_TS;
    private javax.swing.JTextField jTextField_Tech;
    private javax.swing.JTextField jTextField_Years;
    // End of variables declaration//GEN-END:variables
}
