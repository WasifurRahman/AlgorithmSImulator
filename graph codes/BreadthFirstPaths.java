import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.imageio.*;
import java.io.*;


public class BreadthFirstPaths{
    //set up graph instance variables
    private boolean[] marked;
    private int[]edgeTo;
    private final int s;
    private Graph graph;
    
    
    //declare all the variabls and objects
    private int SIZE=6;
   
  
    private double TOP_X=80;
    private double TOP_Y=50;
    private int FRAME_HEIGHT=500;
    private int FRAME_WIDTH=600;
    private int PANEL_HEIGHT=800;
    private int PANEL_WIDTH=1000;
    private int CIRCLE_RADIUS=40;
    private int queueAddPos=0;
    private int queueRemovePos=0;
    private boolean algoChanged=false;//initially declare it to be not changed
    private boolean programTerminated=false;
    
    
    
    //declare all the frames, panels and object arrays
    JFrame frame=new JFrame("BREADTH FIRST SEARCH");
    JFrame helperFrame=new JFrame("BFS algo description");
    DrawPanel panel=new DrawPanel();
    Box queueBox=new Box(BoxLayout.Y_AXIS);
    Box controlBox=new Box(BoxLayout.Y_AXIS);
    JPanel panelBox=new JPanel();
    Co_ordinate[] co_ords=new Co_ordinate[SIZE];
    Color[][] edge_color=new Color[SIZE][SIZE];   //array to denote edge colors
    //JButton textButton=new JButton();
    String textMessage=" ";      //helper text 
    String vertexMessage=" ";
    // ArrayList<JButton> queueButtonArray=new ArrayList<JButton>(SIZE);
    JButton [] queueButtonArray=new JButton[SIZE];
    JButton nextButton=new JButton("NEXT");
 
    
    
