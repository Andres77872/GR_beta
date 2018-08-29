/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Andres
 */
public class Visualizer extends javax.swing.JFrame {

    /**
     * Creates new form Visualizador
     */
    private static MAP M;
    private static JCheckBox[] jCheckBox_VAR;
    private JPanel jPanel_EnergySystem, jPanel_EconomicBalance, jPanel_EnviromentalImpact, jPanel_SocialApproach;

    public static int REG = 0, TS, TS_MIN, TS_MAX;

    private final int Tech;

    private String TMP = "";

    public Visualizer(File Map, File ShortName, File FullName, int Nodes, int Years, int Tech, int TS) {
        initComponents();
        this.Tech = Tech;
        Visualizer.TS = TS;
        INI(Map, ShortName, FullName, Nodes, Years, TS);
    }

    private void INI(File Map, File ShortName, File FullName, int Nodes, int Years, int TS) {
        M = new MAP(Map.getAbsolutePath(), ShortName, FullName, Nodes, true);
        jScrollPane1.setViewportView(M);
        setCheckBox();
        addEeventCheckBox();
        for (int x = 0; x < jCheckBox_VAR.length; x++) {
            disableCheckBox(x);
        }

        for (int x = 0; x < OpenDataToDisplay.DATA.size(); x++) {
            for (int y = 0; y < Nodes; y++) {
                if (!OpenDataToDisplay.DATA.get(x).get(y).isEmpty()) {
                    jCheckBox_VAR[x].setEnabled(true);
                }
            }
        }
        setSize(1200, 600);
        jComboBox_Years.removeAllItems();
        for (int x = 0; x < Years; x++) {
            jComboBox_Years.addItem("Year: " + (x + 1));
        }
        jPanel_DATA.setBorder(javax.swing.BorderFactory.createTitledBorder(M.FullName_Array[REG]));
        jSlider_TS_MIN.setPaintTicks(true);
        jSlider_TS_MIN.setMajorTickSpacing(1);
        jSlider_TS_MIN.setMaximum(TS - 1);
        jSlider_TS_MIN.setMinimum(1);
        jSlider_TS_MIN.setValue(1);
        jTextField_MIN.setText("0");
        TS_MIN = 0;

        jSlider_TS_MAX.setPaintTicks(true);
        jSlider_TS_MAX.setMajorTickSpacing(1);
        jSlider_TS_MAX.setMaximum(TS);
        jSlider_TS_MAX.setMinimum(2);
        jSlider_TS_MAX.setValue(TS);
        jTextField_MAX.setText(String.valueOf(TS));
        TS_MAX = TS;
    }

    public static void UpdataTab(int REG) {
        Visualizer.REG = REG;
        jTabbedPane_TableShow.removeAll();
        jPanel_DATA.setBorder(javax.swing.BorderFactory.createTitledBorder(M.FullName_Array[REG]));
        for (JCheckBox V : jCheckBox_VAR) {
            if (V.isSelected()) {
                int VarID = OpenDataToDisplay.GR_NAME.indexOf(V.getText().substring(V.getText().indexOf(" - ") + 3));

                if (!OpenDataToDisplay.DATA.get(VarID).get(REG).isEmpty()) {
                    ArrayList<long[]> ROW = new ArrayList<>();
                    ArrayList<String> COL_LST;
                    int Year = jComboBox_Years.getSelectedIndex();

                    System.out.println(TS);

                    for (int x = 0; x < OpenDataToDisplay.DATA.get(VarID).get(REG).size(); x++) {
                        ROW.add(Arrays.copyOfRange(OpenDataToDisplay.DATA.get(VarID).get(REG).get(x),
                                (Year * TS) + TS_MIN, ((Year + 1) * TS) - (TS - TS_MAX)));
                    }
                    COL_LST = OpenDataToDisplay.DATA_NAME.get(VarID).get(REG);

                    int _TS = TS_MAX, _TECH = ROW.size();

                    Object[] COL = new Object[COL_LST.size() + 1];
                    Object[][] ROW_F = new Object[ROW.get(0).length][];

                    String ZERO = "";

                    while (ZERO.length() != String.valueOf(_TS).length()) {
                        ZERO += "0";
                    }

                    for (int x = 0; x < ROW_F.length; x++) {
                        ROW_F[x] = new Object[_TECH + 1];
                        ROW_F[x][0] = ZERO.substring(String.valueOf(x + TS_MIN + 1).length()) + (x + TS_MIN + 1);
                        for (int y = 1; y <= _TECH; y++) {
                            ROW_F[x][y] = ROW.get(y - 1)[x];
                        }
                    }

                    COL[0] = "TS";
                    for (int x = 1; x < COL.length; x++) {
                        COL[x] = COL_LST.get(x - 1);
                    }

                    jTabbedPane_TableShow.addTab(V.getText(), new TAB(ROW_F, COL, M.FullName_Array[Visualizer.REG]));
                }
            }
        }
    }

