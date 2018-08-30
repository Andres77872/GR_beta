/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Andres
 */
public class OpenCountryData extends javax.swing.JFrame {

    /**
     * Creates new form Abrir
     */
    private File FILE, FileMap, FileShortName, FileFullName, FileTechName, FileFuelName;
    private boolean AllOK = false;
    private final int Nodes, Tech, TS, Years, Fuel;

    private final String[] GR_FULL_NAME = {
        "Demand",
        "Accumulated Capacity",
        "New Capacity",
        "Generation",
        "Investment Cost",
        "Maintenance Cost",
        "Operation Cost",
        "Policies Cost",
        "GHG Emissions",
        "Water Consumption",
        "Water Withdrawal",
        "Investment Employment",
        "Manufacture Employment",
        "Operation Employment",
        "Fuel Employment",
        "Retirement Employment",
        "Energy Market Price",
        "Initial Generation Capacity",
        "Fuel Importation Cost"};

    public static final ArrayList<String> GR_NAME = new ArrayList<>(Arrays.asList(new String[]{
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
        "FueCos"}));

    private final String[] LB = {"MAP", "Short Name", "Full Name", "Tech Name", "Fuel Name"};

    public OpenCountryData(int Nodes, int Tech, int TS, int Years, int Fuel) {
        initComponents();
        this.Nodes = Nodes;
        this.Tech = Tech;
        this.TS = TS;
        this.Years = Years;
        this.Fuel = Fuel;
        INI();
    }

    private void INI() {

        int ALL_TS = this.TS * this.Years;
        jTextArea1.setText("Nodes size:\t" + Nodes + "\n");

        jLabels_Model[17][2].setText(Nodes + " x " + Tech);
        jLabels_Model[18][2].setText(String.valueOf(ALL_TS));

        for (int x = 0; x < jLabels_Model.length - 2; x++) {
            jLabels_Model[x][2].setText(Nodes + " x " + ALL_TS);
        }
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        jTextArea1 = new JTextArea();
        jTextField_FilePath_MAP = new JTextField("File Path");

        jPanel_NecessaryFiles_TMP = new JPanel(new GridLayout(0, 4));
        jPanel_NecessaryFiles_TMP.setBorder(BorderFactory.createTitledBorder("Necessary files"));
        jPanel_DataModel = new JPanel(new java.awt.GridLayout(0, 3, 8, 0));
        jPanel_DataModel.setBorder(javax.swing.BorderFactory.createTitledBorder("Data model"));

        jLabels_Model = new JLabel[19][3];
        int x = 0;
        for (JLabel[] JL : jLabels_Model) {
            JL[0] = new JLabel(GR_NAME.get(x), SwingConstants.CENTER);
            JL[1] = new JLabel(GR_FULL_NAME[x], SwingConstants.CENTER);
            JL[2] = new JLabel("", SwingConstants.CENTER);
            jPanel_DataModel.add(JL[0]);
            jPanel_DataModel.add(JL[1]);
            jPanel_DataModel.add(JL[2]);
            x++;
        }

        jLabels_NessesaryFiles = new JLabel[5][3];
        jButtons = new JButton[5];
        x = 0;
        for (JLabel[] JL : jLabels_NessesaryFiles) {
            JL[0] = new JLabel(LB[x], SwingConstants.CENTER);
            JL[1] = new JLabel(new ImageIcon(getClass().getResource("/ICON/NULL.png")));
            JL[2] = new JLabel("null", SwingConstants.CENTER);
            jButtons[x] = new JButton("Help");
            jPanel_NecessaryFiles_TMP.add(JL[0]);
            jPanel_NecessaryFiles_TMP.add(JL[1]);
            jPanel_NecessaryFiles_TMP.add(JL[2]);
            jPanel_NecessaryFiles_TMP.add(jButtons[x]);
            x++;
        }
        for (JButton JB : jButtons) {
            JB.addActionListener((evt) -> {
                EVT_HELP(JB);
            });
        }

        jButton_Continue = new javax.swing.JButton("Continue");
        jButton_ShowMap = new javax.swing.JButton("Show loaded map");
        jButton_OpenFile = new JButton("Select File");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1 = new JScrollPane(jTextArea1);

        jButton_OpenFile.setText("Select File");
        jButton_OpenFile.addActionListener((evt) -> {
            jButton_OpenFIleActionPerformed();
        });

        jButton_Continue.setEnabled(false);
        jButton_Continue.addActionListener((evt) -> {
            jButton_ContinueActionPerformed();
        });

        jButton_ShowMap.setEnabled(false);
        jButton_ShowMap.addActionListener((evt) -> {
            jButton_ShowMapActionPerformed();
        });

        JPanel JP_ALL = new JPanel();
        JP_ALL.setLayout(new BoxLayout(JP_ALL, BoxLayout.LINE_AXIS));
        jPanel_DataModel.setMaximumSize(new Dimension(jPanel_DataModel.getPreferredSize().width, Integer.MAX_VALUE));
        JP_ALL.add(jPanel_DataModel);
        JPanel JP_Group1 = new JPanel();
        JP_Group1.setLayout(new BoxLayout(JP_Group1, BoxLayout.PAGE_AXIS));
        jPanel_NecessaryFiles_TMP.setMaximumSize(new Dimension(Integer.MAX_VALUE, jPanel_NecessaryFiles_TMP.getPreferredSize().height));
        JP_Group1.add(jPanel_NecessaryFiles_TMP);
        JP_Group1.add(jScrollPane1);
        JPanel JP = new JPanel();
        JP.setLayout(new BoxLayout(JP, BoxLayout.PAGE_AXIS));
        JP.setBorder(BorderFactory.createTitledBorder("Upload country data"));
        JPanel JP_Group2 = new JPanel();
        JP_Group2.setLayout(new BoxLayout(JP_Group2, BoxLayout.LINE_AXIS));
        JP_Group2.add(jButton_OpenFile);
        jTextField_FilePath_MAP.setMaximumSize(new Dimension(Integer.MAX_VALUE, jTextField_FilePath_MAP.getPreferredSize().height));
        JP_Group2.add(jTextField_FilePath_MAP);
        JPanel JP_Group3 = new JPanel();
        JP_Group3.setLayout(new BoxLayout(JP_Group3, BoxLayout.LINE_AXIS));
        JP_Group3.add(jButton_ShowMap);
        JP_Group3.add(jButton_Continue);
        JP.add(JP_Group2);
        JP.add(JP_Group3);
        JP_Group1.add(JP);
        JP_ALL.add(JP_Group1);
        add(JP_ALL);

        setSize(1100, 500);
    }

