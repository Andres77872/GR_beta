/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
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
        this.Nodes = Nodes;
        this.Tech = Tech;
        this.TS = TS;
        this.Years = Years;
        this.Fuel = Fuel;
        Initialize();
        setLayout();
    }

    private void Initialize() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTextArea1 = new JTextArea(5, 20);
        jTextField_FilePath_MAP = new JTextField("File Path");

        jPanel_NecessaryFiles_TMP = new JPanel(new GridBagLayout());
        jPanel_NecessaryFiles_TMP.setBorder(BorderFactory.createTitledBorder("Necessary files"));
        jPanel_DataModel = new JPanel(new GridBagLayout());
        jPanel_DataModel.setBorder(BorderFactory.createTitledBorder("Data model"));

        jLabels_Model = new JLabel[19][3];
        int x = 0;
        for (JLabel[] JL : jLabels_Model) {
            JL[0] = new JLabel(GR_NAME.get(x), SwingConstants.CENTER);
            JL[1] = new JLabel(GR_FULL_NAME[x], SwingConstants.CENTER);
            JL[2] = new JLabel("", SwingConstants.CENTER);
            GridBagConstraints GBC = new GridBagConstraints();
            GBC.ipadx = 8;
            GBC.ipady = 6;

            GBC.gridx = 0;
            GBC.gridy = x;
            jPanel_DataModel.add(JL[0], GBC);
            GBC.gridx = 1;
            GBC.gridy = x;
            jPanel_DataModel.add(JL[1], GBC);
            GBC.gridx = 2;
            GBC.gridy = x;
            jPanel_DataModel.add(JL[2], GBC);

            x++;
        }

        jLabels_NessesaryFiles = new Object[5][3];
        jButtons_NessesaryFiles = new JButton[5];
        x = 0;
        for (Object[] JL : jLabels_NessesaryFiles) {
            JL[0] = new JLabel(LB[x], SwingConstants.CENTER);
            JL[1] = new JLabel(new ImageIcon(getClass().getResource("/ICON/NULL.png")));
            JL[2] = new JTextField("No file selected yet") {
                @Override
                public boolean isEditable() {
                    return false;
                }
            };
            jButtons_NessesaryFiles[x] = new JButton("Help");
            GridBagConstraints S = new GridBagConstraints();
            S.ipadx = 12;
            S.gridx = 0;
            S.gridy = x;
            jPanel_NecessaryFiles_TMP.add((Component) JL[0], S);
            S.gridx = 1;
            S.gridy = x;
            jPanel_NecessaryFiles_TMP.add((Component) JL[1], S);
            S.gridx = 2;
            S.gridy = x;
            jPanel_NecessaryFiles_TMP.add((Component) JL[2], S);
            S.gridx = 3;
            S.gridy = x;
            jPanel_NecessaryFiles_TMP.add(jButtons_NessesaryFiles[x], S);
            x++;
        }
        for (JButton JB : jButtons_NessesaryFiles) {
            JB.addActionListener((evt) -> {
                EVT_HELP(JB);
            });
        }

        jButton_Continue = new JButton(new AbstractAction("Continue") {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton_ContinueActionPerformed();
            }
        });
        jButton_Continue.setEnabled(false);
        jButton_ShowMap = new JButton(new AbstractAction("Show loaded map") {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton_ShowMapActionPerformed();
            }
        });
        jButton_ShowMap.setEnabled(false);
        jButton_OpenFile = new JButton(new AbstractAction("Select File") {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton_OpenFIleActionPerformed();
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1 = new JScrollPane(jTextArea1);
        
        int ALL_TS = this.TS * this.Years;
        jTextArea1.append("Nodes:\t" + Nodes + "\n");
        jTextArea1.append("Years:\t" + Years + "\n");
        jTextArea1.append("TS:\t" + TS + "\n");
        jTextArea1.append("Fuel:\t" + Fuel + "\n");
        jTextArea1.append("Tech:\t" + Tech + "\n");

        jLabels_Model[17][2].setText(Nodes + " x " + Tech);
        jLabels_Model[18][2].setText(String.valueOf(ALL_TS));

        for (x = 0; x < jLabels_Model.length - 2; x++) {
            jLabels_Model[x][2].setText(Nodes + " x " + ALL_TS);
        }
    }

    private void setLayout() {
        JPanel JP_ALL = new JPanel(new BorderLayout(8, 8));
        JP_ALL.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        jPanel_DataModel.setMaximumSize(new Dimension(jPanel_DataModel.getPreferredSize().width, Integer.MAX_VALUE));
        JP_ALL.add(jPanel_DataModel, BorderLayout.LINE_START);
        JPanel JP_Group_R = new JPanel(new BorderLayout(8, 8));
        jPanel_NecessaryFiles_TMP.setMaximumSize(new Dimension(Integer.MAX_VALUE, jPanel_NecessaryFiles_TMP.getPreferredSize().height));
        JP_Group_R.add(jPanel_NecessaryFiles_TMP, BorderLayout.PAGE_START);
        JP_Group_R.add(jScrollPane1);
        JPanel JP_UploadCD = new JPanel(new BorderLayout(8, 8));
        JP_UploadCD.setBorder(BorderFactory.createTitledBorder("Upload country data"));
        JPanel JP_Group_OpenFile = new JPanel(new BorderLayout(8, 8));
        JP_Group_OpenFile.add(jButton_OpenFile, BorderLayout.LINE_START);
        JP_Group_OpenFile.add(jTextField_FilePath_MAP);
        JPanel JP_Group3 = new JPanel(new BorderLayout(8, 8));
        JP_Group3.add(jButton_ShowMap, BorderLayout.LINE_START);
        JP_Group3.add(jButton_Continue, BorderLayout.LINE_END);
        JP_UploadCD.add(JP_Group_OpenFile, BorderLayout.PAGE_START);
        JP_UploadCD.add(JP_Group3);
        JP_Group_R.add(JP_UploadCD, BorderLayout.PAGE_END);
        JP_ALL.add(JP_Group_R);
        add(JP_ALL);
        pack();
        setLocationRelativeTo(null);
    }

    private void jButton_OpenFIleActionPerformed() {
        JFileChooser JFC = new JFileChooser();
        //JFC.setMultiSelectionEnabled(true);
        JFC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        AllOK = true;
        JFC.setFileFilter(new FileNameExtensionFilter("TXT", "txt"));

        String TypeMap = "";
        Object[][] FilesFound = new Object[][]{
            {"_polygon.txt", "_shortname.txt", "_fullname.txt", "_techname.txt", "_fuelname.txt"},
            {false, false, false, false, false}};

        if (JFC.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            FILE = JFC.getSelectedFile();
            jTextField_FilePath_MAP.setText(FILE.getAbsolutePath());
            if (FILE.isDirectory()) {
                File Files[] = FILE.listFiles();
                for (File F : Files) {
                    if (F.getName().toLowerCase().endsWith("_polygon.txt")) {
                        TypeMap = F.getName().substring(4, F.getName().indexOf("_"));

                        for (Object[] JL : jLabels_NessesaryFiles) {
                            JLabel L = (JLabel) JL[1];
                            L.setIcon(new ImageIcon(getClass().getResource("/ICON/UNCHECK.png")));
                            JL[1] = L;
                            JTextField T = (JTextField) JL[2];
                            T.setText("No file selected yet");
                            JL[2] = T;
                        }
                    }
                }

                if (!TypeMap.equals("")) {
                    for (File F : Files) {
                        String FN = F.getName();
                        String SN = FN.substring(FN.lastIndexOf("_")).toLowerCase();
                        if (Arrays.asList(FilesFound[0]).contains(SN)) {
                            int IDX_FileFound = Arrays.asList(FilesFound[0]).indexOf(SN);
                            if ((IDX_FileFound == 0 && getNumLines(F) == (Nodes * 2 + 2))
                                    || ((IDX_FileFound == 3 && getNumLines(F) == Tech))
                                    || ((IDX_FileFound == 4 && getNumLines(F) == Fuel))
                                    || ((IDX_FileFound != 0 && getNumLines(F) == Nodes))) {
                                FilesFound[0][IDX_FileFound] = F;
                                FilesFound[1][IDX_FileFound] = true;
                                JLabel L = (JLabel) jLabels_NessesaryFiles[IDX_FileFound][1];
                                L.setIcon(new ImageIcon(getClass().getResource("/ICON/CHECK.png")));
                                jLabels_NessesaryFiles[IDX_FileFound][1] = L;

                                JTextField T = (JTextField) jLabels_NessesaryFiles[IDX_FileFound][2];
                                T.setText(FN);
                                jLabels_NessesaryFiles[IDX_FileFound][2] = T;

                            } else {
                                jTextArea1.append("ERROR:\t" + "The file " + FN + " don't have a correct matriz. 	No exception");
                            }

                        }
                    }
                }

            } else {
                System.out.println("NOPE");
                //jTextField_FilePath_MAP.setText(FILE.getAbsolutePath());
                //jTextArea1.setText("Nodes size:\t" + (L - 2) / 2);
            }

            for (int x = 0; x < FilesFound[0].length; x++) {
                if (FilesFound[1][x].equals(false)) {
                    jTextArea1.append("ERROR:\t" + "The file " + FilesFound[0][x] + " was not found\n");
                    AllOK = false;
                }
            }
            if (AllOK) {
                FileFuelName = (File) FilesFound[0][4];
                FileFullName = (File) FilesFound[0][2];
                FileMap = (File) FilesFound[0][0];
                FileShortName = (File) FilesFound[0][1];
                FileTechName = (File) FilesFound[0][3];

            }
            jButton_Continue.setEnabled(AllOK && !TypeMap.equals(""));
            jButton_ShowMap.setEnabled(AllOK && !TypeMap.equals(""));
        }
    }

    private void jButton_ContinueActionPerformed() {
        new OpenDataToDisplay(FileTechName, FileFuelName, FileMap, FileFullName, FileShortName, Nodes, TS, Years, Tech).setVisible(true);
    }

    private void EVT_HELP(JButton JB) {
        JOptionPane.showMessageDialog(rootPane, LB[Arrays.asList(jButtons_NessesaryFiles).indexOf(JB)]);
    }

    private void jButton_ShowMapActionPerformed() {
        Preview();
    }

    private void Preview() {
        JFrame JF = new JFrame("Preview");
        JF.add(new MAP(FileMap.getAbsolutePath(), FileShortName, FileFullName, Nodes, false));
        JF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
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
    private Object[][] jLabels_NessesaryFiles;
    private JButton[] jButtons_NessesaryFiles;

    private javax.swing.JPanel jPanel_NecessaryFiles_TMP;
    private javax.swing.JPanel jPanel_DataModel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField_FilePath_MAP;
}
