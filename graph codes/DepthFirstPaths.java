import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

public class DepthFirstPaths{
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
    private boolean algoChanged=false;//initially declare it to be not changed
    private boolean programTerminated=false;
    
    
    
    //declare all the frames,panels and object arrays
    JFrame frame=new JFrame("DEPTH FIRST SEARCH");
    Box controlBox=new Box(BoxLayout.Y_AXIS);
    DrawPanel panel=new DrawPanel();
    Co_ordinate[] co_ords=new Co_ordinate[SIZE];
    Color[][] edgeColor=new Color[SIZE][SIZE];//array to denote edge colotrs
    String textMessage;
    String vertexMessage;//string to tell the current vertex visited
    JButton nextButton=new JButton("NEXT");
   
    
    
    public void setUpGUI(){
        //sets up the frame and panel
        frame.setSize(PANEL_WIDTH,PANEL_HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        frame.getContentPane().add(BorderLayout.CENTER,panel);
        frame.getContentPane().add(BorderLayout.WEST,controlBox);
     
        
        controlBox.add(nextButton);
        nextButton.addActionListener(new NextListener());
        
        //set up the co_ordinates of the vertices.
        //all the co_ordinates ar filled and gray by default
        setUpCo_ordinates(co_ords);
        
        //perpare the connetions
        setUpEdgeColor(graph);
    }
    
    
    public void setUpEdgeColor(Graph graph){
        
         for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                edgeColor[i][j]=new Color(255,255,255);//color as white
                //edge_color[i][j]=Color.WHITE;
            }
        }
        for(int v=0;v<graph.V();v++){
            for(int w:graph.adj(v)){
                edgeColor[v][w]=Color.BLACK;
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
    
    public void pause(double factor){
        try{
            Thread.sleep((int)(2000*factor));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public boolean hasProgramTerminated(){
        return programTerminated;
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
        public void changeEdgeColor(int v,int w,Color color,double secToPause){
        edgeColor[v][w]=color;
        edgeColor[w][v]=color;
        panel.repaint();
        pause(secToPause);
    }
      
      
      public void changeVertexColor(int v,Color color,double secToPause){
          co_ords[v].setColor(color);
          panel.repaint();
          pause(secToPause);
      }
      
   
    
    public void vertexBlink(int v,Color color){
        // v is the vertex whose color is vertexBlink
        //color is the initial color of the vertex
        changeVertexColor(v,Color.GREEN,0.5);
        changeVertexColor(v,Color.BLUE,0.5);
        changeVertexColor(v,Color.GREEN,0.5);
        changeVertexColor(v,color,0.2);
    }
    
    
    public void edgeBlink(int v,int w,Color color){
        // v is the vertex whose color is vertexBlink
        //color is the initial color of the vertex
        
        changeEdgeColor(v,w,Color.GREEN,0.4);
        changeEdgeColor(v,w,Color.BLUE,0.4);
        changeEdgeColor(v,w,Color.GREEN,0.4);
        changeEdgeColor(v,w,Color.BLUE,0.4);
        changeEdgeColor(v,w,Color.GREEN,0.4);
        changeEdgeColor(v,w,color,0.01);
    }
    
    
    
    public DepthFirstPaths(Graph G,int s){
        marked=new boolean[G.V()];
        edgeTo=new int[G.V()];
        this.s=s;
        this.graph=G;
        setUpGUI();
        //dfs(G,s);
    }
    
    public void rollTheBall(){
        dfs(graph,s);
        programTerminated=true;
        textMessage=("The DFS is complete");
    }
    
    private void dfs(Graph G,int v){
        marked[v]=true;
        //first draw the edge to the vertex
        if(v!=s){
            edgeColor[edgeTo[v]][v]=Color.RED;
            edgeColor[v][edgeTo[v]]=Color.RED; 
            
//            panel.repaint();
//            pause(2);
            pause();
            
        }
        //now paint the vertex appropriately
        co_ords[v].setColor(Color.RED);
        textMessage=new String(v+ " was not visited before.so visit it ");
        //set the current vertex message
        vertexMessage=new String("Current vertex: "+v);
//        panel.repaint();
//        pause(2);
        pause();
        
//        if(v!=s){
//            edgeColor[edgeTo[v]][v]=Color.RED;
//            edgeColor[v][edgeTo[v]]=Color.RED; 
//            
//            panel.repaint();
//            pause(2);
//            
//        }
        
        for(int w:G.adj(v)){
            textMessage=new String("check "+w);
            edgeBlink(v,w,edgeColor[w][v]);

            pause();
            
            if(!marked[w])
            {
                edgeTo[w]=v;
                dfs(G,w);
            }
            else{
                textMessage=new String(w+" was visited before.So no need to visit it");
                if(edgeColor[w][v]!=Color.RED){
                    edgeColor[w][v]=Color.RED;
                    edgeColor[v][w]=Color.RED;
//                    panel.repaint();
//                    pause(1.5);
                    pause();
                        
                    edgeColor[w][v]=Color.GRAY;
                    edgeColor[v][w]=Color.GRAY;
//                    panel.repaint();
//                    pause(1.5);
                    pause();
                }
                else{
//                    panel.repaint();
//                    pause(1);
                    pause();
                }
                
            }
        }
        
        textMessage=new String("the tour from "+v+" is complete");//declare the tour complete
        //before finishing the tour,set the current vertex as v's previous vertex in path
        //null will be returned in case v is the source,so be careful
        if(v!=s)vertexMessage=new String("Current vertex :"+edgeTo[v]);
        
        //some wait codes were here
        
        
        co_ords[v].setColor(Color.GRAY);
//        panel.repaint();
//        pause(1);
        pause();
        //paint the edge that brought to it Gray
        if(v!=s){
            int w=edgeTo[v];
            edgeColor[w][v]=Color.GRAY;
            edgeColor[v][w]=Color.GRAY;
//            panel.repaint();
//            pause(2);
            pause();
        }
    }
    
    public boolean hasPathTo(int w){
        return marked[w];
    }
 
    public Iterable<Integer> pathTo(int v){
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
            //set the new stroke
            
            //draw the string message
            g.setFont(new Font("TimesRoman", Font.PLAIN,20)); 
            
            g.setColor(Color.BLACK);
            g.drawString(textMessage,(int)(TOP_X+FRAME_WIDTH*0.4),(int)(TOP_Y+ FRAME_HEIGHT*1.1));
            
            
            //draw the vertexMessage
            g.setColor(Color.BLACK);
            g.drawString(vertexMessage,(int)(TOP_X+FRAME_WIDTH*0.4),(int)(TOP_Y+FRAME_HEIGHT));
            //make the painting brush thicker
            g.setStroke(new BasicStroke(3));
            
             //draw the edges to meet their appropriate color description
           for(int i=0;i<SIZE;i++){
                for(int j=0;j<SIZE;j++){
                    if(edgeColor[i][j]==Color.WHITE)continue;
                    
                    g.setColor(edgeColor[i][j]);
                    g.drawLine(co_ords[i].getCenX(),co_ords[i].getCenY(),co_ords[j].getCenX(),co_ords[j].getCenY());
                    }
                }
            
            //then draw the circles
            for(int i=0;i<SIZE;i++){
                g.setColor(co_ords[i].getColor());
                g.fillOval(co_ords[i].get_X(),co_ords[i].get_Y(),CIRCLE_RADIUS,CIRCLE_RADIUS);
            }
            
            //then draw the vertice numbers near the top of the circles
             for(int i=0;i<SIZE;i++){
                g.setColor(Color.BLACK);
                g.drawString(Integer.toString(i),co_ords[i].get_X()-15,co_ords[i].get_Y());
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
        DepthFirstPaths path=new DepthFirstPaths(G,s);
        
    }
}