    private void jButton_OpenFIleActionPerformed() {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser JFC = new JFileChooser();
        //JFC.setMultiSelectionEnabled(true);
        JFC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        AllOK = true;
        JFC.setFileFilter(new FileNameExtensionFilter("TXT", "txt"));

        boolean B_M = false, B_SN = false, B_FN = false, B_FU = false, B_TC = false;
        String TypeMap = "";

        if (JFC.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            jTextArea1.setText("Nodes size:\t" + Nodes + "\n");
            FILE = JFC.getSelectedFile();
            jTextField_FilePath_MAP.setText(FILE.getAbsolutePath());
            if (FILE.isDirectory()) {
                File Files[] = FILE.listFiles();
                for (File F : Files) {
                    if (F.getName().toLowerCase().endsWith("_polygon.txt")) {
                        TypeMap = F.getName().substring(4, F.getName().indexOf("_"));

                        for (JLabel[] JL : jLabels_NessesaryFiles) {
                            JL[1].setIcon(new ImageIcon(getClass().getResource("/ICON/UNCHECK.png")));
                            JL[2].setText("null");
                        }
                    }
                }

                for (File F : Files) {
                    if (!B_M && F.getName().toLowerCase().endsWith("_polygon.txt")) {
                        B_M = true;
                        if (Nodes != (getNumLines(F) - 2) / 2) {
                            jTextArea1.append("ERROR:\t" + "The file " + F.getName() + " don't have a correct matriz. " + "\tNo exception\n");
                            AllOK = false;
                        } else {
                            jLabels_NessesaryFiles[0][1].setIcon(new ImageIcon(getClass().getResource("/ICON/CHECK.png")));
                            jLabels_NessesaryFiles[0][2].setText(F.getName());
                            FileMap = F;
                        }
                    }
                    if (!B_SN && F.getName().toLowerCase().endsWith("_shortname.txt")
                            && TypeMap.equals(F.getName().substring(4, F.getName().indexOf("_")))) {
                        B_SN = true;
                        if (Nodes != getNumLines(F)) {
                            jTextArea1.append("ERROR:\t" + "The file " + F.getName() + " don't have a correct matriz. " + "\tNo exception\n");
                            AllOK = false;
                        } else {
                            jLabels_NessesaryFiles[1][1].setIcon(new ImageIcon(getClass().getResource("/ICON/CHECK.png")));
                            jLabels_NessesaryFiles[1][2].setText(F.getName());
                            FileShortName = F;
                        }
                    }
                    if (!B_FN && F.getName().toLowerCase().endsWith("_fullname.txt")
                            && TypeMap.equals(F.getName().substring(4, F.getName().indexOf("_")))) {
                        B_FN = true;
                        if (Nodes != getNumLines(F)) {
                            jTextArea1.append("ERROR:\t" + "The file " + F.getName() + " don't have a correct matriz. " + "\tNo exception\n");
                            AllOK = false;
                        } else {
                            jLabels_NessesaryFiles[2][1].setIcon(new ImageIcon(getClass().getResource("/ICON/CHECK.png")));
                            jLabels_NessesaryFiles[2][2].setText(F.getName());
                            FileFullName = F;
                        }
                    }
                    if (!B_TC && F.getName().toLowerCase().endsWith("_techname.txt")
                            && TypeMap.equals(F.getName().substring(4, F.getName().indexOf("_")))) {
                        B_TC = true;
                        if (Tech != getNumLines(F)) {
                            jTextArea1.append("ERROR:\t" + "The file " + F.getName() + " don't have a correct matriz. " + "\tNo exception\n");
                            AllOK = false;
                        } else {
                            jLabels_NessesaryFiles[3][1].setIcon(new ImageIcon(getClass().getResource("/ICON/CHECK.png")));
                            jLabels_NessesaryFiles[3][2].setText(F.getName());
                            FileTechName = F;
                        }
                    }
                    if (!B_FU && F.getName().toLowerCase().endsWith("_fuelname.txt")
                            && TypeMap.equals(F.getName().substring(4, F.getName().indexOf("_")))) {
                        B_FU = true;
                        if (Fuel != getNumLines(F)) {
                            jTextArea1.append("ERROR:\t" + "The file " + F.getName() + " don't have a correct matriz. " + "\tNo exception\n");
                            AllOK = false;
                        } else {
                            jLabels_NessesaryFiles[4][1].setIcon(new ImageIcon(getClass().getResource("/ICON/CHECK.png")));
                            jLabels_NessesaryFiles[4][2].setText(F.getName());
                            FileFuelName = F;
                        }
                    }
                }

            } else {
                System.out.println("NOPE");
                //jTextField_FilePath_MAP.setText(FILE.getAbsolutePath());
                //jTextArea1.setText("Nodes size:\t" + (L - 2) / 2);
            }

            if (!B_FN) {
                jTextArea1.append("ERROR:\t" + "The file _FullName.txt was not found\n");
                AllOK = false;
            }
            if (!B_FU) {
                jTextArea1.append("ERROR:\t" + "The file _FuelName.txt was not found\n");
                AllOK = false;
            }
            if (!B_SN) {
                jTextArea1.append("ERROR:\t" + "The file _ShortName.txt was not found\n");
                AllOK = false;
            }
            if (!B_TC) {
                jTextArea1.append("ERROR:\t" + "The file _TechName.txt was not found\n");
                AllOK = false;
            }

            jButton_Continue.setEnabled(AllOK);
            jButton_ShowMap.setEnabled(AllOK);
        }
    }

