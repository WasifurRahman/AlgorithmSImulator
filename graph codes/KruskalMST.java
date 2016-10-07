import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;


public class KruskalMST{
    private Queue<Edge>mst;
    private double weight;
    
    private boolean[] marked;
    private EdgeWeightedGraph graph;
    MinPQ<Edge>pq;
    UF uf;
   
   
    
    //declare all the variabls and objects
    private int SIZE=6;
    private double TOP_X=50;
    private double TOP_Y=50;
    private int FRAME_HEIGHT=500;
    private int FRAME_WIDTH=500;
    private int PANEL_HEIGHT=700;
    private int PANEL_WIDTH=1000;
    private int CIRCLE_RADIUS=40;
    private boolean programTerminated=false;
    private boolean algoChanged=false;
    
    
    
    //declare all the frames,panels and object arrays
    JFrame frame=new JFrame("KRUSKAL MST");
    DrawPanel panel=new DrawPanel();
    Co_ordinateMST[] co_ords=new Co_ordinateMST[SIZE];
    Color[][] edgeColor=new Color[SIZE][SIZE];//array to denote edge colotrs
    Co_ordinateMST[][] weightPosition=new Co_ordinateMST[SIZE][SIZE];
    //int[]treeVretices=new int[numVertices];
    String textMessage;
    String mstMessage="MST Edges: ";//string to tell the current vertex visited
    Co_ordinateMST point;//the point where the weight on the edge is drawn
    JButton nextButton=new JButton("NEXT");
    Box controlBox=new Box(BoxLayout.Y_AXIS);
    
    public KruskalMST(EdgeWeightedGraph G){
        
        graph=G;
        mst=new Queue<Edge>();
        pq=new MinPQ<Edge>(G.E());
        //now iterate through the edges and insert them in MinPQ
        for(Edge e:G.edges())pq.insert(e);
        uf=new UF(G.V());
        
        setUpGUI();
    }
    
    public void rollTheBall(){
        mst();
    }
    
    public void mst(){
        
        while(!pq.isEmpty() && mst.size()<graph.V()-1){
            Edge e=pq.delMin();//the minimum weighted edge is deleted
            //its two endpoints are found out
            int v=e.either();
            int w=e.other(v);
            
            textMessage=new String("The minimum weight edge chosen is: "+e);
            edgeBlink(e,edgeColor[w][v]);
            
            //paint that edge red
            edgeColor[v][w]=Color.RED;
            edgeColor[w][v]=Color.RED;
            //panel.repaint();
            pause();
            
            if(uf.connected(v,w)){
                //the edge is creating a cycle.so it will not be added.
                //so paint it back to light gray
                textMessage=new String("But "+e+" creates a cycle.So it can not be in MST");
                //panel.repaint();
                pause();
                
                edgeColor[v][w]=Color.LIGHT_GRAY;
                edgeColor[w][v]=Color.LIGHT_GRAY;
                //panel.repaint();
                pause();
                
                continue;//ignore ineligible edges
            }
            uf.union(v,w);
            mst.enqueue(e);//the edge is enqueued,so paint it to black
            edgeColor[v][w]=Color.BLACK;
            edgeColor[w][v]=Color.BLACK;
            textMessage=new String(e+" does not create a cycle.So include it in MST");
            //color the vetices red 
            
            
            co_ords[v].setColor(Color.RED);
            co_ords[w].setColor(Color.RED);
            //panel.repaint();
            pause();
            //prepare the mstMessage
            mstMessage="MST Edges are: ";
            for(Edge ed:edges()){
                mstMessage+="  "+ed+ "  ";
            }
            //panel.repaint();
            pause();
            
            
            weight+=e.weight();
        }
        //declare the algo to be complete
        textMessage="the MST is Complete";
        //panel.repaint();
        pause();
        textMessage="MST weight is: "+weight;
        //panel.repaint();
        pause();
        
        programTerminated=true;
        
    }
    
    public boolean hasProgramTerminated(){
        return programTerminated;
    }
    
