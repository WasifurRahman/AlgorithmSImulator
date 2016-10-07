import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;


public class Selection{
    private static int PAUSE_TIME=700;
    private static int SIZE =15;
    private boolean programTerminated=false;
    private boolean algoChanged=false;
    
    private JFrame frame;
    private JFrame textFrame;
    private JPanel panel;
    //private  TextPanel helperPanel;//holds the basic summary of the algo
    private Integer[] array;
    private JButton[] buttonArray;
    private JLabel[] indexArray;
    private JLabel textMessage;
    private Box controlBox;
    private JButton nextButton;
   
    
    
    public Selection(){
        frame=new JFrame("Selection Sort");

        panel=new JPanel();
        //helperPanel=new TextPanel();
        array =new Integer[SIZE];
        buttonArray=new JButton[SIZE];
        indexArray=new JLabel[SIZE];
        textMessage=new JLabel("textMessage");
        controlBox=new Box(BoxLayout.Y_AXIS);
        nextButton=new JButton("NEXT");
       
            
        setUpGUI();
    }
    
    
    private void setUpGUI(){
        frame.setSize(2000,200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        
        frame.getContentPane().add(BorderLayout.CENTER,panel);
        
        panel.setLayout(new GridLayout(2,SIZE,2,2));
        
        frame.getContentPane().add(BorderLayout.SOUTH,textMessage);
        frame.getContentPane().add(BorderLayout.WEST,controlBox);
        
        controlBox.add(nextButton);
        nextButton.addActionListener(new NextListener());
        
        textMessage.setFont(new Font("Serif", Font.PLAIN, 20));
        //searchMessage.setFont(new Font("Serif", Font.PLAIN, 20));
        nextButton.setFont(new Font("Serif", Font.PLAIN, 20));
        
        
        for(int i=0;i<SIZE;i++){
            indexArray[i]=new JLabel("");
            indexArray[i].setFont(new Font("Serif", Font.PLAIN, 20));
            panel.add(indexArray[i]);
            indexArray[0].setText("i");
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
    }
    
   
    
    public void rollTheBall(){
        //now begin the dance of the colors
        
        sort(array);
    }
    public void clearIndexField(){
        for(int i=0;i<SIZE;i++)indexArray[i].setText("");
    }
    
    public boolean hasProgramTerminated(){
        return programTerminated;
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
       // helperPanel.repaint();
        notify();
    }
    
    
    public void sort(Comparable[] a){
        int N = a.length;
        //helperPanel.repaint();
        //traverse through the loop 
        for(int i=0;i<N;i++){
            //set i accordingly
            
            indexArray[i].setText("     i");
            //erase the previous i unless that is 0
            if(i>0)indexArray[i-1].setText("");
            //keep the background of the minimum index CYAN
            int min=i;
            buttonArray[min].setBackground(Color.CYAN);
            textMessage.setText("Start traversing from i");
            pause();
            //traverse from j to the end of the array
            for(int j=i+1;j<N;j++){
                //set j accordingly
                indexArray[j].setText("     j");
                //erase the previous index of j unless they are 0 and i itself
                if(j>0 &&(j-1)!=i)indexArray[j-1].setText("");
                textMessage.setText("Now loop form j to the last index and color the min index CYAN");
                pause();
                //if j is less than min
                if(less(a[j],a[min])){
                    textMessage.setText("A new minimum has been found");
                    blink(buttonArray[j],Color.CYAN);
                    //set the background of min GRAY
                    buttonArray[min].setBackground(Color.GRAY);
                    //set new min
                    min=j;
                    //set the background of the new min as CYAN
                    //buttonArray[min].setBackground(Color.CYAN);
                }
                pause();
                //after each loop is over,set the last index to be ""
                indexArray[SIZE-1].setText("");
            }
            
            //now set the color of the two ints to be exchanged to PINK
            textMessage.setText("Swap the values of index i and min");
            pause();
            buttonArray[i].setBackground(Color.PINK);
            buttonArray[min].setBackground(Color.PINK);
            
            pause();
            //exchange i and min
            exch(a,i,min);
            pause();
            if(i!=min){
                //color i green since it is sorted
                //color min gray
                
                buttonArray[i].setBackground(Color.GREEN);
                buttonArray[min].setBackground(Color.GRAY);
            }
            else{
                buttonArray[i].setBackground(Color.GREEN);
            }
            textMessage.setText("The green part is sorted");
            pause();
        } 
        textMessage.setText("The whole array is sorted");
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
    
//     class TextPanel extends JPanel{
//        public void paintComponent(Graphics g2){
//            //System.out.println("textpanel called");
//            //for(int i=0;i<50;i++)System.out.println("hello");
//            Image image=new ImageIcon("download.jpg").getImage();
//            //ImageIcon=new ImageIcon("Selection sort.PNG");
//            g2.drawImage(image,10,10,this);
//        }
//     }
    
    public static void main(String[]args){
        //setUpFrame();
        Selection selection=new Selection();
        
    }
    
}