    private void setCheckBox() {

        jPanel_EconomicBalance = new JPanel();
        jPanel_EnergySystem = new JPanel();
        jPanel_EnviromentalImpact = new JPanel();
        jPanel_SocialApproach = new JPanel();

        String[] Var = {
            "Demand - Dem",
            "AccumulatedCapacity - Cap",
            "NewCapacity - NewCap",
            "Generation - Gen",
            "InvestmentCost - InvCos",
            "MaintenanceCost - ManCos",
            "OperationCost - OpeCos",
            "PoliciesCost - PolCos",
            "GHGEmissions - GHGEmi",
            "WaterConsumption - WatCon",
            "WaterWithdrawal - WatWit",
            "InvestmentEmployment - InvCapEmp",
            "ManufactureEmployment - ManCapEmp",
            "OperationEmployment - OpeCapEmp",
            "FuelEmployment - FueCapEmp",
            "RetirementEmployment - RetCapEmp",
            "EnergyMarketPrice - EneMarPri",
            "InitialGenerationCapacity - ExiCap",
            "FuelImportationCost - FueCos"};

        jCheckBox_VAR = new JCheckBox[19];

        for (int x = 0; x < jCheckBox_VAR.length; x++) {
            jCheckBox_VAR[x] = new JCheckBox(Var[x]);
        }

        jPanel_EnergySystem.setLayout(new GridLayout(0, 3));
        for (int x = 0; x < 4; x++) {
            jPanel_EnergySystem.add(jCheckBox_VAR[17]);
            jPanel_EnergySystem.add(jCheckBox_VAR[x]);
        }
        jTabbedPane2.addTab("Energy System", jPanel_EnergySystem);

        jPanel_EconomicBalance.setLayout(new GridLayout(0, 3));
        for (int x = 4; x < 8; x++) {
            jPanel_EconomicBalance.add(jCheckBox_VAR[18]);
            jPanel_EconomicBalance.add(jCheckBox_VAR[x]);
        }
        jTabbedPane2.addTab("Economic Balance", jPanel_EconomicBalance);

        jPanel_EnviromentalImpact.setLayout(new GridLayout(0, 3));
        for (int x = 8; x < 11; x++) {
            jPanel_EnviromentalImpact.add(jCheckBox_VAR[x]);
        }
        jTabbedPane2.addTab("Enviromental Impact", jPanel_EnviromentalImpact);

        jPanel_SocialApproach.setLayout(new GridLayout(0, 3));
        for (int x = 11; x < 17; x++) {
            jPanel_SocialApproach.add(jCheckBox_VAR[x]);
        }
        jTabbedPane2.addTab("Social Approach", jPanel_SocialApproach);
    }

    private void addEeventCheckBox() {

        for (JCheckBox V : jCheckBox_VAR) {
            V.addActionListener((ActionEvent e) -> {
                EVT_CHECKBOX(V);
            });
        }

    }

    private static void EVT_CHECKBOX(JCheckBox V) {
        if (V.isSelected()) {
            if (!setChart(V.getText())) {
                V.setSelected(false);
            } else {
                UpdataTab(REG);
            }
        } else {
            removeChart(V.getText());
            jTabbedPane_TableShow.removeTabAt(jTabbedPane_TableShow.indexOfTab(V.getText()));
        }
    }

