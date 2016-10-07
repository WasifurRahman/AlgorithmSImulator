import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

public class Quick{
    
    
    private  int ARRAY_SIZE =15;
    private  int PAUSE_TIME=1000;
    private  int ARRAY_NO=3;
    private boolean programTerminated=false;
    private boolean algoChanged=false;
    
    private JFrame frame;
    private JPanel panel;
    private Integer[] tempArray;
    private JLabel[] upperIndexArray;
    private JButton[] upperButtonArray;
    private JLabel[] lowerIndexArray;
    private JLabel textMessage;
    private Box controlBox;
    private JButton nextButton;
    
        
    public Quick(){
        
        frame=new JFrame("QUICK SORT");
        panel=new JPanel();
        tempArray =new Integer[ARRAY_SIZE];
        upperIndexArray=new JLabel[ARRAY_SIZE];
        upperButtonArray=new JButton[ARRAY_SIZE];
        lowerIndexArray=new JLabel[ARRAY_SIZE];
        textMessage=new JLabel("textMessage");
        controlBox=new Box(BoxLayout.Y_AXIS);
        nextButton=new JButton("NEXT");
        
        setUpGUI();
        
    }
    
    
    private void setUpGUI(){
        //sets up the frame and panel
        frame.setSize(2000,300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.getContentPane().add(BorderLayout.CENTER,panel);
        
        panel.setLayout(new GridLayout(ARRAY_NO,ARRAY_SIZE,2,2));
        
        frame.getContentPane().add(BorderLayout.SOUTH,textMessage);
        frame.getContentPane().add(BorderLayout.WEST,controlBox);
       
        textMessage.setFont(new Font("Serif", Font.PLAIN, 20));
        nextButton.setFont(new Font("Serif", Font.PLAIN, 20));
        
        
        controlBox.add(nextButton);
        nextButton.addActionListener(new NextListener());
        
        
        //sets up the arrays in the grid
        
        buildIndexArray(upperIndexArray);
        buildButtonArray(upperButtonArray);
        buildIndexArray(lowerIndexArray);
        
        //set up the random Temp array
        setUpTempArray(tempArray);
        setUpButtonArray(upperButtonArray,tempArray);
        
        
        //sort(tempArray);
    }
    public void rollTheBall(){
        sort(tempArray);
    }
    
     
     
    public boolean hasProgramTerminated(){
        return programTerminated;
    }
    
    private void pause(double d){
        try{
            Thread.sleep((int)(d*PAUSE_TIME));
        }catch(Exception ex){}
    }
    
    private void setUpTempArray(Integer[] array){
        for(int i=0;i<array.length;i++){
            array[i]=(int)(Math.random()*100);
        }
    }
    
    
    private void buildIndexArray(JLabel[] indexArray){
        for(int i=0;i<indexArray.length;i++){
            
            indexArray[i]=new JLabel("");
            indexArray[i].setFont(new Font("Serif", Font.PLAIN, 20));
            panel.add(indexArray[i]);
        }
    }
    
    private void buildButtonArray(JButton[] buttonArray){
        for(int i=0;i<buttonArray.length;i++){
            
            buttonArray[i]=new JButton();
            buttonArray[i].setFont(new Font("Serif", Font.PLAIN, 20));
            buttonArray[i].setBackground(Color.GRAY);
            panel.add(buttonArray[i]);
        }
    }
    
    private void setUpButtonArray(JButton[] buttonArray,Comparable[] array){
        for(int i=0;i<buttonArray.length;i++){
            buttonArray[i].setText(Integer.toString((Integer)array[i]));
        }
    }
    
    private void setUpButtonArray(JButton[] buttonArray,Comparable[] array,int lo,int hi){
        for(int i=lo;i<=hi;i++){
            buttonArray[i].setText(Integer.toString((Integer)array[i]));
        }
    }
    
    
    private void clearIndexArray(JLabel[] indexArray){
        for(int i=0;i<indexArray.length;i++)indexArray[i].setText("");
    }
    
    private void clearButtonArray(JButton[] buttonArray){
        for(int i=0;i<buttonArray.length;i++)buttonArray[i].setText("");
    }
    
    private void changeColor(JButton[] buttonArray,Color color,int lo,int hi){
        for(int i=lo;i<=hi;i++)buttonArray[i].setBackground(color);
    }
    
    private void blink(JButton button,Color prevColor){
        changeButtonColor(button,Color.GREEN,0.2);
        changeButtonColor(button,Color.BLUE,0.2);
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
        
        while(!algoChanged){
            try{
                wait();
            }catch(InterruptedException ex){
                System.out.println("InterruptedExceptionCatched");
            }
            
        }
        System.out.println("in draw");
        //panel.repaint();
        notify();
    }
    
    
    
    private void sort(Comparable[] a){
        //StdRandom.shuffle(a);
        sort(a,0,a.length-1);
        //after the sort is done,color everyone to be green to indicate that they are sorted
        changeColor(upperButtonArray,Color.GREEN,0,a.length-1);
        textMessage.setText("The whole array is sorted");
        programTerminated=true;
        
    }
    private void sort(Comparable[]a,int lo,int hi){
        
        if(hi<=lo){
            return;
        }
        int j=partition(a,lo,hi);
        sort(a,lo,j-1);
        sort(a,j+1,hi);
    }
    
    private int partition(Comparable[]a,int lo,int hi){
        //set the buttons for lo and hi appropriately
        upperIndexArray[lo].setText("   lo");
        lowerIndexArray[hi].setText("   hi");
        textMessage.setText("partition the subarray between lo and hi by taking "+a[lo]+" as the partitioning element");
        blink(upperButtonArray[lo],Color.CYAN);
        
        //color lo to hi in yellow to highlight it's importance;
        changeColor(upperButtonArray,Color.YELLOW,lo,hi);
        //color the partition integer with CYAN
        changeColor(upperButtonArray,Color.CYAN,lo,lo);
        pause();
        
        //set up initial value of i and j
        int i=lo;
        int j=hi+1;
        Comparable v=a[lo];
        
        //select appropriate positions for i and j text
        upperIndexArray[lo+1].setText("    i");
        lowerIndexArray[hi].setText("    hi,j");
        
        while(true){
            while(less(a[++i],v)){
                
                if(i!=lo+1){
                    upperIndexArray[i-1].setText("");//make the previous index of i ""
                    changeColor(upperButtonArray,Color.WHITE,i-1,i-1);//change the color of previous index to white
                }
                upperIndexArray[i].setText("    i");//set i in its new position
                textMessage.setText("increment i while the element in index i is less than the partition element");
                pause();
                
                if(i==hi)break;
            }
            
            //set i appropriately since its's current position has not
            //been captured by the loop.then take a a pause
            if(i!=lo+1){
                upperIndexArray[i-1].setText("");//make the previous index of i ""
                changeColor(upperButtonArray,Color.WHITE,i-1,i-1);//change the color of the previous entry to gray
            }
            upperIndexArray[i].setText("    i");//set i in its new position
            pause();
            
            while(less(v,a[--j])){
                
                if(j==hi-1){
                    lowerIndexArray[j+1].setText("    hi");//make the previous index of j ""
                    changeColor(upperButtonArray,Color.WHITE,j,j);
                }
                else if(j!=a.length-1){
                    lowerIndexArray[j+1].setText("");
                    changeColor(upperButtonArray,Color.WHITE,j,j);
                }
                
                if(j!=hi)lowerIndexArray[j].setText("    j");//set j in its new position
                
                //take a pause after changing j each time
                
                textMessage.setText("decrement j while the element in index j is greater than the partition element");
                pause();
                if(j==lo)break;
            }
            
            //set j appropriately because their latest position is not cactched in loop
            if(j==hi-1){
                lowerIndexArray[j+1].setText("    hi");//make the previous index of j ""
                changeColor(upperButtonArray,Color.WHITE,i-1,i-1);
            }
            else if(j!=a.length-1){
                lowerIndexArray[j+1].setText("");
                changeColor(upperButtonArray,Color.WHITE,j,j);
            }
            
            if(j!=hi)lowerIndexArray[j].setText("      j");//set j in its new position
            //pause for a moment
            pause();
            if(i>=j){
                textMessage.setText("i and j pointers have crossed.");
                pause(2.0);
                textMessage.setText("So lets swap the partition element with the element in j");
                pause();
                break;
            }
            
            textMessage.setText("So we need to swap between index i and j");
            pause();
            exch(a,i,j);
            
            //if the hi key has been colored gray,color it to yellow again
            changeColor(upperButtonArray,Color.YELLOW,hi,hi);
            changeColor(upperButtonArray,Color.CYAN,lo,lo);
        }
        exch(a,lo,j);
        
        //if the hi key has been colored gray,color it to yellow again
        changeColor(upperButtonArray,Color.YELLOW,hi,hi);
        changeColor(upperButtonArray,Color.CYAN,lo,lo);
        
        //clear up the index arrays
        clearIndexArray(upperIndexArray);
        clearIndexArray(lowerIndexArray);
        
        //change the color of all keys from lo to hi to gray
        changeColor(upperButtonArray,Color.WHITE,lo,hi);
        //color the partition element green to indicate that it is sorted
        textMessage.setText("The partition element "+a[j]+" is in the correct position");
        changeColor(upperButtonArray,Color.GREEN,j,j);
        pause();
        textMessage.setText("All the entries to its left are less that it and all the entries in the right are greater than it");
        //pause for a while to enhance visual effect
        pause();
        return j;
    }
    
    private boolean less(Comparable v,Comparable w){
        return v.compareTo(w)<0;
    }
    private void exch(Comparable[]a,int i,int j){
        
        
        
        Comparable t=a[i];
        a[i]=a[j];
        a[j]=t;
        
        //highlight the two indices to be swapped.pause for a while
        
        upperButtonArray[i].setBackground(Color.PINK);
        upperButtonArray[j].setBackground(Color.PINK);
        pause();
        textMessage.setText("Items in index i and j have been swapped");
        
        
        
        //set the buttomArray appropriately and pause for a while
        upperButtonArray[i].setText(""+a[i]);
        upperButtonArray[j].setText(""+a[j]);
        pause();
        
        //bring them back to their original color
        upperButtonArray[i].setBackground(Color.WHITE);
        upperButtonArray[j].setBackground(Color.WHITE);
    }
    private void show(Comparable[]a){
        //for(int i=0;i<a.length;i++)StdOut.print(a[i]+" ");
        //StdOut.println();
    }
    public boolean isSorted(Comparable[]a){
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
        Quick quick=new Quick();
        
    }
}