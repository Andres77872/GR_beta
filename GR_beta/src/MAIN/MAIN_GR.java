/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MAIN;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

/**
 *
 * @author Andres
 */
public class MAIN_GR extends javax.swing.JFrame {

    private String[][] LB = {{"Nodes", "Arcs", "Technologies", "Lines", "Years", "Fuels", "Time Slice"},
    //{"N", "A", "T", "L", "Y", "F", "TS"}};
    {"53", "A", "14", "L", "366", "9", "24"}};
    private final String[] HELP = {"Nodes", "Arcs", "Technologies", "Lines", "Years", "Fuels", "Time Slice"};

    private final Font FONT = new Font("Dialog", 0, 16);

    public MAIN_GR() {
        initComponents();
    }

    private void initComponents() {

        JS = new JScrollPane();
        jTextArea1 = new JTextArea();
        jButton_Continue = new JButton("Continue");

        jLabel = new JLabel[LB[0].length];
        jTextField = new JTextField[LB[1].length];
        jPanel_Form = new JPanel(new GridLayout(0, 2));

        for (int x = 0; x < jLabel.length; x++) {
            jLabel[x] = new JLabel(LB[0][x], SwingConstants.CENTER);
            jLabel[x].setFont(FONT);
            jPanel_Form.add(jLabel[x]);

            jTextField[x] = new JTextField(LB[1][x]);
            jTextField[x].setFont(FONT);
            jTextField[x].setHorizontalAlignment(JTextField.CENTER);
            jPanel_Form.add(jTextField[x]);
        }

        for (JTextField TF : jTextField) {
            TF.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent evt) {
                    jTextField_FocusGained(TF);
                }
            });
        }

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(FONT);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        JS.setViewportView(jTextArea1);

        jButton_Continue.setFont(FONT);
        jButton_Continue.addActionListener((evt) -> {
            jButton_ContinueActionPerformed();
        });

        JPanel JP_ALL = new JPanel(new GridLayout(0, 2));
        JP_ALL.setBorder(BorderFactory.createTitledBorder("Data dimension"));
        JP_ALL.add(jPanel_Form);
        JPanel JP = new JPanel();
        JP.setLayout(new BoxLayout(JP, BoxLayout.PAGE_AXIS));
        JP.add(JS);
        JP.add(jButton_Continue);
        JP_ALL.add(JP);
        add(JP_ALL);

        pack();
        setLocationRelativeTo(null);
    }

    private void jButton_ContinueActionPerformed() {
        new Frame.OpenCountryData(
                Integer.parseInt(jTextField[0].getText()),
                Integer.parseInt(jTextField[2].getText()),
                Integer.parseInt(jTextField[6].getText()),
                Integer.parseInt(jTextField[4].getText()),
                Integer.parseInt(jTextField[5].getText())).setVisible(true);
    }

    private void jTextField_FocusGained(JTextField TF) {
        jTextArea1.setText(HELP[Arrays.asList(jTextField).indexOf(TF)]);
    }

    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        javax.swing.UIManager.setLookAndFeel(new WindowsLookAndFeel());
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MAIN_GR().setVisible(true);
            }
        });
    }

    private JButton jButton_Continue;
    private JLabel[] jLabel;
    private JTextField[] jTextField;
    private JPanel jPanel_Form;
    private JScrollPane JS;
    private JTextArea jTextArea1;

}
