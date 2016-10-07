import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;



public class Merge{
    private Comparable[] aux;
    
    private JFrame frame=new JFrame("MERGE SORT");
    private  int ARRAY_SIZE =20;
    private  int PAUSE_TIME=1500;
    private  int ARRAY_NO=5;
    private boolean algoChanged=false;
    private boolean programTerminated=false;
    
    JPanel panel=new JPanel();
    Integer[] tempArray =new Integer[ARRAY_SIZE];
    JLabel[] upperIndexArray=new JLabel[ARRAY_SIZE];
    JButton[] upperButtonArray=new JButton[ARRAY_SIZE];
    JLabel[] middleIndexArray=new JLabel[ARRAY_SIZE];
    JButton[] lowerButtonArray=new JButton[ARRAY_SIZE];
    JLabel[] lowerIndexArray=new JLabel[ARRAY_SIZE];
    JButton nextButton=new JButton("NEXT");
    JLabel textMessage=new JLabel(" ");
    Box controlBox=new Box(BoxLayout.Y_AXIS);
   
    
    public Merge(){
        setUpGUI();
    }
    
    private  void setUpGUI(){
        //sets up the frame and panel
        frame.setSize(2000,400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.getContentPane().add(BorderLayout.CENTER,panel);
        frame.getContentPane().add(BorderLayout.WEST,controlBox);
        frame.getContentPane().add(BorderLayout.SOUTH,textMessage);
       
        
        panel.setLayout(new GridLayout(ARRAY_NO,ARRAY_SIZE,2,2));
        
        textMessage.setFont(new Font("Serif", Font.PLAIN, 20));
        nextButton.setFont(new Font("Serif", Font.PLAIN, 20));
        
        controlBox.add(nextButton);
        nextButton.addActionListener(new NextListener());
        
        
        //sets up the arrays in the grid
        
        buildIndexArray(upperIndexArray);
        buildButtonArray(upperButtonArray);
        buildIndexArray(middleIndexArray);
        buildButtonArray(lowerButtonArray);
        buildIndexArray(lowerIndexArray);
        
        //set up the random Temp array
        setUpTempArray(tempArray);
        setUpButtonArray(upperButtonArray,tempArray);
        
        
        // sort(tempArray);
    }
    
    
     
    public void rollTheBall(){
        sort(tempArray);
    }
    private  void pause(double d){
        try{
            Thread.sleep((int)(d*1000));
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
    
    public boolean hasProgramTerminated(){
        return programTerminated;
    }
    
    private  void setUpTempArray(Integer[] array){
        for(int i=0;i<array.length;i++){
            array[i]=(int)(Math.random()*100);
        }
    }
    
    
    private  void buildIndexArray(JLabel[] indexArray){
        for(int i=0;i<indexArray.length;i++){
            indexArray[i]=new JLabel("");
            indexArray[i].setFont(new Font("Serif", Font.PLAIN, 20));
            panel.add(indexArray[i]);
        }
    }
    
    private  void buildButtonArray(JButton[] buttonArray){
        for(int i=0;i<buttonArray.length;i++){
            buttonArray[i]=new JButton();
            buttonArray[i].setFont(new Font("Serif", Font.PLAIN, 20));
            buttonArray[i].setBackground(Color.GRAY);
            panel.add(buttonArray[i]);
        }
    }
    
    private  void setUpButtonArray(JButton[] buttonArray,Comparable[] array){
        for(int i=0;i<buttonArray.length;i++){
            buttonArray[i].setText(Integer.toString((Integer)array[i]));
        }
    }
    
    private  void setUpButtonArray(JButton[] buttonArray,Comparable[] array,int lo,int hi){
        for(int i=lo;i<=hi;i++){
            buttonArray[i].setText(Integer.toString((Integer)array[i]));
        }
    }
    
    
    private  void clearIndexArray(JLabel[] indexArray){
        for(int i=0;i<indexArray.length;i++)indexArray[i].setText("");
    }
    
    private  void clearButtonArray(JButton[] buttonArray){
        for(int i=0;i<buttonArray.length;i++)buttonArray[i].setText("");
    }
    
    private  void changeColor(JButton[] buttonArray,Color color,int lo,int hi){
        for(int i=lo;i<=hi;i++)buttonArray[i].setBackground(color);
    }
    private  void changeColorInMerging(int upper,int lower){
        changeColor(upperButtonArray,Color.PINK,upper,upper);
        changeColor(lowerButtonArray,Color.PINK,lower,lower);
        pause();
        
    }
     
      private void blinkTwoButtons(JButton b1,JButton b2){
        Color prev1=b1.getBackground();
        Color prev2=b2.getBackground();
        changeButtonColor(b1,b2,Color.GREEN,0.25);
        changeButtonColor(b1,b2,Color.BLUE,0.25);
        changeButtonColor(b1,b2,Color.GREEN,0.25);
        changeButtonColor(b1,b2,Color.BLUE,0.25);
        changeButtonColor(b1,b2,Color.GREEN,0.25);
        //changeButtonColor(b1,b2,prev1,prev2,0.1);
        b1.setBackground(prev1);
        b2.setBackground(prev2);
        panel.repaint();
    }
    private void changeButtonColor(JButton button1,JButton button2,Color color,double secToPause){
        button1.setBackground(color);
        button2.setBackground(color);
        panel.repaint();
        pause(secToPause);
    }
    
    
 
    private  void sort(Comparable[] a){
        aux=new Comparable[a.length];
        sort(a,0,a.length-1);
        textMessage.setText("The whole array is sorted");
        changeColor(upperButtonArray,Color.GREEN,0,a.length-1);
        programTerminated=true;
    }
    private  void sort(Comparable[]a,int lo,int hi){
        if(hi<=lo)return;
        int mid=lo+(hi-lo)/2;
        //sets up lo,mid and hi while splitting up the array
        upperIndexArray[lo].setText("    lo");
        upperIndexArray[hi].setText("    hi");
        middleIndexArray[mid].setText("    mid");
        textMessage.setText("Recursively sorting between lo and hi");
        pause();
                            
        clearIndexArray(upperIndexArray);
        clearIndexArray(middleIndexArray);
        
        sort(a,lo,mid);
        sort(a,mid+1,hi);
        
        //sets up the lo and hi for merging
        upperIndexArray[lo].setText("    lo");
        upperIndexArray[hi].setText("    hi");
        //middleIndexArray[mid].setText("    mid");
        //textMessage.setText("Recursively sorting between lo and hi");
        // middleIndexArray[mid].setText("   mid");
        
        merge(a,lo,mid,hi);
        
        clearIndexArray(upperIndexArray);
        clearIndexArray(middleIndexArray);
    }
    private  void merge(Comparable[] a,int lo,int mid,int hi){
        int upper,lower;
        changeColor(upperButtonArray,Color.ORANGE,lo,hi);
        String prevText=middleIndexArray[mid].getText();//get the text of the upper mid index
        middleIndexArray[mid].setText("    mid");
        textMessage.setText("Merging between the two sorted subarrays [lo....mid] and [mid+1....hi]");
        pause();
        //set mid to be prev index so that i and j is unhampered
        middleIndexArray[mid].setText(prevText);
        
                                
        int i=lo,j=mid+1;
        for(int k=lo;k<=hi;k++){
            aux[k]=a[k];
        }
        lowerButtonArray[mid].setBackground(Color.CYAN);
        
        setUpButtonArray(lowerButtonArray,aux,lo,hi);
        textMessage.setText("All the elements between lo and hi are copied in the auxiliary array");
        pause();
                            
        for(int k=lo;k<=hi;k++){
            //sets up i,j,k properly 
            if(i<=mid)lowerIndexArray[i].setText("     i");
            if(j<=hi)lowerIndexArray[j].setText("     j");
            middleIndexArray[k].setText("     k");
            
            
            if(i>mid){
                //i>mid.so cpy elments from the second part of the array
                upper=k;
                lower=j;
                textMessage.setText("i has overrun j.so we have to copy element to the upper array from index j exclusively.Then increment j");
                changeColorInMerging(upper,lower);
                a[k]=aux[j++];
                pause();
            }
            else if(j>hi){
                upper=k;
                lower=i;
                textMessage.setText("j has overrun hi.so we have to copy element to the upper array  from index i exclusively.Then increment i");
                changeColorInMerging(upper,lower);
                a[k]=aux[i++];
                pause();
            }
            else if(less(aux[j],aux[i])){
                upper=k;
                lower=j;
                textMessage.setText("element in index j is less than that of i.so,copy element to the upper array from index j.Then increment j");
                blinkTwoButtons(lowerButtonArray[i],lowerButtonArray[j]);
                changeColorInMerging(upper,lower);
                a[k]=aux[j++];
                pause();
            }
            else{
                upper=k;
                lower=i;
                textMessage.setText("element in index i is less than that of j.so,copy element to the upper array from index i.Then increment i");
                blinkTwoButtons(lowerButtonArray[i],lowerButtonArray[j]);
                a[k]=aux[i++];
                changeColorInMerging(upper,lower);
                pause();
            }
            
            //undo color changing in comparison
            changeColor(upperButtonArray,Color.ORANGE,upper,upper);
            changeColor(lowerButtonArray,Color.GRAY,lower,lower);
            if(lower==mid)changeColor(lowerButtonArray,Color.CYAN,lower,lower);
            
            
            //the element in k is sorted.so mark it green
            upperButtonArray[k].setText(""+a[k]);
            changeColor(upperButtonArray,Color.GREEN,k,k);
            textMessage.setText("Elements in range [lo...k] are sorted.(Within the [lo...hi] region)");
            pause();
            
            clearIndexArray(lowerIndexArray);
            clearIndexArray(middleIndexArray);
            
        }
        textMessage.setText("Elements in range [lo...hi] are sorted.");
        pause();
        changeColor(upperButtonArray,Color.GRAY,lo,hi);
        lowerButtonArray[mid].setBackground(Color.GRAY);
        clearButtonArray(lowerButtonArray);
    }
    
    private  boolean less(Comparable v,Comparable w){
        return v.compareTo(w)<0;
    }
    private  void exch(Comparable[]a,int i,int j){
        Comparable t=a[i];
        a[i]=a[j];
        a[j]=t;
    }
    private  void show(Comparable[]a){
       // for(int i=0;i<a.length;i++)StdOut.print(a[i]+" ");
        // StdOut.println();
    }
    private  boolean isSorted(Comparable[]a){
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
        
        
    }
}