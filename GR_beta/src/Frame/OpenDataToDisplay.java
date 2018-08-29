/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Andres
 */
public class OpenDataToDisplay extends javax.swing.JFrame implements Runnable {

    /**
     * Creates new form OpenDataToDisplay
     */
    private final String[] GRName = {
        "Dem",
        "Cap",
        "NewCap",
        "Gen",
        "InvCos",
        "ManCos",
        "OpeCos",
        "PolCos",
        "GHGEmi",
        "WatCon",
        "WatWit",
        "InvCapEmp",
        "ManCapEmp",
        "OpeCapEmp",
        "FueCapEmp",
        "RetCapEmp",
        "EneMarPri",
        "ExiCap",
        "FueCos"
    };
    private JLabel[][] jLabel_DataDisplay;
    private File FileList[];
    public static ArrayList<String> GR_NAME, TECH_NAME, FUEL_NAME;
    private boolean AllOK = false;
    private boolean[] AllOK_E = new boolean[19];
    private final int Nodes, TS, Years, Tech;

    private final File FileShortName, FileFullName, FileMap;
    public static ArrayList<
            ArrayList<
            ArrayList<long[]>>> DATA;
    public static ArrayList<
            ArrayList<
            ArrayList<String>>> DATA_NAME;

    private int[] FilesLoaded = new int[19];

    public OpenDataToDisplay(File FileTechName, File FileFuelName, File FileMap, File FileFullName, File FileShortName, int Nodes, int TS, int Years, int Tech) {
        initComponents();
        setLocationRelativeTo(null);
        this.Nodes = Nodes;
        this.TS = TS;
        this.Years = Years;
        this.Tech = Tech;
        this.FileFullName = FileFullName;
        this.FileShortName = FileShortName;
        this.FileMap = FileMap;
        INI(FileTechName, FileFuelName);
        setLabels(new int[]{0, 17, 1, 2, 3});
    }