    private void jButton_ContinueActionPerformed() {//GEN-FIRST:event_jButton_ContinueActionPerformed
        new OpenDataToDisplay(FileTechName, FileFuelName, FileMap, FileFullName, FileShortName, Nodes, TS, Years, Tech).setVisible(true);
    }

    private void EVT_HELP(JButton JB) {
        JOptionPane.showMessageDialog(rootPane, LB[Arrays.asList(jButtons).indexOf(JB)]);
    }

    private void jButton_ShowMapActionPerformed() {
        Preview();
    }

    private void Preview() {
        JFrame JF = new JFrame("Preview");
        JF.add(new MAP(FileMap.getAbsolutePath(), FileShortName, FileFullName, Nodes, false));
        JF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JF.setSize(800, 600);
        JF.setLocationRelativeTo(null);
        JF.setVisible(true);
    }

    private int getNumLines(File F) {
        BufferedReader BR = null;
        int L = 0;
        try {
            BR = new BufferedReader(new FileReader(F));
            L = BR.lines().toArray().length;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(OpenCountryData.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                BR.close();
            } catch (IOException ex) {
                Logger.getLogger(OpenCountryData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return L;
    }

    private javax.swing.JButton jButton_OpenFile;
    private javax.swing.JButton jButton_Continue;
    private javax.swing.JButton jButton_ShowMap;

    private JLabel[][] jLabels_Model;
    private JLabel[][] jLabels_NessesaryFiles;
    private JButton[] jButtons;

    private javax.swing.JPanel jPanel_NecessaryFiles_TMP;
    private javax.swing.JPanel jPanel_DataModel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField_FilePath_MAP;
}
