/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andres
 */
public class MAP extends javax.swing.JPanel {

    public MAP(String MAP, File ShortName, File FullName, int Nodes, boolean Event) {
        INI(MAP, ShortName, FullName, Nodes, Event);
    }

    private ArrayList<ArrayList<Double>> MP_X;
    private ArrayList<ArrayList<Double>> MP_Y;
    private boolean XY = false;
    private int REG_ZOOM = -1;
    private int X_Polygon[][];
    private int Y_Polygon[][];
    private double ESCALA,
            Move_X = 0, Move_Y = 0,
            MX_A = 0, MY_A = 0;

    private final Color ColorRegZoom = new Color(50, 50, 50, 220),
            ColorReg = new Color(100, 100, 100),
            ColorMap = new Color(180, 180, 180);

    public String[] ShortName_Array, FullName_Array;
    private String Chart1 = "null", Chart2 = "null";

    private void INI(String MAP, File ShortName, File FullName, int Nodes, boolean Event) {
        MP_X = new ArrayList<>();
        MP_Y = new ArrayList<>();
        BufferedReader BR;
        try {
            ShortName_Array = new String[Nodes];
            FullName_Array = new String[Nodes];
            BR = new BufferedReader(new FileReader(ShortName));
            for (int x = 0; x < Nodes; x++) {
                ShortName_Array[x] = BR.readLine();
            }
            BR = new BufferedReader(new FileReader(FullName));
            for (int x = 0; x < Nodes; x++) {
                FullName_Array[x] = BR.readLine();
            }

            System.out.println(new File(MAP).getAbsolutePath());
            BR = new BufferedReader(new FileReader(new File(MAP).getAbsoluteFile()));
            BR.lines().forEach((L) -> {
                ArrayList<Double> F = new ArrayList<>();
                for (String P : L.split("\t")) {
                    F.add(Double.parseDouble(P));
                }

                XY = !XY;
                if (XY) {
                    MP_X.add(F);
                } else {
                    MP_Y.add(F);
                }

            });
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MAP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MAP.class.getName()).log(Level.SEVERE, null, ex);
        }

        X_Polygon = new int[MP_X.size()][];
        Y_Polygon = new int[MP_Y.size()][];

