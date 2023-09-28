import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/* Once the window opens up, click 3 random spots and the triangle will
 * appear. Click again to position the point P, and the program will
 * automatically determine if P is within, outside, or stradling triangle
 * ABC. 
 */

public class TriangleArea 
{
    static int phase = 1;
    static Point Mouse = new Point(0,0);

    static Point A;
    static Point B;
    static Point C;

    static Point P;

    public static void main(String args[])
    {
        JFrame window = new JFrame("Triangle Program");
        JPanel gfxwin;

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setSize(500, 500);
        window.setLocationRelativeTo(null);

        gfxwin = new JPanel(null)
        {
            @Override
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);

                if(C != null)
                {
                    //drawing triangle
                    g.setColor(Color.black);
                    g.drawPolygon(new int[] {A.x, B.x, C.x}, new int[] {A.y, B.y, C.y}, 3);
                }
                if(P != null)
                {
                    //drawing point cross
                    g.setColor(Color.red);
                    g.drawLine(P.x - 8, P.y, P.x + 8, P.y);
                    g.drawLine(P.x, P.y - 8, P.x, P.y + 8);

                    g.setFont(new Font("Consolas", Font.BOLD, 12));
                    g.drawString(findPoint(), 10, 15); //swap out algorithms here!
                }
            }
        };

        //mouse position
        gfxwin.addMouseMotionListener(new MouseMotionAdapter() 
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                Mouse.x = e.getX();
                Mouse.y = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) 
            {
                // empty by default
            }
        });

        gfxwin.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mousePressed(MouseEvent e) 
            {
                if (phase < 5) 
                {
                    if (phase == 1) 
                    {
                        A = new Point(Mouse.x, Mouse.y);
                        phase++;
                        gfxwin.repaint();
                    } else if (phase == 2) 
                    {
                        B = new Point(Mouse.x, Mouse.y);
                        phase++;
                        gfxwin.repaint();
                    } else if (phase == 3) 
                    {
                        C = new Point(Mouse.x, Mouse.y);
                        phase++;
                        gfxwin.repaint();
                    } else if (phase == 4) 
                    {
                        P = new Point(Mouse.x, Mouse.y);
                        phase++;
                        gfxwin.repaint();
                    }
                }
            }
        });


        gfxwin.setBounds(0, 0, 500, 500);
        window.setVisible(true);
        window.add(gfxwin);
    }

    //checks for if P is within, outside, or on the border of the ABC triangle
    public static String findPoint()
    {
        //Barycentric algorithm
        double alpha = ( (double)(B.y - C.y) * (double)(P.x - C.x) + (double)(C.x - B.x) * (double)(P.y - C.y)) / ((double)(B.y - C.y) * (double)(A.x - C.x) + (double)(C.x - B.x) * (double)(A.y - C.y));
        double beta = ( (double)(C.y - A.y) * (double)(P.x - C.x) + (double)(A.x - C.x) * (double)(P.y - C.y)) / ((double)(B.y - C.y) * (double)(A.x - C.x) + (double)(C.x - B.x) * (double)(A.y - C.y));
        double gamma = 1.0 - alpha - beta;

        if(alpha >= 0 && beta >= 0 && gamma >= 0)
            if(alpha == 0 || beta == 0 || gamma == 0)
                return "The Point is on the boundary of the triangle";
            else
                return "Point is inside the triangle";
        else
            return "The point is outside the triangle";
    }
}