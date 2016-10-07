import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.Color;
import java.util.*;

public class BinarySearch{
    private JFrame frame;
    private  int SIZE;
    private int PAUSE_TIME;
    
    private JPanel panel;
    private int[] array;
    private int key;
    private JButton[] buttonArray;
    private JLabel[] indexArray;
    private JLabel textMessage=new JLabel("information");
    private JLabel searchMessage=new JLabel("information");
    private JButton nextButton;
    private Box controlBox;
    private boolean programTerminated=false;
    private boolean algoChanged=false;
    
    
    public BinarySearch(){
        frame=new JFrame("BinarySearch");
        SIZE =20;
        PAUSE_TIME=1000;
        
        panel=new JPanel();
        array =new int[SIZE];
        buttonArray=new JButton[SIZE];
        indexArray=new JLabel[SIZE];
        
        controlBox=new Box(BoxLayout.Y_AXIS);
        nextButton = new JButton("NEXT");
        
        setUpGUI();
    }
    
    private  void setUpGUI(){
        frame.setSize(2000,170);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        frame.getContentPane().add(BorderLayout.CENTER,panel);
        
        panel.setLayout(new GridLayout(2,SIZE,2,2));
        
        frame.getContentPane().add(BorderLayout.SOUTH,textMessage);
        frame.getContentPane().add(BorderLayout.NORTH,searchMessage);
        frame.getContentPane().add(BorderLayout.WEST,controlBox);
        
        
        textMessage.setFont(new Font("Serif", Font.PLAIN, 20));
        searchMessage.setFont(new Font("Serif", Font.PLAIN, 20));
        nextButton.setFont(new Font("Serif", Font.PLAIN, 20));
        //set up the controlBox
 
        controlBox.add(nextButton);
        nextButton.addActionListener(new NextListener());
   
        //adds all the labels in the panel
        for(int i=0;i<SIZE;i++){
            indexArray[i]=new JLabel("");
            indexArray[i].setFont(new Font("Serif", Font.PLAIN, 20));
            panel.add(indexArray[i]);
            indexArray[0].setText("i");
        }
        //prepares a random array
        for(int i=0;i<SIZE;i++){
            array[i]=(int)(Math.random()*100);
      
        }
        //sorts it as binarysearch array has to be sorted
        Arrays.sort(array);
        for(int i=0;i<SIZE;i++){
            buttonArray[i]=new JButton(Integer.toString(array[i]));
            buttonArray[i].setFont(new Font("Serif", Font.PLAIN, 20));
            buttonArray[i].setBackground(Color.GREEN);
            panel.add(buttonArray[i]);
        }
        //computes a random key form the array
        key=array[(int)(Math.random()*SIZE)];
        searchMessage.setText("The key to search for is "+key);
       // rank(key,array);
    }
    
    public void rollTheBall(){
        rank(key,array);
    }
    
    
    public boolean hasProgramTerminated(){
        return programTerminated;
    }
    
    //clears all the indexField of indexArray
    private void clearIndexField(JLabel[]indexArray){
        for(int i=0;i<SIZE;i++)indexArray[i].setText("");
    }
    
    //all the buttons from hi to the end are colored gray to indicate that they are discarded
    private void eraseHigh(JButton[]buttonArray,int hi){
        for(int i=hi+1;i<SIZE;i++)buttonArray[i].setBackground(Color.GRAY);
    }
    
    //all the buttons from the begiing to low are colored gray to declare that they are discarded
    private void eraseLow(JButton[]buttonArray,int lo){
        for(int i=0;i<lo;i++)buttonArray[i].setBackground(Color.GRAY);
    }
    
    //pause for d seconds
    
    private void pause(Double d){
        try{
            Thread.sleep((int)(d*2000));
            
        }catch(Exception ex){}
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
    
    
    //finds out the position of a key given the array.returns -1 if not found
    
    private int rank(int key,int[]a){
        int lo=0;
        int hi=a.length-1;
        //sets the text to lo and hi
        indexArray[lo].setText("    lo");
        indexArray[hi].setText("    hi");
        textMessage.setText("we are searching between lo and hi");
        pause();
        
        while(lo<=hi){
            //selects an appropriate mid and sets the text for it
            int mid=lo+(hi-lo)/2;
            indexArray[mid].setText("     mid");
            blink(buttonArray[mid],Color.CYAN);
            //pauses for a while
            pause();
            //then select the mid to be""
            textMessage.setText("the middle index has the value :"+a[mid]);
            pause();
            
            
            
            if(key<a[mid])
            {
                //key is less than mid
                textMessage.setText("The key to search for is " +key +" which is smaller than "+a[mid]);
                pause();
                textMessage.setText("So we need to search between lo and mid-1");
                pause();
                //the high has changed.so set it appropriately
                indexArray[hi].setText("");
                indexArray[mid].setText("");
                hi=mid-1;
                indexArray[hi].setText("       hi");
                textMessage.setText("So lets search between lo and hi");
                //set all the buttons from hi to last to gray
                eraseHigh(buttonArray,hi);
                pause();
            }
            else if(key>a[mid])
            {
                textMessage.setText("The key to search for is "+key +" which is greater than "+a[mid]);
                pause();
                textMessage.setText("So we need to search between mid+1 and hi");
                pause();
                //the lo has changed.set it appropriately
                indexArray[lo].setText("");
                indexArray[mid].setText("");
                lo=mid+1;
                indexArray[lo].setText("        lo");
                textMessage.setText("So lets search between lo and hi");
                //set all the buttoms form first to lo to gray
                eraseLow(buttonArray,lo);
                pause();
            }
            
            
            else
            {
                //wow.the key has been found
                //clear all the indexfields.and set mid to got it.color mid red
                clearIndexField(indexArray);
                indexArray[mid].setText("Got It!!");
                buttonArray[mid].setBackground(Color.RED);
                textMessage.setText("Search Successful");
                return mid;
            }
            
            //pause at the end of each loop
            pause(1.5);
            
          
        }//end of while loop
        indexArray[lo].setText("NOT FOUND!!");
        return -1;
    }//end of rank method
    
    
       class NextListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            algoChanged=false;
            System.out.println("in next");
            //notify();
        }
    }
    
    public static void main(String[]args){
        BinarySearch sim=new BinarySearch();
        
    }
}