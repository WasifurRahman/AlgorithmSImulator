import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

public class Insertion{
    
    private int PAUSE_TIME=1000;
    private int SIZE =15;
    private boolean programTerminated=false;
    private boolean algoChanged=false;
    
    private JFrame frame;
    private JPanel panel;
    private Integer[] array;
    private JButton[] buttonArray;
    private JLabel[] indexArray;
    private JLabel textMessage;
    private JButton nextButton;
    private Box controlBox;
    
    
    public Insertion(){
        frame=new JFrame("Insertion Sort");
        panel=new JPanel();
        array =new Integer[SIZE];
        buttonArray=new JButton[SIZE];
        indexArray=new JLabel[SIZE];
        textMessage=new JLabel("textMessage");
        nextButton=new JButton("NEXT");
        controlBox=new Box(BoxLayout.Y_AXIS);
        setUpGUI();
    }
    
    private void setUpGUI(){
        frame.setSize(2000,300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        frame.getContentPane().add(BorderLayout.CENTER,panel);
        frame.getContentPane().add(BorderLayout.SOUTH,textMessage);
        
        panel.setLayout(new GridLayout(2,SIZE,2,2));
        
        frame.getContentPane().add(BorderLayout.WEST,controlBox);
      
        
        textMessage.setFont(new Font("Serif", Font.PLAIN, 20));
        nextButton.setFont(new Font("Serif", Font.PLAIN, 20));
        
        controlBox.add(nextButton);
        nextButton.addActionListener(new NextListener());
        
        
        for(int i=0;i<SIZE;i++){
            indexArray[i]=new JLabel("");
            indexArray[i].setFont(new Font("Serif", Font.PLAIN, 20));
            panel.add(indexArray[i]);
            indexArray[0].setText("   i");
        }
        for(int i=0;i<SIZE;i++){
            array[i]=(int)(Math.random()*100);
      
        }
        for(int i=0;i<SIZE;i++){
            buttonArray[i]=new JButton(Integer.toString(array[i]));
            buttonArray[i].setFont(new Font("Serif", Font.PLAIN, 20));
            buttonArray[i].setBackground(Color.GRAY);
            panel.add(buttonArray[i]);
        }
        
       
        //sort(array);
    }
    
   
    
    
     public synchronized void pause(){
        System.out.println("in pause");
        
        algoChanged=true;
        notify();
       
        while(algoChanged){
            try{
                wait();
            }catch(InterruptedException ex){
                System.out.println("InterruptedExceptionCatched");
            }
        }
        //notify();
        
    }
    
    public synchronized void draw(){
        System.out.println("in draw");
        while(!algoChanged){
            try{
                wait();
            }catch(InterruptedException ex){
                System.out.println("InterruptedExceptionCatched");
            }
            
        }
        //panel.repaint();
        notify();
    }
    
    
    public void rollTheBall(){
        sort(array);
    }
    public boolean hasProgramTerminated(){
        return programTerminated;
    }
    
    private void clearIndexField(){
        for(int i=0;i<SIZE;i++)indexArray[i].setText("");
    }
    
   
    private void pause(double factor){
        try{
            Thread.sleep((int)(PAUSE_TIME * factor));
        }catch(Exception ex){}
    }
    
    private void blink(JButton button,Color prevColor){
        changeButtonColor(button,Color.GREEN,0.2);
        changeButtonColor(button,Color.BLUE,0.2);
        changeButtonColor(button,Color.GREEN,0.2);
        changeButtonColor(button,prevColor,0.2);
      }
    private void changeButtonColor(JButton button,Color color,double secToPause){
        button.setBackground(color);
        panel.repaint();
        pause(secToPause);
    }
    private void eraseLow(JButton[]buttonArray,int lo){
        for(int i=0;i<= lo;i++)buttonArray[i].setBackground(Color.GRAY);
    }
     private void colorLow(JButton[]buttonArray,int lo){
        for(int i=0;i<= lo;i++)buttonArray[i].setBackground(Color.GREEN);
    }
     
     private void changeColor(JButton[] buttonArray,Color color,int lo,int hi){
         for(int i=lo;i<=hi;i++)buttonArray[i].setBackground(color);
     }
    
    private void sort(Comparable[] a){
        int N=a.length;
        int position_j=0;
        //textMessage.setText("i moves from index 1 to the end of array");
        //pause();
        for(int i=1;i<N;i++){
            pause();
            if(i>0)indexArray[i-1].setText("");
            indexArray[i].setText("     i");
            //paint the part from 0 to i gray to show that they are unsorted
            //eraseLow(buttonArray,i);
            changeColor(buttonArray,Color.WHITE,0,i);
    
            textMessage.setText("The part from 0 to i is out of order");
            pause();
            textMessage.setText("So let's bring the part to order");
            pause();
            
            for(int j=i;j>0 && less(a[j],a[j-1]);j--){
                
                if(j==i)indexArray[j].setText("      i,j");
                if(j!=i)indexArray[j].setText("      j");
                if(j==i-1 &&j!=(N-1))indexArray[j+1].setText("      i");
                if(j!=i-1 && j!=(N-1))indexArray[j+1].setText("");
                pause();
                buttonArray[j].setBackground(Color.PINK);
                buttonArray[j-1].setBackground(Color.PINK);
                textMessage.setText("The items in j and j-1 index are out of order");
                pause();
                textMessage.setText("So swap them to bring them in order");
                pause();
                
                exch(a,j,j-1);
                
                buttonArray[j].setBackground(Color.GREEN);
                buttonArray[j-1].setBackground(Color.GREEN);
                textMessage.setText("j and j-1 are in order");
                
                pause();
                textMessage.setText(" ");
                position_j=j;
            }
          
            indexArray[position_j].setText("");
            
           //display the last position of j
            if(position_j>=1)indexArray[position_j-1].setText("        j");
            pause();
            colorLow(buttonArray,i);
            indexArray[i].setText("     i");
            textMessage.setText("No more comparisons are necessary in the part from 0 to i");
            pause();
            textMessage.setText("Everything from index 0 to i is in order");
            pause();
            if(position_j>=1)indexArray[position_j-1].setText("");
                
           
           if(i==1)buttonArray[0].setBackground(Color.GREEN);
           buttonArray[i].setBackground(Color.GREEN);
           pause();
        }
        indexArray[a.length-1].setText("");
        
        programTerminated=true;
    }
    
    private boolean less(Comparable v,Comparable w){
        return v.compareTo(w)<0;
    }
    private void exch(Comparable[]a,int i,int j){
        Comparable t=a[i];
        a[i]=a[j];
        a[j]=t;
        buttonArray[i].setText(a[i]+"");
        buttonArray[j].setText(""+a[j]);
    }
    private void show(Comparable[]a){
        for(int i=0;i<a.length;i++)StdOut.print(a[i]+" ");
        //StdOut.println();
    }
    private boolean isSorted(Comparable[]a){
        for(int i=1;i<a.length;i++){
            if(less(a[i],a[i-1]))return false;
        }
        return true;
    }
    
    class NextListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            algoChanged=false;
            System.out.println("in next");
            //notify();
        }
    }
    
   
    
    public static void main(String[]args){
        Insertion obj=new Insertion();
    }
}