    public Iterable<Edge> edges(){
        return mst;
    }
    public double weight(){
        return weight;
    }
    
    
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
        setUpCo_ordinateMSTs(co_ords);
        //perpare the connetions
        //first declare alkl connections as white
        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                edgeColor[i][j]=new Color(255,255,255);//color as white
                //edgeColor[i][j]=Color.WHITE;
            }
        }
        //then declare the specific connections as black
        setUpEdgeColor(graph);
        //now set up the positions of the edges
        setWeightArray();
        
        
    }
    //returns an appropriate point to set the text of the weight in edge
    public Co_ordinateMST setWeightPosition(Co_ordinateMST c1,Co_ordinateMST c2){
        double x=(c1.get_X()+c2.get_X())/2.0-9;//set the midpoint of the co_ordinates
        double y=(c1.get_Y()+c2.get_Y())/2.0+5;//set the midpoint of the co_ordinates and deduct 5
        return new Co_ordinateMST(x,y);
    }
    //sets up the weight array
    public void setWeightArray(){
        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                if(edgeColor[i][j]==Color.LIGHT_GRAY){
                    weightPosition[i][j]=setWeightPosition(co_ords[i],co_ords[j]);
                    weightPosition[j][i]=setWeightPosition(co_ords[i],co_ords[j]);
                    //weightPosition[j][i]=weightPosition[i][j];
                    
                }
                
            }
        }
    }
    
    public void setUpEdgeColor(EdgeWeightedGraph graph){

        for(int v=0;v<graph.V();v++){
            for(Edge e:graph.adj(v)){
                edgeColor[v][e.other(v)]=Color.LIGHT_GRAY;
            }
        }
        
    }
    
    public void setUpCo_ordinateMSTs(Co_ordinateMST[]co_ords){
        co_ords[0]=new Co_ordinateMST(TOP_X,TOP_Y);
        co_ords[1]=new Co_ordinateMST(TOP_X + FRAME_WIDTH/4,TOP_Y+FRAME_HEIGHT/2);
        co_ords[2]=new Co_ordinateMST(TOP_X+FRAME_WIDTH-2*CIRCLE_RADIUS,TOP_Y);
        co_ords[3]=new Co_ordinateMST(TOP_X+FRAME_WIDTH/2,TOP_Y+FRAME_HEIGHT*3/4);
        co_ords[4]=new Co_ordinateMST(TOP_X+FRAME_WIDTH-2*CIRCLE_RADIUS,TOP_Y+FRAME_HEIGHT-2*CIRCLE_RADIUS);
        co_ords[5]=new Co_ordinateMST(TOP_X,TOP_Y+FRAME_HEIGHT-2*CIRCLE_RADIUS);
    }
    
    public void pause(double factor){
        try{
            Thread.sleep((int)(2000*factor));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
     public void changeEdgeColor(int v,int w,Color color,double secToPause){
        edgeColor[v][w]=color;
        edgeColor[w][v]=color;
        panel.repaint();
        pause(secToPause);
    }
      
      public void changeEdgeColor(Edge e,Color color,double secToPause){
          int v=e.either();
          int w=e.other(v);
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
    
    
    public void edgeBlink(Edge e,Color color){
        // v is the vertex whose color is vertexBlink
        //color is the initial color of the vertex
        int v=e.either();
        int w=e.other(v);
        changeEdgeColor(e,Color.GREEN,0.4);
        changeEdgeColor(e,Color.BLUE,0.4);
        changeEdgeColor(e,Color.GREEN,0.4);
        changeEdgeColor(e,Color.BLUE,0.4);
        changeEdgeColor(e,Color.GREEN,0.4);
        changeEdgeColor(e,color,0.01);
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
    
    
    
    public class DrawPanel extends JPanel{
        public void paintComponent(Graphics g2){
             //cast out g2 to be a Graphics2D object
            Graphics2D g=(Graphics2D)g2;
            //first clear out the previous window
            g.setColor(Color.WHITE);
            //first clear out the previous window
            g.fillRect(0,0,this.getWidth(),this.getHeight());
            
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
         
            
            //draw the string message
            g.setColor(Color.BLACK);
            g.drawString(textMessage,(int)(TOP_X+FRAME_WIDTH*0.4),(int)(TOP_Y+ FRAME_HEIGHT*1.1));
//            
            
            //draw the mstMessage only if it is not null
            if(mstMessage!=null){
                g.setColor(Color.BLACK);
                g.drawString(mstMessage,(int)(TOP_X+FRAME_WIDTH*0.4),(int)(TOP_Y+FRAME_HEIGHT));
            }
            
           // make the painting brush thicker
            
            
             //draw the edges to meet their appropriate color description
            for(int i=0;i<SIZE;i++){
                for(int j=0;j<SIZE;j++){
                    g.setColor(edgeColor[i][j]);
                    if((edgeColor[i][j]==Color.RED)||(edgeColor[i][j]==Color.BLACK))
                        g.setStroke(new BasicStroke(4));
                    else{
                        g.setStroke(new BasicStroke(2));
                    }
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
                g.drawString(Integer.toString(i),co_ords[i].get_X()-20,co_ords[i].get_Y());
            }
             //draw the mst weights
            for(Edge e:graph.edges()){
                int v=e.either();
                int w=e.other(v);
                Co_ordinateMST point=weightPosition[w][v];
                //System.out.println(point.get_X());
                //System.out.println(point.get_Y());
                g.setColor(Color.GREEN);
                g.drawString(Double.toString(e.weight()),point.get_X()-10,point.get_Y()+15);
                //g.drawString(e.weight()+"",200,300);
            }

//           
        }
        
     }//end of inner class
    
    class NextListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            algoChanged=false;
            System.out.println("in next");
            //notify();
        }
    }
     
     public static void main(String[]args){
         In in = new In("tinyMST.txt");
         EdgeWeightedGraph G;
         G = new EdgeWeightedGraph(in);
         KruskalMST mst = new KruskalMST(G);
         
     }
}