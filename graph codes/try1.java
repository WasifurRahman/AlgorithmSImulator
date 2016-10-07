import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class try1{
    MyPanel p;
    JFrame frame;
    public void setUpGui(){
        frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,1000);
        frame.setVisible(true);
        try{
        Image image = ImageIO.read(new File("E:\\project cse\\sort codes\\Selection sort.PNG"));
        JLabel picLabel = new JLabel(new ImageIcon(image));
        frame.getContentPane().add(picLabel,BorderLayout.CENTER);
        }catch(Exception e){
        }
        
//        p=new MyPanel();
//        frame.getContentPane().add(p,BorderLayout.CENTER);
//        for(int i=0;i<10;i++){
//            p.repaint();
//        }
    }
    public static void main(String[]args){
        try1 t=new try1();
        t.setUpGui();
       
    }
    
    class MyPanel extends JPanel{
        public void paintComponent(Graphics g){
            //Image image=new ImageIcon("Selection sort.PNG").getImage();
            Image image=new ImageIcon("download.jpg").getImage();
            g.drawImage(image,10,10,this);
        }
    }
    
}