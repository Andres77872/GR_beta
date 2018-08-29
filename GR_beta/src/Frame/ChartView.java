/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.PieChart;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.CombinedRangeCategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.LineRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.TableOrder;

/**
 *
 * @author Andres
 */
public class ChartView extends JFrame {

    public static final int Format_OneChart = 0, Format_MultipleChart = 1;

    public ChartView(long[] DATA, String[] DATA_NAME, String Title, int ChartType, int Format) throws HeadlessException {
        ChartPanel CP = null;
        switch (Format) {
            case 0:
                switch (ChartType) {
                    case 0:
                        CP = getPieChart(DATA, DATA_NAME, Title);
                        break;
                    case 1:
                        CP = getLineChart(DATA, DATA_NAME, Title);
                        break;
                    case 2:
                        CP = getBarChart(DATA, DATA_NAME, Title);
                        break;
                    case 3:
                        CP = getAreaChart(DATA, DATA_NAME, Title);
                        break;
                }
                break;
        }
        INI(CP);
    }

    public ChartView(long[][] DATA, String[] DATA_NAME, String[] PLOT_NAME, String Title, int ChartType, int Format) throws HeadlessException {
        ChartPanel CP = null;
        switch (Format) {
            case 0:
                switch (ChartType) {
                    case 0:
                        CP = getPieChart(DATA, DATA_NAME, Title);
                        break;
                    case 1:
                        CP = getLineChart(DATA, DATA_NAME, PLOT_NAME, Title);
                        break;
                    case 2:
                        CP = getBarChart(DATA, DATA_NAME, PLOT_NAME, Title);
                        break;
                    case 3:
                        CP = getAreaChart(DATA, DATA_NAME, PLOT_NAME, Title);
                        break;
                }
                break;
            case 1:
                CP = getMuliplePieChart(DATA, DATA_NAME, PLOT_NAME, Title);
                break;
        }
        INI(CP);
    }

    public ChartView(long[][][] DATA, String[][] DATA_NAME, String[][] PLOT_NAME, String Title, int ChartType) throws HeadlessException {
        INI(getMulipleChart(DATA, DATA_NAME, PLOT_NAME, Title, ChartType));
    }

    private ChartPanel getPieChart(long[] DATA, String[] DATA_NAME, String Title) {
        DefaultPieDataset DPD = new DefaultPieDataset();

        for (int x = 0; x < DATA.length; x++) {
            DPD.setValue(DATA_NAME[x], DATA[x]);
        }
        return new ChartPanel(
                ChartFactory.createPieChart(Title, DPD, true, true, false));
    }

    private ChartPanel getPieChart(long[][] DATA, String[] DATA_NAME, String Title) {
        DefaultPieDataset DPD = new DefaultPieDataset();

        for (int x = 0; x < DATA.length; x++) {
            for (int y = 0; y < DATA[x].length; y++) {
                DPD.setValue(DATA_NAME[y], DATA[x][y]);
            }

        }

        return new ChartPanel(
                ChartFactory.createPieChart(Title, DPD, true, true, false));
    }

    private ChartPanel getLineChart(long[] DATA, String[] DATA_NAME, String Title) {
        DefaultCategoryDataset DPD = new DefaultCategoryDataset();

        for (int x = 0; x < DATA.length; x++) {
            DPD.addValue(DATA[x], Title.substring(Title.lastIndexOf(" ") + 1, Title.length()), DATA_NAME[x]);
        }

        return new ChartPanel(
                ChartFactory.createLineChart(Title, Title.substring(Title.indexOf(" of ") + 4, Title.indexOf(" for ")), "Value", DPD, PlotOrientation.VERTICAL, true, true, false));
    }

    private ChartPanel getLineChart(long[][] DATA, String[] DATA_NAME, String[] PLOT_NAME, String Title) {
        DefaultCategoryDataset DPD = new DefaultCategoryDataset();

        for (int x = 0; x < DATA.length; x++) {
            for (int y = 0; y < DATA[x].length; y++) {
                DPD.addValue(DATA[x][y], PLOT_NAME[x], DATA_NAME[y]);
            }
        }

        return new ChartPanel(
                ChartFactory.createLineChart(Title, Title.substring(Title.indexOf(" of ") + 4, Title.indexOf(" for ")), "Value", DPD, PlotOrientation.VERTICAL, true, true, false));
    }

    private ChartPanel getBarChart(long[] DATA, String[] DATA_NAME, String Title) {
        DefaultCategoryDataset DPD = new DefaultCategoryDataset();

        for (int x = 0; x < DATA.length; x++) {
            DPD.addValue(DATA[x], Title.substring(Title.lastIndexOf(" ") + 1, Title.length()), DATA_NAME[x]);
        }

        return new ChartPanel(
                ChartFactory.createBarChart(Title, Title.substring(Title.indexOf(" of ") + 4, Title.indexOf(" for ")), "Value", DPD, PlotOrientation.VERTICAL, true, true, false));
    }

