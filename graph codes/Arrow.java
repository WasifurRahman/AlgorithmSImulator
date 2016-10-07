import java.awt.*;
import javax.swing.*;
import static java.awt.geom.AffineTransform.*;
import java.awt.geom.AffineTransform;

public class Arrow{
    JFrame frame=new JFrame();
    public Arrow(){
 
    frame.setSize(1000,1000);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    MyDrawPanel panel=new MyDrawPanel();
    frame.add(panel);
    }
    class MyDrawPanel extends JPanel{
        
            private final int ARR_SIZE = 4;

            void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
                Graphics2D g = (Graphics2D) g1.create();

                double dx = x2 - x1, dy = y2 - y1;
                double angle = Math.atan2(dy, dx);
                int len = (int) Math.sqrt(dx*dx + dy*dy);
                AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
                at.concatenate(AffineTransform.getRotateInstance(angle));
                g.transform(at);

                // Draw horizontal arrow starting in (0, 0)
                g.drawLine(0, 0, len, 0);
                g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                              new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
            }

            public void paintComponent(Graphics g) {
                for (int x = 15; x < 200; x += 16)
                    drawArrow(g, x, x, x, 150);
                drawArrow(g, 30, 300, 300, 190);
            }

    }
    
    public static void main(String[]args){
        Arrow arrow=new Arrow();
    }
}