    private void INI(File FileTechName, File FileFuelName) {
        TECH_NAME = new ArrayList<>();
        FUEL_NAME = new ArrayList<>();
        GR_NAME = new ArrayList<>();

        GR_NAME.addAll(Arrays.asList(GRName));

        BufferedReader BR = null;
        try {
            BR = new BufferedReader(new FileReader(FileTechName));
            BR.lines().forEach((LINE) -> {
                TECH_NAME.add(LINE);
            });
            BR.close();
            BR = new BufferedReader(new FileReader(FileFuelName));
            BR.lines().forEach((LINE) -> {
                FUEL_NAME.add(LINE);
            });
            BR.close();
        } catch (FileNotFoundException ex) {
            try {
                BR.close();
            } catch (IOException ex1) {
                Logger.getLogger(OpenDataToDisplay.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(OpenDataToDisplay.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OpenDataToDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }

        jLabel_DataDisplay = new JLabel[19][3];

        for (int x = 0; x < jLabel_DataDisplay.length; x++) {
            jLabel_DataDisplay[x][0] = new JLabel(GRName[x]);
            jLabel_DataDisplay[x][1] = new JLabel(new ImageIcon(getClass().getResource("/ICON/NULL.png")));
            jLabel_DataDisplay[x][2] = new JLabel("null");
            jLabel_DataDisplay[x][2].setHorizontalAlignment(JLabel.CENTER);
        }
    }

    int PareceNoSerParaNadaPeroSinEstoNoFunciona = 1;

    private void setLabels(int[] D) {
        jPanel1.removeAll();
        //Name
        jPanel1.add(new JLabel("==Name=="));
        //Icon
        jPanel1.add(new JLabel("==Is selected=="));
        //Thech size
        jPanel1.add(new JLabel("==Files loaded=="));

        //for (JLabel[] LB : jLabel_DataDisplay) {
        for (int x = 0; x < D.length; x++) {
            //Name
            jPanel1.add(jLabel_DataDisplay[D[x]][0]);
            //Icon
            jPanel1.add(jLabel_DataDisplay[D[x]][1]);
            //Thech size
            jPanel1.add(jLabel_DataDisplay[D[x]][2]);

        }
        this.setSize(this.getSize().width, this.getSize().height + PareceNoSerParaNadaPeroSinEstoNoFunciona);
        PareceNoSerParaNadaPeroSinEstoNoFunciona *= -1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup_ToggleButtons = new javax.swing.ButtonGroup();
        jToolBar1 = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        jButton_SelectFile = new javax.swing.JButton();
        jTextField_FilePath = new javax.swing.JTextField();
        jButton_Acept = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        jToggleButton_EnergySystem = new javax.swing.JToggleButton();
        jToggleButton_EconomicBalance = new javax.swing.JToggleButton();
        jToggleButton_EnvironmentalImpact = new javax.swing.JToggleButton();
        jToggleButtonSocialApproach = new javax.swing.JToggleButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jToolBar1.setRollover(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("DT"));
        jPanel1.setLayout(new java.awt.GridLayout(0, 3));

        jButton_SelectFile.setText("Select File");
        jButton_SelectFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SelectFileActionPerformed(evt);
            }
        });

        jTextField_FilePath.setText("FilePath");

        jButton_Acept.setText("Acept");
        jButton_Acept.setEnabled(false);
        jButton_Acept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_AceptActionPerformed(evt);
            }
        });

        jPanel2.setLayout(new java.awt.GridLayout(2, 2));

        buttonGroup_ToggleButtons.add(jToggleButton_EnergySystem);
        jToggleButton_EnergySystem.setSelected(true);
        jToggleButton_EnergySystem.setText("Energy System");
        jToggleButton_EnergySystem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_EnergySystemActionPerformed(evt);
            }
        });
        jPanel2.add(jToggleButton_EnergySystem);

        buttonGroup_ToggleButtons.add(jToggleButton_EconomicBalance);
        jToggleButton_EconomicBalance.setText("Economic Balance");
        jToggleButton_EconomicBalance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_EconomicBalanceActionPerformed(evt);
            }
        });
        jPanel2.add(jToggleButton_EconomicBalance);

        buttonGroup_ToggleButtons.add(jToggleButton_EnvironmentalImpact);
        jToggleButton_EnvironmentalImpact.setText("Environmental Impact");
        jToggleButton_EnvironmentalImpact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_EnvironmentalImpactActionPerformed(evt);
            }
        });
        jPanel2.add(jToggleButton_EnvironmentalImpact);

        buttonGroup_ToggleButtons.add(jToggleButtonSocialApproach);
        jToggleButtonSocialApproach.setText("Social Approach");
        jToggleButtonSocialApproach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonSocialApproachActionPerformed(evt);
            }
        });
        jPanel2.add(jToggleButtonSocialApproach);

        jTextPane1.setEditable(false);
        jTextPane1.setContentType(""); // NOI18N
        jScrollPane2.setViewportView(jTextPane1);

        jLabel2.setText("Upload data to display");

        jButton1.setText("Help");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addComponent(jButton_Acept, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                        .addGap(190, 190, 190))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton_SelectFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_FilePath)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_Acept))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_SelectFile)
                            .addComponent(jTextField_FilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_SelectFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SelectFileActionPerformed
        JFileChooser JFC = new JFileChooser();
        JFC.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        JFC.setMultiSelectionEnabled(true);

        if (JFC.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File[] FILE = JFC.getSelectedFiles();

            if (FILE.length == 1) {
                if (FILE[0].isDirectory()) {
                    FileList = FILE[0].listFiles();
                    jTextField_FilePath.setText(JFC.getSelectedFile().getAbsolutePath());
                    new Thread(this, "DIR").start();
                } else {
                    FileList = FILE;
                    new Thread(this, "FILE").start();
                }
            } else {
                FileList = FILE;
                new Thread(this, "FILES").start();
            }
        }
    }//GEN-LAST:event_jButton_SelectFileActionPerformed

    private void jToggleButton_EnergySystemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_EnergySystemActionPerformed
        setLabels(new int[]{0, 17, 1, 2, 3});
    }//GEN-LAST:event_jToggleButton_EnergySystemActionPerformed

    private void jToggleButton_EconomicBalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_EconomicBalanceActionPerformed
        setLabels(new int[]{4, 5, 6, 18, 7});
    }//GEN-LAST:event_jToggleButton_EconomicBalanceActionPerformed

    private void jToggleButton_EnvironmentalImpactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_EnvironmentalImpactActionPerformed
        setLabels(new int[]{8, 9, 10});
    }//GEN-LAST:event_jToggleButton_EnvironmentalImpactActionPerformed

    private void jToggleButtonSocialApproachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonSocialApproachActionPerformed
        setLabels(new int[]{11, 12, 13, 14, 15, 16});
    }//GEN-LAST:event_jToggleButtonSocialApproachActionPerformed

    private void jButton_AceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AceptActionPerformed
        if (AllOK) {
            new Visualizer(FileMap, FileShortName, FileFullName, Nodes, Years, Tech, TS).setVisible(true);
        }
    }//GEN-LAST:event_jButton_AceptActionPerformed

    int Progress = 0, TechCont = 0;
    String TMP = "NULL";

    public void run() {
        System.out.println(Years + " : " + Tech + " : " + Nodes + " : " + TS);
        DATA = new ArrayList<>();
        DATA_NAME = new ArrayList<>();
        for (int x = 0; x < 19; x++) {
            ArrayList<ArrayList<long[]>> TST = new ArrayList<>();
            ArrayList<ArrayList<String>> TST_NAME = new ArrayList<>();
            for (int y = 0; y < Nodes; y++) {
                TST.add(new ArrayList<>());
                TST_NAME.add(new ArrayList<>());
            }
            DATA.add(TST);
            DATA_NAME.add(TST_NAME);
        }
        jProgressBar1.setMaximum(FileList.length);
        Progress = 0;
        TechCont = 0;
        TMP = "NULL";
        String Name;
        AllOK = true;
        AllOK_E = new boolean[19];
        FilesLoaded = new int[19];
        jTextPane1.setText("");
        for (File Var : FileList) {
            jProgressBar1.setValue(Progress);
            Progress++;
            Name = Var.getName();

            if (Name.matches("([A-z]*)[\\.]([A-z]*)\\.txt")) {
                OpenTXT(Name, Var);
            } else if (Name.matches("([A-z]*)[\\.]([A-z]*)\\.xlsx")) {
                OpenEXCEL1(Name, Var);
            } else if (Name.equals("Function.xlsx")) {
                OpenEXCEL3(Var);
            } else if (Name.endsWith(".xlsx")) {
                OpenEXCEL2(Name, Var);
            }
        }
        jProgressBar1.setValue(0);
        if (AllOK) {
            jButton_Acept.setEnabled(true);
        }

    }

    private void OpenTXT(String Name, File Var) {

        switch (Name) {
            case "ExiCap.txt":
                
                break;
            case "FueCos.txt":

                break;
            default:
                OpenDefaultTXT(Name, Var);
                break;
        }

    }

    private void OpenDefaultTXT(String Name, File Var) {
        String NS = Name.substring(Name.indexOf(".") + 1);
        String V = Name.substring(0, Name.indexOf("."));
        String T = NS.substring(0, NS.indexOf("."));

        try {
            if (GR_NAME.contains(V) && TECH_NAME.contains(T)) {
                BufferedReader BR = new BufferedReader(new FileReader(Var));
                Object[] LINES = BR.lines().toArray();

                if (LINES.length == Nodes) {
                    boolean OK = true;
                    for (int ID_reg = 0; ID_reg < LINES.length; ID_reg++) {
                        String[] DATA_LINE = LINES[ID_reg].toString().split("\t");
                        if (DATA_LINE.length == (TS * Years)) {
                            long[] re = new long[TS * Years];
                            for (int x = 0; x < re.length; x++) {
                                if (DATA_LINE[x].contains(".")) {
                                    re[x] = Long.parseLong(DATA_LINE[x].substring(0, DATA_LINE[x].indexOf(".")));
                                } else {
                                    re[x] = Long.parseLong(DATA_LINE[x]);
                                }
                            }
                            DATA.get(GR_NAME.indexOf(V)).get(ID_reg).add(re);
                            DATA_NAME.get(GR_NAME.indexOf(V)).get(ID_reg).add(T);
                        } else {
                            OK = false;
                            CHECK_update(GR_NAME.indexOf(V), true);
                            jTextPane1.setText(jTextPane1.getText() + "ERROR:\t" + Var.getName()
                                    + "\tThe document must have " + (TS * Years) + " columns\n");
                            jTextPane1.setCaretPosition(jTextPane1.getText().length());
                            break;
                        }
                    }
                    if (OK) {
                        int VarIDX = GR_NAME.indexOf(V);
                        FilesLoaded[VarIDX]++;
                        CHECK_update(VarIDX, false);
                        jTextPane1.setText(jTextPane1.getText() + Name + "\t\twas loaded\n");
                        jTextPane1.setCaretPosition(jTextPane1.getText().length());
                    }
                } else {
                    CHECK_update(GR_NAME.indexOf(V), true);
                    jTextPane1.setText(jTextPane1.getText() + "ERROR:\t" + Var.getName()
                            + "\tThe document must have " + Nodes + " rows\n");
                    jTextPane1.setCaretPosition(jTextPane1.getText().length());
                }

            }
        } catch (IOException E) {
            CHECK_update(GR_NAME.indexOf(V), true);
            jTextPane1.setText(jTextPane1.getText() + "ERROR:\t" + Var.getName() + "\tIOException\n");
        } catch (NumberFormatException E) {
            CHECK_update(GR_NAME.indexOf(V), true);
            jTextPane1.setText(jTextPane1.getText() + "ERROR:\t" + Var.getName() + "\tNumberFormatException\n");
        } catch (ArrayIndexOutOfBoundsException E) {
            CHECK_update(GR_NAME.indexOf(V), true);
            jTextPane1.setText(jTextPane1.getText() + "ERROR:\t" + "The file " + Var.getName() + " don't have a correct matriz. " + "\tArrayIndexOutOfBoundsException\n");
        }
    }

    private void OpenEXCEL1(String Name, File Var) {
        String NS = Name.substring(Name.indexOf(".") + 1);
        String V = Name.substring(0, Name.indexOf("."));
        String T = NS.substring(0, NS.indexOf("."));

        if (GR_NAME.contains(V) && TECH_NAME.contains(T)) {
            try (FileInputStream file = new FileInputStream(Var)) {

                Iterator<Row> rowIterator = new XSSFWorkbook(file).getSheetAt(0).iterator();

                Row row;
                int ID_reg = 0;
                while (rowIterator.hasNext()) {
                    row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();

                    Cell cell;
                    int y = 0;
                    long[] re = new long[TS * Years];
                    while (cellIterator.hasNext()) {
                        cell = cellIterator.next();
                        String C = String.valueOf(cell.getNumericCellValue());
                        if (C.contains(".")) {
                            re[y] = Long.parseLong(C.substring(0, C.indexOf(".")));
                        } else {
                            re[y] = Long.parseLong(C);
                        }
                        y++;
                    }
                    DATA.get(GR_NAME.indexOf(V)).get(ID_reg).add(re);
                    DATA_NAME.get(GR_NAME.indexOf(V)).get(ID_reg).add(T);
                    ID_reg++;
                }

                file.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void OpenEXCEL2(String Name, File Var) {
        String V = Name.substring(0, Name.indexOf("."));
        String T;

        if (GR_NAME.contains(V)) {
            try (FileInputStream file = new FileInputStream(Var)) {
                XSSFWorkbook XW = new XSSFWorkbook(file);
                for (int x = 0; x < XW.getNumberOfSheets(); x++) {
                    if (TECH_NAME.contains(XW.getSheetName(x))) {
                        T = XW.getSheetName(x);
                        Iterator<Row> rowIterator = XW.getSheetAt(x).iterator();

                        Row row;
                        int ID_reg = 0;
                        while (rowIterator.hasNext()) {
                            row = rowIterator.next();
                            Iterator<Cell> cellIterator = row.cellIterator();

                            Cell cell;
                            int y = 0;
                            long[] re = new long[TS * Years];
                            while (cellIterator.hasNext()) {
                                cell = cellIterator.next();
                                String C = String.valueOf(cell.getNumericCellValue());
                                if (C.contains(".")) {
                                    re[y] = Long.parseLong(C.substring(0, C.indexOf(".")));
                                } else {
                                    re[y] = Long.parseLong(C);
                                }
                                y++;
                            }
                            DATA.get(GR_NAME.indexOf(V)).get(ID_reg).add(re);
                            DATA_NAME.get(GR_NAME.indexOf(V)).get(ID_reg).add(T);
                            ID_reg++;
                        }
                    }
                }

                file.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void OpenEXCEL3(File Var) {

        try (FileInputStream file = new FileInputStream(Var)) {
            XSSFWorkbook XW = new XSSFWorkbook(file);
            for (int x = 0; x < XW.getNumberOfSheets(); x++) {
                String Name = XW.getSheetName(x);
                String NS = Name.substring(Name.indexOf(".") + 1);
                String V = Name.substring(0, Name.indexOf("."));
                String T = NS.substring(0, NS.length());
                if (TECH_NAME.contains(T) && GR_NAME.contains(V)) {
                    Iterator<Row> rowIterator = XW.getSheetAt(x).iterator();

                    Row row;
                    int ID_reg = 0;
                    while (rowIterator.hasNext()) {
                        row = rowIterator.next();
                        Iterator<Cell> cellIterator = row.cellIterator();

                        Cell cell;
                        int y = 0;
                        long[] re = new long[TS * Years];
                        while (cellIterator.hasNext()) {
                            cell = cellIterator.next();
                            String C = String.valueOf(cell.getNumericCellValue());
                            if (C.contains(".")) {
                                re[y] = Long.parseLong(C.substring(0, C.indexOf(".")));
                            } else {
                                re[y] = Long.parseLong(C);
                            }
                            y++;
                        }
                        System.out.println(re[0]);
                        DATA.get(GR_NAME.indexOf(V)).get(ID_reg).add(re);
                        DATA_NAME.get(GR_NAME.indexOf(V)).get(ID_reg).add(T);
                        ID_reg++;
                    }
                }
            }

            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void CHECK_update(int Var, boolean ERROR) {
        if (FilesLoaded[Var] != 0) {
            AllOK = true;
            if (ERROR) {
                AllOK_E[Var] = true;
            }
            jLabel_DataDisplay[Var][1].setIcon(
                    new ImageIcon(getClass().getResource(
                            AllOK_E[Var] ? "/ICON/CHECK_E.png" : "/ICON/CHECK.png")));
        } else {
            if (!ERROR) {
                AllOK = true;
            }
            jLabel_DataDisplay[Var][1].setIcon(
                    new ImageIcon(getClass().getResource(
                            ERROR ? "/ICON/UNCHECK.png" : "/ICON/CHECK.png")));
        }

        jLabel_DataDisplay[Var][2].setText(String.valueOf(FilesLoaded[Var]));
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup_ToggleButtons;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_Acept;
    private javax.swing.JButton jButton_SelectFile;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField_FilePath;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JToggleButton jToggleButtonSocialApproach;
    private javax.swing.JToggleButton jToggleButton_EconomicBalance;
    private javax.swing.JToggleButton jToggleButton_EnergySystem;
    private javax.swing.JToggleButton jToggleButton_EnvironmentalImpact;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
