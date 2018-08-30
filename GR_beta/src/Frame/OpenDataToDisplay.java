/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
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
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Andres
 */
public class OpenDataToDisplay extends javax.swing.JFrame implements Runnable {

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

    private String[] LB = {"Energy System", "Economic Balance", "Environmental Impact", "Social Approach"};

    public OpenDataToDisplay(File FileTechName, File FileFuelName, File FileMap, File FileFullName, File FileShortName, int Nodes, int TS, int Years, int Tech) {
        initComponents();
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
        GR_NAME = new ArrayList<>(OpenCountryData.GR_NAME);

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
            jLabel_DataDisplay[x][0] = new JLabel(GR_NAME.get(x));
            jLabel_DataDisplay[x][1] = new JLabel(new ImageIcon(getClass().getResource("/ICON/NULL.png")));
            jLabel_DataDisplay[x][2] = new JLabel("No selected");
            jLabel_DataDisplay[x][2].setHorizontalAlignment(JLabel.CENTER);
        }
    }

    int PareceNoSerParaNadaPeroSinEstoNoFunciona = 1;

    private void setLabels(int[] D) {
        jPanel_Var.removeAll();
        //Name
        jPanel_Var.add(new JLabel("==Name=="));
        //Icon
        jPanel_Var.add(new JLabel("==Is selected=="));
        //Thech size
        jPanel_Var.add(new JLabel("==Files loaded=="));

        //for (JLabel[] LB : jLabel_DataDisplay) {
        for (int x = 0; x < D.length; x++) {
            //Name
            jPanel_Var.add(jLabel_DataDisplay[D[x]][0]);
            //Icon
            jPanel_Var.add(jLabel_DataDisplay[D[x]][1]);
            //Thech size
            jPanel_Var.add(jLabel_DataDisplay[D[x]][2]);

        }
        this.setSize(this.getSize().width, this.getSize().height + PareceNoSerParaNadaPeroSinEstoNoFunciona);
        PareceNoSerParaNadaPeroSinEstoNoFunciona *= -1;
    }

    private void initComponents() {
        buttonGroup_ToggleButtons = new ButtonGroup();
        jToolBar1 = new JToolBar();
        jPanel_Var = new JPanel(new GridLayout(0, 3));
        jButton_OpenFile = new JButton("Select File");
        jTextField_FilePath = new JTextField("File Path");
        jButton_Acept = new JButton("Acept");
        jProgressBar1 = new JProgressBar();
        jPanel_Buttons = new JPanel(new GridLayout(2, 2));
        jTextPane_LOG = new JTextPane();
        jScrollPane2 = new JScrollPane(jTextPane_LOG);
        jLabel = new JLabel();
        jButton_Help = new JButton("Help");
        jToggleButtons = new JToggleButton[4];

        jToolBar1.setRollover(true);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        jPanel_Var.setBorder(BorderFactory.createTitledBorder("Variables"));

        jButton_OpenFile.addActionListener((evt) -> {
            jButton_OpenFileActionPerformed();
        });

        jButton_Acept.setEnabled(false);
        jButton_Acept.addActionListener((evt) -> {
            jButton_AceptActionPerformed();
        });

        jToggleButtons[0] = new JToggleButton(LB[0]);
        jToggleButtons[0].setSelected(true);
        jToggleButtons[1] = new JToggleButton(LB[1]);
        jToggleButtons[2] = new JToggleButton(LB[2]);
        jToggleButtons[3] = new JToggleButton(LB[3]);
        for (JToggleButton TB : jToggleButtons) {
            TB.addActionListener((evt) -> {
                EVT_jToggleButton(TB);
            });
            buttonGroup_ToggleButtons.add(TB);
            jPanel_Buttons.add(TB);
        }

        jLabel.setText("Upload data to display");

        JPanel JP = new JPanel();
        JP.setLayout(new BoxLayout(JP, BoxLayout.PAGE_AXIS));
        JP.add(jPanel_Var);
        JP.add(jProgressBar1);
        JP.add(jButton_Acept);

        JPanel JP2 = new JPanel();
        JP2.setLayout(new BoxLayout(JP2, BoxLayout.LINE_AXIS));
        JP2.add(jButton_OpenFile);
        jTextField_FilePath.setMaximumSize(new Dimension(Integer.MAX_VALUE, jTextField_FilePath.getPreferredSize().height));
        JP2.add(jTextField_FilePath);
        JP2.add(jButton_Help);

        JPanel JP3 = new JPanel();
        JP3.setLayout(new BoxLayout(JP3, BoxLayout.PAGE_AXIS));

        JP3.add(jLabel);
        JP3.add(JP2);
        jPanel_Buttons.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        JP3.add(jPanel_Buttons);
        JP3.add(jScrollPane2);

        JPanel JP_ALL = new JPanel();
        JP_ALL.setLayout(new BoxLayout(JP_ALL, BoxLayout.LINE_AXIS));

        JP_ALL.add(JP);
        JP_ALL.add(JP3);

        add(JP_ALL);

        setPreferredSize(new Dimension(800, 400));

        pack();
    }

    private void EVT_jToggleButton(JToggleButton TB) {
        switch (Arrays.asList(jToggleButtons).indexOf(TB)) {
            case 0:
                setLabels(new int[]{0, 17, 1, 2, 3});
                break;
            case 1:
                setLabels(new int[]{4, 5, 6, 18, 7});
                break;
            case 2:
                setLabels(new int[]{8, 9, 10});
                break;
            case 3:
                setLabels(new int[]{11, 12, 13, 14, 15, 16});
                break;
        }
    }

    private void jButton_OpenFileActionPerformed() {
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
    }

    private void jButton_AceptActionPerformed() {
        if (AllOK) {
            new Visualizer(FileMap, FileShortName, FileFullName, Nodes, Years, Tech, TS).setVisible(true);
        }
    }

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
        jTextPane_LOG.setText("");
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
                            jTextPane_LOG.setText(jTextPane_LOG.getText() + "ERROR:\t" + Var.getName()
                                    + "\tThe document must have " + (TS * Years) + " columns\n");
                            jTextPane_LOG.setCaretPosition(jTextPane_LOG.getText().length());
                            break;
                        }
                    }
                    if (OK) {
                        int VarIDX = GR_NAME.indexOf(V);
                        FilesLoaded[VarIDX]++;
                        CHECK_update(VarIDX, false);
                        jTextPane_LOG.setText(jTextPane_LOG.getText() + Name + "\t\twas loaded\n");
                        jTextPane_LOG.setCaretPosition(jTextPane_LOG.getText().length());
                    }
                } else {
                    CHECK_update(GR_NAME.indexOf(V), true);
                    jTextPane_LOG.setText(jTextPane_LOG.getText() + "ERROR:\t" + Var.getName()
                            + "\tThe document must have " + Nodes + " rows\n");
                    jTextPane_LOG.setCaretPosition(jTextPane_LOG.getText().length());
                }

            }
        } catch (IOException E) {
            CHECK_update(GR_NAME.indexOf(V), true);
            jTextPane_LOG.setText(jTextPane_LOG.getText() + "ERROR:\t" + Var.getName() + "\tIOException\n");
        } catch (NumberFormatException E) {
            CHECK_update(GR_NAME.indexOf(V), true);
            jTextPane_LOG.setText(jTextPane_LOG.getText() + "ERROR:\t" + Var.getName() + "\tNumberFormatException\n");
        } catch (ArrayIndexOutOfBoundsException E) {
            CHECK_update(GR_NAME.indexOf(V), true);
            jTextPane_LOG.setText(jTextPane_LOG.getText() + "ERROR:\t" + "The file " + Var.getName() + " don't have a correct matriz. " + "\tArrayIndexOutOfBoundsException\n");
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
    private javax.swing.ButtonGroup buttonGroup_ToggleButtons;
    private javax.swing.JButton jButton_Help;
    private javax.swing.JButton jButton_Acept;
    private javax.swing.JButton jButton_OpenFile;
    private javax.swing.JLabel jLabel;
    private javax.swing.JPanel jPanel_Var;
    private javax.swing.JPanel jPanel_Buttons;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField_FilePath;
    private javax.swing.JTextPane jTextPane_LOG;
    private JToggleButton[] jToggleButtons;
    private javax.swing.JToolBar jToolBar1;
}