    private ChartPanel getBarChart(long[][] DATA, String[] DATA_NAME, String[] PLOT_NAME, String Title) {
        DefaultCategoryDataset DPD = new DefaultCategoryDataset();

        for (int x = 0; x < DATA.length; x++) {
            for (int y = 0; y < DATA[x].length; y++) {
                DPD.addValue(DATA[x][y], PLOT_NAME[x], DATA_NAME[y]);
            }
        }

        return new ChartPanel(
                ChartFactory.createBarChart(Title, Title.substring(Title.indexOf(" of ") + 4, Title.indexOf(" for ")), "Value", DPD, PlotOrientation.VERTICAL, true, true, false));
    }

    private ChartPanel getAreaChart(long[] DATA, String[] DATA_NAME, String Title) {
        DefaultCategoryDataset DPD = new DefaultCategoryDataset();

        for (int x = 0; x < DATA.length; x++) {
            DPD.addValue(DATA[x], Title.substring(Title.lastIndexOf(" ") + 1, Title.length()), DATA_NAME[x]);
        }

        return new ChartPanel(
                ChartFactory.createAreaChart(Title, Title.substring(Title.indexOf(" of ") + 4, Title.indexOf(" for ")), "Value", DPD, PlotOrientation.VERTICAL, true, true, false));
    }

    private ChartPanel getAreaChart(long[][] DATA, String[] DATA_NAME, String[] PLOT_NAME, String Title) {
        DefaultCategoryDataset DPD = new DefaultCategoryDataset();

        for (int x = 0; x < DATA.length; x++) {
            for (int y = 0; y < DATA[x].length; y++) {
                DPD.addValue(DATA[x][y], PLOT_NAME[x], DATA_NAME[y]);
            }
        }

        return new ChartPanel(
                ChartFactory.createAreaChart(Title, Title.substring(Title.indexOf(" of ") + 4, Title.indexOf(" for ")), "Value", DPD, PlotOrientation.VERTICAL, true, true, false));
    }

    private ChartPanel getMuliplePieChart(long[][] DATA, String[] DATA_NAME, String[] PLOT_NAME, String Title) {

        DefaultCategoryDataset DPD = new DefaultCategoryDataset();

        for (int x = 0; x < DATA.length; x++) {
            for (int y = 0; y < DATA[x].length; y++) {
                DPD.addValue(DATA[x][y], PLOT_NAME[x], DATA_NAME[y]);
            }
        }

        return new ChartPanel(ChartFactory.createMultiplePieChart(Title, DPD, TableOrder.BY_COLUMN, true, true, false));
    }

    private ChartPanel getMulipleChart(long[][][] DATA, String[][] DATA_NAME, String[][] PLOT_NAME, String Title, int ChartType) {
        CombinedDomainCategoryPlot cdcp = new CombinedDomainCategoryPlot();
        DefaultCategoryDataset[] DCD = new DefaultCategoryDataset[DATA.length];
        DefaultCategoryDataset DCD_FULL = new DefaultCategoryDataset();
        System.out.println(DCD.length);
        for (int x = 0; x < DATA.length; x++) {
            DCD[x] = new DefaultCategoryDataset();
            for (int y = 0; y < DATA[x].length; y++) {
                for (int z = 0; z < DATA[x][y].length; z++) {
                    DCD[x].addValue(DATA[x][y][z], PLOT_NAME[x][y], DATA_NAME[x][z]);
                    DCD_FULL.addValue(DATA[x][y][z], PLOT_NAME[x][y], DATA_NAME[x][z]);
                }
            }
        }

        for (DefaultCategoryDataset D : DCD) {
            switch (ChartType) {
                case 1:
                    cdcp.add(new CategoryPlot(D, null, new NumberAxis("Value"), new LineAndShapeRenderer(true, true)));
                    break;
                case 2:
                    cdcp.add(new CategoryPlot(D, null, new NumberAxis("Value"), new BarRenderer()));
                    break;
                case 3:
                    cdcp.add(new CategoryPlot(D, null, new NumberAxis("Value"), new AreaRenderer()));
                    break;
            }
        }
        switch (ChartType) {
            case 1:
                cdcp.add(new CategoryPlot(DCD_FULL, null, new NumberAxis("Value"), new LineAndShapeRenderer(true, true)));
                break;
            case 2:
                cdcp.add(new CategoryPlot(DCD_FULL, null, new NumberAxis("Value"), new BarRenderer()));
                break;
            case 3:
                cdcp.add(new CategoryPlot(DCD_FULL, null, new NumberAxis("Value"), new AreaRenderer()));
                break;
        }
        return new ChartPanel(new JFreeChart(Title, JFreeChart.DEFAULT_TITLE_FONT, cdcp, true));
    }

    private void INI(ChartPanel CP) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 800);
        JPanel JP = new JPanel();
        JP.setLayout(new BoxLayout(JP, BoxLayout.PAGE_AXIS));
        JButton JB = new JButton("Save chart as image (PNG)");
        JB.addActionListener(((e) -> {
            try {
                FileOutputStream FOS = new FileOutputStream(new File("asd.png"));
                ChartUtilities.writeChartAsPNG(FOS, CP.getChart(), CP.getWidth(), CP.getHeight());
                FOS.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ChartView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ChartView.class.getName()).log(Level.SEVERE, null, ex);
            }

        }));
        JP.add(CP);
        JPanel JP2 = new JPanel();
        JP2.setLayout(new BoxLayout(JP2, BoxLayout.LINE_AXIS));
        JP2.add(JB);
        JP.add(JP2);
        add(JP);
        pack();
    }
}
