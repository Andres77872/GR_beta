/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andres
 */
public class TAB extends JPanel {

    private boolean TabMode = true;
    private JTable JT;

    private final Object[][] ROW;
    private final Object[] COL;

    private DefaultTableModel TBMoldel;

    public TAB(Object[][] ROW, Object[] COL, String REG_NAME) {
        this.ROW = ROW;
        this.COL = COL;
        INI(REG_NAME);
    }

    private void INI(String REG_NAME) {
        JT = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        TBMoldel = (DefaultTableModel) JT.getModel();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JButton JB = new JButton("Change the type of matrix");
        JB.addActionListener((e) -> {
            TabMode = !TabMode;
            changeMode();
        });

        JButton JB2 = new JButton("Show selection");
        JB2.addActionListener((e) -> {
            EVT_Button_ShowChart_Selection(REG_NAME, ChartView.Format_OneChart);
        });

        JButton JB3 = new JButton("Show everything");
        JB3.addActionListener((e) -> {
            EVT_Button_ShowChart_Everything(REG_NAME);
        });

        JButton JB4 = new JButton("Compare selection");
        JB4.addActionListener((e) -> {
            EVT_Button_ShowChart_Selection(REG_NAME, ChartView.Format_MultipleChart);
        });

        JPanel JP = new JPanel(new GridLayout(0, 2));

        JP.add(JB, BorderLayout.EAST);
        JP.add(JB3, BorderLayout.EAST);
        JP.add(JB2);
        JP.add(JB4);

        add(JP);

        JT.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JT.setAutoCreateRowSorter(true);
        JT.setColumnSelectionAllowed(true);
        JT.setRowSelectionAllowed(true);
        JT.setDragEnabled(false);
        JT.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    EVT_JTable_ShowChart(REG_NAME);
                }
            }

        });
        add(new JScrollPane(JT));

        changeMode();
    }

    private void EVT_JTable_ShowChart(String REG_NAME) {
        int SR = JT.getSelectedRow();

        long[] DATA = new long[JT.getColumnCount() - 1];
        String[] DATA_NAME = new String[JT.getColumnCount() - 1];

        for (int x = 0; x < DATA.length; x++) {
            DATA[x] = Long.parseLong(JT.getValueAt(SR, x + 1).toString());
            DATA_NAME[x] = JT.getColumnName(x + 1);
        }
        new ChartView(DATA, DATA_NAME, "Reg: " + REG_NAME + (TabMode ? ", collection of technology for TS" : ", collection of TS for technology"),
                Visualizer.jComboBox_ChartType.getSelectedIndex(), ChartView.Format_OneChart).setVisible(true);
    }

    private void EVT_Button_ShowChart_Everything(String REG_NAME) {

        long[][] DATA = new long[JT.getRowCount()][JT.getColumnCount() - 1];
        String[] DATA_NAME = new String[JT.getColumnCount() - 1];
        String[] PLOT_NAME = new String[JT.getRowCount()];

        for (int x = 0; x < DATA_NAME.length; x++) {
            DATA_NAME[x] = JT.getColumnName(x + 1);
        }

        for (int x = 0; x < JT.getRowCount(); x++) {
            PLOT_NAME[x] = JT.getValueAt(x, 0).toString();
            for (int y = 0; y < DATA[x].length; y++) {
                DATA[x][y] = Long.parseLong(JT.getValueAt(x, y + 1).toString());
            }
        }

        new ChartView(DATA, DATA_NAME, PLOT_NAME, "Reg: " + REG_NAME + (TabMode ? ", collection of TS for technology" : ", collection of technology for TS"),
                Visualizer.jComboBox_ChartType.getSelectedIndex(), ChartView.Format_OneChart).setVisible(true);
    }

    private void EVT_Button_ShowChart_Selection(String REG_NAME, int Format) {
        int[] IDX_ROW = JT.getSelectedRows();
        int[] IDX_COL = JT.getSelectedColumns();

        if (IDX_COL.length == JT.getColumnCount() || (IDX_COL.length == 1 && IDX_COL[0] == 0)) {
            IDX_COL = new int[JT.getColumnCount() - 1];
            for (int x = 0; x < IDX_COL.length; x++) {
                IDX_COL[x] = x + 1;
            }
        } else {
            boolean ok = true;
            for (int x : IDX_COL) {
                if (x == 0) {
                    ok = false;
                }
            }
            if (!ok) {
                IDX_COL = new int[JT.getSelectedColumns().length - 1];
                int[] TMP = JT.getSelectedColumns();
                ArrayList<Integer> TMP2 = new ArrayList<>();
                for (int x = 0; x < TMP.length; x++) {
                    if (TMP[x] != 0) {
                        TMP2.add(TMP[x]);
                    }
                }
                for (int x = 0; x < IDX_COL.length; x++) {
                    IDX_COL[x] = TMP2.get(x);
                }
            }
        }

        for (int G : IDX_ROW) {
            System.out.println(G);
        }
        if (Visualizer.jComboBox_ChartType.getSelectedIndex() == 0) {

            long[][] DATA = new long[IDX_ROW.length][IDX_COL.length];
            String[] DATA_NAME = new String[IDX_COL.length];
            String[] PLOT_NAME = new String[IDX_ROW.length];

            for (int x = 0; x < DATA_NAME.length; x++) {
                DATA_NAME[x] = JT.getColumnName(IDX_COL[x]);
            }

            for (int x = 0; x < IDX_ROW.length; x++) {
                PLOT_NAME[x] = JT.getValueAt(IDX_ROW[x], 0).toString();
                for (int y = 0; y < DATA[x].length; y++) {
                    DATA[x][y] = Long.parseLong(JT.getValueAt(IDX_ROW[x], IDX_COL[y]).toString());
                }
            }

            new ChartView(DATA, DATA_NAME, PLOT_NAME, "Reg: " + REG_NAME + (TabMode ? ", collection of TS for technology" : ", collection of technology for TS"),
                    Visualizer.jComboBox_ChartType.getSelectedIndex(), Format).setVisible(true);
        } else {
            long[][][] DATA = new long[IDX_ROW.length][1][IDX_COL.length];
            String[][] DATA_NAME = new String[IDX_ROW.length][IDX_COL.length];
            String[][] PLOT_NAME = new String[IDX_ROW.length][1];

            for (int x = 0; x < DATA_NAME.length; x++) {
                for (int y = 0; y < DATA_NAME[x].length; y++) {
                    DATA_NAME[x][y] = JT.getColumnName(IDX_COL[y]);
                }
            }
            for (int x = 0; x < PLOT_NAME.length; x++) {
                for (int y = 0; y < PLOT_NAME[x].length; y++) {
                    PLOT_NAME[x][y] = JT.getValueAt(IDX_ROW[x], 0).toString();
                }

            }
            for (int x = 0; x < DATA.length; x++) {
                for (int y = 0; y < DATA[x].length; y++) {
                    for (int z = 0; z < DATA[x][y].length; z++) {
                        DATA[x][y][z] = Long.parseLong(JT.getValueAt(IDX_ROW[x], IDX_COL[z]).toString());
                    }
                }
            }

            new ChartView(DATA, DATA_NAME, PLOT_NAME, "Reg: " + REG_NAME + (TabMode ? ", collection of TS for technology" : ", collection of technology for TS"),
                    Visualizer.jComboBox_ChartType.getSelectedIndex(), Format).setVisible(true);
        }
    }

    public void changeMode() {
        if (TabMode) {

            Object[][] ROW_F = new Object[ROW.length][];

            String ZERO = "";
            while (ZERO.length() != String.valueOf(ROW_F.length * Visualizer.jComboBox_Years.getItemCount()).length()) {
                ZERO += "0";
            }

            for (int x = 0; x < ROW_F.length; x++) {
                ROW_F[x] = new Object[ROW[0].length + 1];

                ROW_F[x][0] = "TS: " + ZERO.substring(String.valueOf(
                        x + 1 + Visualizer.jComboBox_Years.getSelectedIndex() * ROW.length).length())
                        + (x + 1 + Visualizer.jComboBox_Years.getSelectedIndex() * ROW.length);
                for (int y = 1; y < ROW_F[x].length; y++) {
                    ROW_F[x][y] = ROW[x][y - 1];
                }
            }
            TBMoldel.setDataVector(ROW_F, COL);
        } else {

            Object[][] ROW_INV = new Object[ROW[0].length][];
            for (int x = 0; x < ROW_INV.length; x++) {
                ROW_INV[x] = new Object[ROW.length + 1];
                ROW_INV[x][0] = COL[x + 1];
                for (int y = 1; y < ROW_INV[x].length; y++) {
                    ROW_INV[x][y] = ROW[y - 1][x];
                }
            }

            Object[] COL_INV = new Object[ROW.length + 1];
            COL_INV[0] = "TECH";
            for (int x = 1; x < COL_INV.length; x++) {
                COL_INV[x] = x;
            }

            TBMoldel.setDataVector(ROW_INV, COL_INV);
            //JT.setModel(new DefaultTableModel(ROW_INV, COL_INV));
        }
    }

}
