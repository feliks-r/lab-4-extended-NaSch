import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Board extends JComponent implements MouseInputListener, ComponentListener {
    private static final long serialVersionUID = 1L;
    private Point[][] points;
    private int size = 10;
    public int editType = 0;
    private int maxVelocity = 5;
    private int p = 10;

    public Board(int length, int height) {
        addMouseListener(this);
        addComponentListener(this);
        addMouseMotionListener(this);
        setBackground(Color.WHITE);
        setOpaque(true);
    }

    private void initialize(int length, int height) {
        points = new Point[length][height];

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y] = new Point();
            }
        }
        for (int x = 0; x < points.length-1; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].setNext(points[x+points[x][y].getVelocity()][y]);
            }
        }

        for (int y = 0; y < points[points.length-1].length; ++y) {
            points[points.length-1][y].setNext(points[points[points.length-1][y].getVelocity()][y]);
        }
    }

    public void iteration() {

        Random rand = new Random();

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].setMoved(false);
            }
        }

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {

                if(points[x][y].getVelocity() < maxVelocity){
                    points[x][y].changeVelocity(1);
                }
                if(points[x][y].getVelocity() >= 1){
                    int n = rand.nextInt(100);
                    if(n<=p){
                        points[x][y].changeVelocity(-1);
                    }
                }
                int dist = 0;

                while(points[(x+dist+1)%points.length][y].getType() != 1 && dist <= points[x][y].getVelocity()){
                    dist+=1;
                }
                if(dist<points[x][y].getVelocity()){
                    points[x][y].setVelocity(dist);
                }
                points[x][y].setNext(points[(x+points[x][y].getVelocity())%points.length][y]);
                points[x][y].move();
            }
        }
        this.repaint();
    }

    public void clear() {
        for (int x = 0; x < points.length; ++x)
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].clear();
            }
        this.repaint();
    }


    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        g.setColor(Color.GRAY);
        drawNetting(g, size);
    }

    private void drawNetting(Graphics g, int gridSpace) {
        Insets insets = getInsets();
        int firstX = insets.left;
        int firstY = insets.top;
        int lastX = this.getWidth() - insets.right;
        int lastY = this.getHeight() - insets.bottom;

        int x = firstX;
        while (x < lastX) {
            g.drawLine(x, firstY, x, lastY);
            x += gridSpace;
        }

        int y = firstY;
        while (y < lastY) {
            g.drawLine(firstX, y, lastX, y);
            y += gridSpace;
        }

        for (x = 0; x < points.length; ++x) {
            for (y = 0; y < points[x].length; ++y) {
                float a = 1.0F;

                if(points[x][y].getType() == 1){
                    g.setColor(new Color(0, 0, 0));
                }
                else{
                    g.setColor(new Color(255, 255, 255));
                }

                g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
            }
        }

    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
            if (editType == 0) {
                points[x][y].clicked();
            }
            this.repaint();
        }
    }

    public void componentResized(ComponentEvent e) {
        int dlugosc = (this.getWidth() / size) + 1;
        int wysokosc = (this.getHeight() / size) + 1;
        initialize(dlugosc, wysokosc);
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
            if (editType == 0) {
                points[x][y].clicked();
            }
            this.repaint();
        }
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

}