        if (Event) {
            add_EVENT();
        }
        ESCALA = 8;
        gen_MAP();
    }

    private void gen_MAP() {
        int y_FIX = Integer.MAX_VALUE;
        for (ArrayList<Double> F : MP_Y) {
            for (Double Y : F) {
                if (Y < y_FIX) {
                    y_FIX = Y.intValue();
                }
            }
        }

        int cont = 0;
        for (ArrayList<Double> F : MP_X) {
            X_Polygon[cont] = new int[F.size()];
            int cont2 = 0;
            for (Double FF : F) {
                String N = String.valueOf(FF);
                if (N.contains(".")) {
                    X_Polygon[cont][cont2] = Integer.parseInt(String.valueOf(FF * ESCALA).split("\\.")[0]) + (int) Move_X;
                } else {
                    X_Polygon[cont][cont2] = Integer.parseInt(String.valueOf(FF * ESCALA)) + (int) Move_X;
                }
                cont2++;
            }
            cont++;
        }

        cont = 0;
        for (ArrayList<Double> F : MP_Y) {
            Y_Polygon[cont] = new int[F.size()];
            int cont2 = 0;
            for (Double FF : F) {
                String N = String.valueOf(FF);
                if (N.contains(".")) {
                    Y_Polygon[cont][cont2] = Integer.parseInt(String.valueOf(Math.abs((150 - FF)) * ESCALA).split("\\.")[0]) + (int) Move_Y;
                } else {
                    Y_Polygon[cont][cont2] = Integer.parseInt(String.valueOf(Math.abs((150 - FF)) * ESCALA)) + (int) Move_Y;
                }
                cont2++;
            }
            cont++;
        }

        repaint();
    }

    private void add_EVENT() {

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                ESCALA -= e.getWheelRotation() * 0.5;
                gen_MAP();
            }

        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int X = e.getX();
                int Y = e.getY();
                int cont = 0;
                for (int x = 0; x < MP_X.size(); x++) {
                    if (new Polygon(X_Polygon[x], Y_Polygon[x], X_Polygon[x].length).contains(X, Y) && x != 0) {
                        if (REG_ZOOM != x) {
                            REG_ZOOM = x;
                            repaint();
                        }
                        cont++;
                    }
                }
                if (cont == 0 && REG_ZOOM != -1) {
                    REG_ZOOM = -1;
                    repaint();
                }
                MX_A = e.getX();
                MY_A = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Move_X = e.getX() - MX_A;
                Move_Y = e.getY() - MY_A;
                gen_MAP();
            }

        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int X = e.getX();
                int Y = e.getY();
                for (int x = 0; x < MP_X.size(); x++) {
                    if (new Polygon(X_Polygon[x], Y_Polygon[x], X_Polygon[x].length).contains(X, Y) && x != 0) {
                        Visualizer.UpdataTab(x - 1);
                    }
                }
            }

        });
    }

    public boolean setChart(String Name) {
        if (Chart1.equals("null")) {
            Chart1 = Name;
            repaint();
            return true;
        } else if (Chart2.equals("null")) {
            Chart2 = Name;
            repaint();
            return true;
        } else {
            return false;
        }
    }

    public boolean removeChart(String Name) {
        if (Chart1.equals(Name)) {
            Chart1 = "null";
            repaint();
            return true;
        } else if (Chart2.equals(Name)) {
            Chart2 = "null";
            repaint();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D G = (Graphics2D) g;

        if (!Chart1.equals("null")) {
            G.drawString(Chart1, 400, 50);
        }
        if (!Chart2.equals("null")) {
            G.drawString(Chart2, 400, 100);
        }

        boolean Z = false;
        for (int x = 0; x < X_Polygon.length; x++) {
            if (x == 0) {
                G.setColor(ColorMap);
                Polygon P = new Polygon(X_Polygon[x], Y_Polygon[x], X_Polygon[x].length);

                G.fill(P);
            } else if (x != REG_ZOOM) {
                G.setColor(ColorReg);
                Polygon P = new Polygon(X_Polygon[x], Y_Polygon[x], X_Polygon[x].length);

                G.fill(P);

            } else {
                Z = true;
            }
        }

        for (int x = 0; x < X_Polygon.length; x++) {
            if (x != 0 && x != REG_ZOOM) {
                Polygon P = new Polygon(X_Polygon[x], Y_Polygon[x], X_Polygon[x].length);
                Rectangle B = P.getBounds();
                G.setColor(Color.black);
                G.drawString(getNombre(x - 1, false), (int) ((B.getMaxX() - B.getMinX()) / 2 + B.getMinX()), (int) ((B.getMaxY() - B.getMinY()) / 2 + B.getMinY()));
            }
        }

        if (Z) {
            G.setColor(ColorRegZoom);
            Polygon P = getREG_ZOOM(X_Polygon[REG_ZOOM], Y_Polygon[REG_ZOOM]);
            G.fill(P);
            Rectangle B = P.getBounds();
            G.setColor(Color.white);
            String Nombre = getNombre(REG_ZOOM - 1, true);
            G.drawString(Nombre, (int) ((B.getMaxX() - B.getMinX()) / 2 + B.getMinX()) - Nombre.length() * 2, (int) ((B.getMaxY() - B.getMinY()) / 2 + B.getMinY()));
        }

    }

    private String getNombre(int ID, boolean FullName) {
        return (FullName) ? FullName_Array[ID] : ShortName_Array[ID];
    }

    private Polygon getREG_ZOOM(int[] DATA_X, int[] DATA_Y) {
        Polygon P = new Polygon(DATA_X, DATA_Y, DATA_X.length);
        Rectangle B = P.getBounds();
        P = new Polygon();
        double ZOOM = 2;
        for (int x = 0; x < DATA_X.length; x++) {
            P.addPoint((int) (((DATA_X[x] * ZOOM) - B.getMinX()) - (B.getMaxX() - B.getMinX()) / 2), (int) (DATA_Y[x] * ZOOM - B.getMinY() - (B.getMaxX() - B.getMinX()) / 2));
        }

        return P;
    }

}
