/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

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

    public Visualizer(File Map, File ShortName, File FullName, int Nodes, int Years, int Tech, int TS) {
        initComponents();
        Visualizer.TS = TS;
        INI(Map, ShortName, FullName, Nodes, Years, TS);
    }

    private void INI(File Map, File ShortName, File FullName, int Nodes, int Years, int TS) {
        M = new MAP(Map.getAbsolutePath(), ShortName, FullName, Nodes, true);
        jScrollPane1_MAP.setViewportView(M);
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
        jPanel_DATA.setBorder(BorderFactory.createTitledBorder(M.FullName_Array[REG]));
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

        jPanel_EnergySystem.add(jCheckBox_VAR[17]);
        jPanel_EnergySystem.setLayout(new GridLayout(0, 2));
        for (int x = 0; x < 4; x++) {
            jPanel_EnergySystem.add(jCheckBox_VAR[x]);
        }
        jTabbedPane_Var.addTab("Energy System", jPanel_EnergySystem);

        jPanel_EconomicBalance.add(jCheckBox_VAR[18]);
        jPanel_EconomicBalance.setLayout(new GridLayout(0, 2));
        for (int x = 4; x < 8; x++) {
            jPanel_EconomicBalance.add(jCheckBox_VAR[x]);
        }
        jTabbedPane_Var.addTab("Economic Balance", jPanel_EconomicBalance);

        jPanel_EnviromentalImpact.setLayout(new GridLayout(0, 2));
        for (int x = 8; x < 11; x++) {
            jPanel_EnviromentalImpact.add(jCheckBox_VAR[x]);
        }
        jTabbedPane_Var.addTab("Enviromental Impact", jPanel_EnviromentalImpact);

        jPanel_SocialApproach.setLayout(new GridLayout(0, 2));
        for (int x = 11; x < 17; x++) {
            jPanel_SocialApproach.add(jCheckBox_VAR[x]);
        }
        jTabbedPane_Var.addTab("Social Approach", jPanel_SocialApproach);
        jTabbedPane_Var.setMaximumSize(new Dimension(Integer.MAX_VALUE, jTabbedPane_Var.getPreferredSize().height));
    }

    private void addEeventCheckBox() {

        for (JCheckBox V : jCheckBox_VAR) {
            V.addActionListener((ActionEvent e) -> {
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
            });
        }

    }

    private void disableCheckBox(int Var) {
        jCheckBox_VAR[Var].setEnabled(false);
    }

    private void initComponents() {

        jSplitPane_ALL = new JSplitPane();
        jPanel_JS_R = new JPanel();
        jPanel_DATA = new JPanel();
        jLabel_ChartType = new JLabel("Chart type");
        jComboBox_ChartType = new JComboBox<>(new DefaultComboBoxModel<>(new String[]{"PIE", "LINE", "BAR", "AREA"}));
        jPanel_TSControl = new JPanel();
        jLabel_Year = new JLabel("Year");
        jComboBox_Years = new JComboBox<>();
        jLabel_TS_MIN = new JLabel("TS MIN");
        jSlider_TS_MIN = new JSlider();
        jTextField_MIN = new JTextField();
        jLabel_TS_MAX = new JLabel("TS MAX");
        jSlider_TS_MAX = new JSlider();
        jTextField_MAX = new JTextField();
        jTextField_TSTOTAL = new JTextField();
        jLabel_TotalTS = new JLabel("Total TS");
        jButton_Apply = new JButton("Apply");
        jButton_Preview = new JButton("Preview");
        jTabbedPane_TableShow = new JTabbedPane();
        jButton_Compare = new JButton("Compare");
        jTabbedPane_Var = new JTabbedPane();
        jScrollPane1_MAP = new JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jSplitPane_ALL.setDividerLocation(720);

        jPanel_TSControl.setBorder(javax.swing.BorderFactory.createTitledBorder("TS control"));

        jSlider_TS_MIN.setMinimum(1);
        jSlider_TS_MIN.addChangeListener((evt) -> {
            jSlider_TS_MINStateChanged();
        });

        jTextField_MIN.setHorizontalAlignment(JTextField.CENTER);
        jTextField_MIN.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                jTextField_MINKeyReleased();
            }
        });

        jSlider_TS_MAX.setMinimum(1);
        jSlider_TS_MAX.addChangeListener((evt) -> {
            jSlider_TS_MAXStateChanged();
        });

        jTextField_MAX.setHorizontalAlignment(JTextField.CENTER);
        jTextField_MAX.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_MAXKeyReleased();
            }
        });

        jButton_Apply.addActionListener((evt) -> {
            UpdataTab(REG);
        });

        jButton_Preview.addActionListener((evt) -> {
            jButton_EXAMPLEActionPerformed();
        });

        jTabbedPane_TableShow.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jButton_Compare.addActionListener((evt) -> {
            jButton_CompareActionPerformed();
        });

        jSplitPane_ALL.setRightComponent(jPanel_JS_R);
        jSplitPane_ALL.setLeftComponent(jScrollPane1_MAP);

        jPanel_TSControl.setLayout(new BoxLayout(jPanel_TSControl, BoxLayout.PAGE_AXIS));

        JPanel JP_Y = new JPanel();
        JP_Y.setLayout(new BoxLayout(JP_Y, BoxLayout.LINE_AXIS));
        JP_Y.add(jLabel_Year);
        JP_Y.add(jComboBox_Years);
        JP_Y.add(jButton_Apply);
        JP_Y.add(jLabel_TotalTS);
        JP_Y.add(jTextField_TSTOTAL);
        jPanel_TSControl.add(JP_Y);
        JPanel JP_TS_MIN = new JPanel();
        JP_TS_MIN.setLayout(new BoxLayout(JP_TS_MIN, BoxLayout.LINE_AXIS));
        JP_TS_MIN.add(jLabel_TS_MIN);
        JP_TS_MIN.add(jSlider_TS_MIN);
        JP_TS_MIN.add(jTextField_MIN);
        jPanel_TSControl.add(JP_TS_MIN);
        JPanel JP_TS_MAX = new JPanel();
        JP_TS_MAX.setLayout(new BoxLayout(JP_TS_MAX, BoxLayout.LINE_AXIS));
        JP_TS_MAX.add(jLabel_TS_MAX);
        JP_TS_MAX.add(jSlider_TS_MAX);
        JP_TS_MAX.add(jTextField_MAX);
        jPanel_TSControl.add(JP_TS_MAX);
        jPanel_TSControl.setMaximumSize(new Dimension(Integer.MAX_VALUE, jPanel_TSControl.getPreferredSize().height));
        JPanel JP_Chart = new JPanel();
        JP_Chart.setLayout(new BoxLayout(JP_Chart, BoxLayout.LINE_AXIS));
        JP_Chart.add(jLabel_ChartType);
        JP_Chart.add(jComboBox_ChartType);
        JP_Chart.add(jButton_Preview);
        JP_Chart.setMaximumSize(new Dimension(Integer.MAX_VALUE, JP_Chart.getPreferredSize().height));
        jPanel_DATA.setLayout(new BoxLayout(jPanel_DATA, BoxLayout.PAGE_AXIS));
        jPanel_DATA.add(jPanel_TSControl);
        jPanel_DATA.add(JP_Chart);
        jPanel_DATA.add(jTabbedPane_TableShow);
        jPanel_DATA.add(jButton_Compare);
        jPanel_JS_R.setLayout(new BoxLayout(jPanel_JS_R, BoxLayout.PAGE_AXIS));
        jPanel_JS_R.add(jTabbedPane_Var);
        jPanel_JS_R.add(jPanel_DATA);

        add(jSplitPane_ALL);

        pack();
    }

    private void jButton_EXAMPLEActionPerformed() {
        long[][] DATA = new long[3][12];
        String[] DATA_NAME = {"D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "D11", "D12",};
        String[] PLOT_NAME = {"PLOT 1", "PLOT 2", "PLOT 3",};

        for (long[] D : DATA) {
            for (int y = 0; y < D.length; y++) {
                D[y] = (long) (Math.random() * 10000);
            }
        }

        new ChartView(DATA, DATA_NAME, PLOT_NAME, "EXAMPLE, collection of technology for TS", jComboBox_ChartType.getSelectedIndex(), ChartView.Format_OneChart).setVisible(true);
    }

    private void jButton_CompareActionPerformed() {
        if (jTabbedPane_TableShow.getComponentCount() == 2) {
            JOptionPane.showMessageDialog(rootPane, "In construction");
        } else {
            JOptionPane.showMessageDialog(rootPane, "You must select two variables");
        }
    }

    private void jSlider_TS_MINStateChanged() {
        jTextField_MIN.setText(String.valueOf(jSlider_TS_MIN.getValue()));
        jSlider_TS_MAX.setMinimum(jSlider_TS_MIN.getValue() + 1);
        TS_MIN = jSlider_TS_MIN.getValue() - 1;
        jTextField_TSTOTAL.setText(String.valueOf(Integer.parseInt(jTextField_MAX.getText()) - Integer.parseInt(jTextField_MIN.getText()) + 1));
    }

    private void jSlider_TS_MAXStateChanged() {
        jTextField_MAX.setText(String.valueOf(jSlider_TS_MAX.getValue()));
        jSlider_TS_MIN.setMaximum(jSlider_TS_MAX.getValue() - 1);
        TS_MAX = jSlider_TS_MAX.getValue();
        jTextField_TSTOTAL.setText(String.valueOf(Integer.parseInt(jTextField_MAX.getText()) - Integer.parseInt(jTextField_MIN.getText()) + 1));
    }

    private void jTextField_MINKeyReleased() {
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
    }

    private void jTextField_MAXKeyReleased() {
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
    }

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

    private JButton jButton_Preview;
    private JButton jButton_Compare;
    private JButton jButton_Apply;
    public static JComboBox<String> jComboBox_ChartType;
    private static JComboBox<String> jComboBox_Years;
    private JLabel jLabel_ChartType;
    private JLabel jLabel_Year;
    private JLabel jLabel_TS_MIN;
    private JLabel jLabel_TS_MAX;
    private JLabel jLabel_TotalTS;
    private JPanel jPanel_JS_R;
    private JPanel jPanel_TSControl;
    private static JPanel jPanel_DATA;
    private JScrollPane jScrollPane1_MAP;
    private JSlider jSlider_TS_MAX;
    private JSlider jSlider_TS_MIN;
    private JSplitPane jSplitPane_ALL;
    private JTabbedPane jTabbedPane_Var;
    public static JTabbedPane jTabbedPane_TableShow;
    private JTextField jTextField_TSTOTAL;
    private JTextField jTextField_MAX;
    private JTextField jTextField_MIN;
}
