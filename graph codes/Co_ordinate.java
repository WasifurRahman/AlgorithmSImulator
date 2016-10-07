import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Co_ordinate{
            private double radius=40;
            private double x;
            private double y;
            private Color color;
            private boolean filled;
            public Co_ordinate(double a,double b){
                x=a;
                y=b;
                color=Color.LIGHT_GRAY;
                filled=true;
            }
            
            public int get_X(){return (int) (x);}
            public int get_Y(){return (int) (y);} 
            public int getCenX(){return (int)(x+radius/2);}
            public int getCenY(){return (int)(y+radius/2);}
            public boolean getFilled(){
                return filled;
            }
            public Color getColor(){
                return color;
            }
            
            public void setFilled(boolean b){
                filled=b;
            }
            public void setColor(Color c){
                color=c;
            }
        }