    public BreadthFirstPaths(Graph G,int s){
        marked=new boolean[G.V()];
        edgeTo=new int[G.V()];
        
        this.s=s;
        this.graph=G;
        setUpGUI();//set up the gui within the constructor
        //bfs(G,s);
    }
    
    
    public void setUpGUI(){
        //sets up the frame and panel
        frame.setSize(PANEL_WIDTH,PANEL_HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        
        frame.getContentPane().add(BorderLayout.EAST,queueBox );
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.WEST,controlBox);
        //helperFrame.getContentPane().add(BorderLayout.CENTER,imageLabel);
        
        //loadImage("BFS.PNG");
        
        for(int i=0;i<SIZE;i++){
            queueButtonArray[i]=new JButton("       ");
            queueButtonArray[i].setFont(new Font("Serif", Font.PLAIN, 20));
            
        }
        //set up the queueBox;
        for(int i=0;i<SIZE;i++){
            queueBox.add(queueButtonArray[i]);
        }
        
        //set up the controlBox
        // controlBox.add(pauseButton);
        controlBox.add(nextButton);
        nextButton.addActionListener(new NextListener());
        
        
        
        
        
        //set up the co_ordinates of the vertices.
        //all the co_ordinates ar filled and gray by default
        setUpCo_ordinates(co_ords);
        //perpare the connetions
        //first declare alkl connections as white
        
        //set up the connected edge colors
        setUpEdgeColor(graph);
    }
    
    
   
    
    public void setUpEdgeColor(Graph graph){
        
        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                edge_color[i][j]=new Color(255,255,255);//color as white
                //edge_color[i][j]=Color.WHITE;
            }
        }
        
        for(int v=0;v<graph.V();v++){
            for(int w:graph.adj(v)){
                edge_color[v][w]=Color.BLACK;
            }
        }
        
    }
    

    
     public void setUpCo_ordinates(Co_ordinate[]co_ords){
        co_ords[0]=new Co_ordinate(TOP_X,TOP_Y);
        co_ords[1]=new Co_ordinate(TOP_X + FRAME_WIDTH/4,TOP_Y+FRAME_HEIGHT/2);
        co_ords[2]=new Co_ordinate(TOP_X+FRAME_WIDTH-2*CIRCLE_RADIUS,TOP_Y);
        co_ords[3]=new Co_ordinate(TOP_X+FRAME_WIDTH/2,TOP_Y+FRAME_HEIGHT*3/4);
        co_ords[4]=new Co_ordinate(TOP_X+FRAME_WIDTH-2*CIRCLE_RADIUS,TOP_Y+FRAME_HEIGHT-2*CIRCLE_RADIUS);
        co_ords[5]=new Co_ordinate(TOP_X,TOP_Y+FRAME_HEIGHT-2*CIRCLE_RADIUS);
    }
    
    public synchronized void pause(){
        System.out.println("in pause");
        
        algoChanged=true;
        notify();
        //pause(2.0);
        
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
        //System.out.println("in draw");
        while(!algoChanged){
            try{
                wait();
            }catch(InterruptedException ex){
                System.out.println("InterruptedExceptionCatched");
            }
            
        }
        panel.repaint();
        System.out.println("in draw");
        //pause(2.0);
        notify();
        
    }
    
    public void pause(double factor){
        
        try{
            Thread.sleep((int)(1000*factor));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void changeButtonText(JButton button,String newText){
        button.setText(" ");
        button.setText(newText);
        
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
    
    public boolean hasProgramTerminated(){
        return programTerminated;
    }
    
    
    public void rollTheBall(){
        bfs(graph,s);
    }
    
    private void bfs(Graph G,int s){
        Queue<Integer> queue=new Queue<Integer>();
        marked[s]=true;
        queue.enqueue(s);
        
        queueButtonArray[queueAddPos].setText(s+"    ");
        textMessage="Vertex "+s+" enqueued";
        blink(queueButtonArray[queueAddPos],queueButtonArray[queueAddPos].getBackground());
        queueAddPos+=1;
        pause();
        
        
        //change the text of the jButton while enqueued
        
        
        while(!queue.isEmpty())
        {
            int v=queue.dequeue();
            //remove the element form JButtonArray
            textMessage="The vertex to be dequeued is :"+v;
            blink(queueButtonArray[queueRemovePos],queueButtonArray[queueRemovePos].getBackground());
            pause();
            
            //if a vertice is dequeued,color it RED
            co_ords[v].setColor(Color.RED);
            //change the buttontext
            //changeButtonText(textButton,v+" dequeued");
            textMessage=new String("vertex: "+v+" dequeued");
            
            queueButtonArray[queueRemovePos++].setText("      ");
             pause();
            //set v to be the current vertex
            vertexMessage=new String("Current vertex: "+v);
            //repaint
            //panel.repaint();
            //pause for 2 seconds
            pause();
            
            for(int w:G.adj(v)){
                //whwn a vertice is visited,paint the path red
                edge_color[w][v]=Color.RED;
                edge_color[v][w]=Color.RED;
                textMessage=new String("vertex " +v+" visits "+ "vertex "+ w);
                //panel.repaint();
                //changeButtonText(textButton,v+" visits "+ w);
                
                //pause for 2 seconds
                pause();
                
                if(!marked[w]){
                    marked[w]=true;
                    edgeTo[w]=v;
                    queue.enqueue(w);
                    
                    queueButtonArray[queueAddPos].setText(w+"    ");
                    textMessage=new String("vertex "+w+" was not visited before,so enqueue it");
                    blink(queueButtonArray[queueAddPos],queueButtonArray[queueAddPos].getBackground());
                    //a vertex is enqueued.so add it in the array
                    queueButtonArray[queueAddPos++].setText(w+"     ");
                    
                    //changeButtonText(textButton,w +" enqueued");
                    
                    
                    
                    //if a vertex is enqueued,color it pink and repaint the canvas
                    co_ords[w].setColor(Color.PINK);
                    //panel.repaint();
                    //pause for a sec
                    pause();
                    
                }
                else{
                    textMessage=new String("vertex "+w+" was visited before,no need to enqueue it");
                    // panel.repaint();
                    //pause for a sec
                    pause();
                    
                }
                //paint the edge leading to the vertice to GRAY to indicate it visited,whether enqueued or not
                //but keep the enqueued vertice PINK
                edge_color[w][v]=Color.GRAY;
                edge_color[v][w]=Color.GRAY;
                //panel.repaint();
                pause();
                
                
            }
            //but paint the visited vertice(dequeued one) gray to indicate it is visited
            textMessage=new String("The tour form vertex "+v+" is complete");
            co_ords[v].setColor(Color.GRAY);
            //panel.repaint();
            pause();
            
        }
        
        //declare the program to be terminated
        programTerminated=true;
        textMessage="The BFS is complete";
    }
    
    public boolean hasPathTo(int v){
        return marked[v];
    }
    
    public Iterable<Integer> pathTo(int v)
    {
        if(!hasPathTo(v))return null;
        Stack<Integer> path=new Stack<Integer>();
        for(int x=v;x!=s;x=edgeTo[x]){
            path.push(x);
        }
        path.push(s);
        return path;
        
    }
    
    public class DrawPanel extends JPanel{
        public void paintComponent(Graphics g2){
            //cast out g2 to be a Graphics2D object
            Graphics2D g=(Graphics2D)g2;
            //first clear out the previous window
            g.setColor(Color.WHITE);
            
            g.fillRect(0,0,this.getWidth(),this.getHeight());
            //draw the string below the graph
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
            g.drawString(textMessage,(int)(TOP_X+FRAME_WIDTH*0.4),(int)(TOP_Y+ FRAME_HEIGHT));
            //draw the vertex Message
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
            g.drawString(vertexMessage,(int)(TOP_X+FRAME_WIDTH*0.4),(int)(TOP_Y+ FRAME_HEIGHT*1.07));
            //set the new stroke
            g.setStroke(new BasicStroke(3));
            
            //draw the edges to meet their appropriate color description
            for(int i=0;i<SIZE;i++){
                for(int j=0;j<SIZE;j++){
                    if(edge_color[i][j]==Color.WHITE)continue;
                    g.setColor(edge_color[i][j]);
                    g.drawLine(co_ords[i].getCenX(),co_ords[i].getCenY(),co_ords[j].getCenX(),co_ords[j].getCenY());
                }
            }
            
            //then draw the circles
            for(int i=0;i<SIZE;i++){
                g.setColor(co_ords[i].getColor());
                g.fillOval(co_ords[i].get_X(),co_ords[i].get_Y(),CIRCLE_RADIUS,CIRCLE_RADIUS);
            }
            
            //then draw the string near the top of the circles
            for(int i=0;i<SIZE;i++){
                g.setColor(Color.BLACK);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
                g.drawString(Integer.toString(i),co_ords[i].get_X()-20,co_ords[i].get_Y());
            }
            
//           
        }
        
    }
    
    class NextListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            algoChanged=false;
            System.out.println("in next");
            //notify();
        }
    }
    
    
    public static void main(String[]args){
        Graph G=new Graph(new In("tinyCG.txt"));
        int s=Integer.parseInt("0");
        BreadthFirstPaths path=new BreadthFirstPaths(G,s);
        
    }
}