    private void disableCheckBox(int Var) {
        jCheckBox_VAR[Var].setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel_DATA = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox_ChartType = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox_Years = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jSlider_TS_MIN = new javax.swing.JSlider();
        jTextField_MIN = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jSlider_TS_MAX = new javax.swing.JSlider();
        jTextField_MAX = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jTabbedPane_TableShow = new javax.swing.JTabbedPane();
        jButton2 = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jSplitPane1.setDividerLocation(720);

        jPanel_DATA.setBorder(javax.swing.BorderFactory.createTitledBorder("DT"));

        jLabel1.setText("Chart type");

        jComboBox_ChartType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PIE", "LINE", "BAR", "AREA" }));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("DT"));

        jLabel2.setText("Year");

        jComboBox_Years.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("TS MIN");

        jSlider_TS_MIN.setMinimum(1);
        jSlider_TS_MIN.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider_TS_MINStateChanged(evt);
            }
        });

        jTextField_MIN.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_MIN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_MINKeyReleased(evt);
            }
        });

        jLabel4.setText("TS MAX");

        jSlider_TS_MAX.setMinimum(1);
        jSlider_TS_MAX.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider_TS_MAXStateChanged(evt);
            }
        });

        jTextField_MAX.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_MAX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_MAXKeyReleased(evt);
            }
        });

        jLabel5.setText("Total TS");

        jButton3.setText("Apply");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSlider_TS_MAX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_MAX, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox_Years, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSlider_TS_MIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_MIN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox_Years, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jButton3))
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jSlider_TS_MIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_MIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jSlider_TS_MAX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_MAX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("Preview");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTabbedPane_TableShow.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jButton2.setText("Compare");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_DATALayout = new javax.swing.GroupLayout(jPanel_DATA);
        jPanel_DATA.setLayout(jPanel_DATALayout);
        jPanel_DATALayout.setHorizontalGroup(
            jPanel_DATALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_DATALayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_DATALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane_TableShow)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel_DATALayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox_ChartType, 0, 118, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addGroup(jPanel_DATALayout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel_DATALayout.setVerticalGroup(
            jPanel_DATALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_DATALayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_DATALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox_ChartType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane_TableShow, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel_DATA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel_DATA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel1);
        jSplitPane1.setLeftComponent(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        long[][] DATA = new long[3][12];
        String[] DATA_NAME = {"D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "D11", "D12",};
        String[] PLOT_NAME = {"PLOT 1", "PLOT 2", "PLOT 3",};

        for (int x = 0; x < DATA.length; x++) {
            for (int y = 0; y < DATA[x].length; y++) {
                DATA[x][y] = (long) (Math.random() * 10000);
            }
        }

        new ChartView(DATA, DATA_NAME, PLOT_NAME, "EXAMPLE, collection of technology for TS", jComboBox_ChartType.getSelectedIndex(), ChartView.Format_OneChart).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jTabbedPane_TableShow.getComponentCount() == 2) {

        } else {
            JOptionPane.showMessageDialog(rootPane, "You must select two variables");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jSlider_TS_MINStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider_TS_MINStateChanged
        jTextField_MIN.setText(String.valueOf(jSlider_TS_MIN.getValue()));
        jSlider_TS_MAX.setMinimum(jSlider_TS_MIN.getValue() + 1);
        TS_MIN = jSlider_TS_MIN.getValue() - 1;
        jTextField1.setText(String.valueOf(Integer.parseInt(jTextField_MAX.getText()) - Integer.parseInt(jTextField_MIN.getText()) + 1));
    }//GEN-LAST:event_jSlider_TS_MINStateChanged

    private void jSlider_TS_MAXStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider_TS_MAXStateChanged
        jTextField_MAX.setText(String.valueOf(jSlider_TS_MAX.getValue()));
        jSlider_TS_MIN.setMaximum(jSlider_TS_MAX.getValue() - 1);
        TS_MAX = jSlider_TS_MAX.getValue();
        jTextField1.setText(String.valueOf(Integer.parseInt(jTextField_MAX.getText()) - Integer.parseInt(jTextField_MIN.getText()) + 1));
    }//GEN-LAST:event_jSlider_TS_MAXStateChanged

    private void jTextField_MINKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_MINKeyReleased
        try {
            int Val = Integer.parseInt(jTextField_MIN.getText());
            if (Val < jSlider_TS_MAX.getValue()) {
                jSlider_TS_MIN.setValue(Val);
            } else {
                jTextField_MIN.setText(String.valueOf(jSlider_TS_MIN.getMaximum()));
                jSlider_TS_MIN.setValue(jSlider_TS_MIN.getMaximum());
            }
        } catch (NumberFormatException E) {
            jTextField_MIN.setText(String.valueOf(jSlider_TS_MIN.getMinimum()));
        }
    }//GEN-LAST:event_jTextField_MINKeyReleased

    private void jTextField_MAXKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_MAXKeyReleased
        try {
            int Val = Integer.parseInt(jTextField_MAX.getText());
            if (Val > jSlider_TS_MIN.getValue()) {
                jSlider_TS_MAX.setValue(Val);
            } else {
                jTextField_MAX.setText(String.valueOf(jSlider_TS_MAX.getMinimum()));
                jSlider_TS_MAX.setValue(jSlider_TS_MAX.getMinimum());
            }
        } catch (NumberFormatException E) {
            jTextField_MAX.setText(String.valueOf(jSlider_TS_MAX.getMaximum()));
        }
    }//GEN-LAST:event_jTextField_MAXKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        UpdataTab(REG);
    }//GEN-LAST:event_jButton3ActionPerformed

    private static boolean setChart(String Name) {
        if (!M.setChart(Name)) {
            JOptionPane.showMessageDialog(null, "You can only select two charts");
            return false;
        }
        return true;
    }

    private static void removeChart(String Name) {
        if (!M.removeChart(Name)) {
            JOptionPane.showMessageDialog(null, "ERROR");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    public static javax.swing.JComboBox<String> jComboBox_ChartType;
    private static javax.swing.JComboBox<String> jComboBox_Years;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private static javax.swing.JPanel jPanel_DATA;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSlider_TS_MAX;
    private javax.swing.JSlider jSlider_TS_MIN;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    public static javax.swing.JTabbedPane jTabbedPane_TableShow;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField_MAX;
    private javax.swing.JTextField jTextField_MIN;
    // End of variables declaration//GEN-END:variables
}
