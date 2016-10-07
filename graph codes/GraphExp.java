import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GraphExp{
    //declare all the variabls and objects
    private int SIZE=6;
    private double TOP_X=50;
    private double TOP_Y=50;
    private int FRAME_HEIGHT=500;
    private int FRAME_WIDTH=500;
    private int PANEL_HEIGHT=2000;
    private int PANEL_WIDTH=2000;
    private int CIRCLE_RADIUS=40;
    
    
    
    //declare all the frames,panels and object arrays
    JFrame frame=new JFrame("lets begin graph");
    DrawPanel panel=new DrawPanel();
    Co_ordinate[] co_ords=new Co_ordinate[SIZE];
    Color[][] edge_color=new Color[SIZE][SIZE];//array to denota edge colotrs
    
    
     
     public void setUpGUI(){
        //sets up the frame and panel
        frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        frame.getContentPane().add(BorderLayout.CENTER,panel);
        
       //set up the co_ordinates of the vertices.
        //all the co_ordinates ar filled and gray by default
        setUpCo_ordinates(co_ords);
        //perpare the connetions
        //first declare alkl connections as white
        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                edge_color[i][j]=new Color(255,255,255);//color as white
                //edge_color[i][j]=Color.WHITE;
            }
        }
        setUpEdgeColor();
     }
     
     public void setUpEdgeColor(){
        edge_color[0][1]=Color.BLACK;
        edge_color[1][0]=Color.BLACK;
        edge_color[0][2]=Color.BLACK;
        edge_color[2][0]=Color.BLACK;
        edge_color[0][5]=Color.BLACK;
        edge_color[5][0]=Color.BLACK;
        edge_color[1][2]=Color.BLACK;
        edge_color[2][1]=Color.BLACK;
        edge_color[2][4]=Color.BLACK;
        edge_color[4][2]=Color.BLACK;
        edge_color[3][2]=Color.BLACK;
        edge_color[2][3]=Color.BLACK;
        edge_color[3][4]=Color.BLACK;
        edge_color[4][3]=Color.BLACK;
        edge_color[5][3]=Color.BLACK;
        edge_color[3][5]=Color.BLACK;
         
     }
     
     public void setUpCo_ordinates(Co_ordinate[]co_ords){
        co_ords[0]=new Co_ordinate(TOP_X,TOP_Y);
        co_ords[1]=new Co_ordinate(TOP_X + FRAME_WIDTH/4,TOP_Y+FRAME_HEIGHT/2);
        co_ords[2]=new Co_ordinate(TOP_X+FRAME_WIDTH-2*CIRCLE_RADIUS,TOP_Y);
        co_ords[3]=new Co_ordinate(TOP_X+FRAME_WIDTH/2,TOP_Y+FRAME_HEIGHT*3/4);
        co_ords[4]=new Co_ordinate(TOP_X+FRAME_WIDTH-2*CIRCLE_RADIUS,TOP_Y+FRAME_HEIGHT-2*CIRCLE_RADIUS);
        co_ords[5]=new Co_ordinate(TOP_X,TOP_Y+FRAME_HEIGHT-2*CIRCLE_RADIUS);
     }
        
        public class DrawPanel extends JPanel{
            public void paintComponent(Graphics g){
                //first clear out the previous window
                g.setColor(Color.WHITE);
                g.fillRect(0,0,this.getWidth(),this.getHeight());
                //then draw the circles
                for(int i=0;i<SIZE;i++){
                    g.setColor(co_ords[i].getColor());
                    g.fillOval(co_ords[i].get_X(),co_ords[i].get_Y(),CIRCLE_RADIUS,CIRCLE_RADIUS);
                }
                for(int i=0;i<SIZE;i++){
                    for(int j=0;j<SIZE;j++){
                        g.setColor(edge_color[i][j]);
                        g.drawLine(co_ords[i].get_X(),co_ords[i].get_Y(),co_ords[j].get_X(),co_ords[j].get_Y());
                    }
                }
            }
        }
        
        
        
        
        public static void main(String[]args){
            GraphExp exp=new GraphExp();
            exp.setUpGUI();
        }
        
        
}