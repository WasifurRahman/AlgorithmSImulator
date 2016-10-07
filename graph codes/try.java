import javax.swing.*;
import java.awt.*;

public class try1{
    public static void main(String[]args){
        JFrame frame=new JFrame();
        frame.DefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,1000);
        frame.setVisible(true);
        
        
        MyPanel p=new MyPanel();
        frame.getContentPane().add(p,BorderLayout.CENTER);
        for(int i=0;i<10;i++){
            p.repaint();
        }
    }
    
    class MyPanel extends JPanel{
        public void paintComponent(Graphics g){
            Image=new ImageIcon("Selection sort.PNG").getImage();
            g2.drawImage(image,10,10,this);
        }
    }
    
}