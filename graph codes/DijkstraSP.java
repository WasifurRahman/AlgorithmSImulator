import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class DijkstraSP{
    
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;
    private EdgeWeightedDigraph graph;
    private ArrayList<DirectedEdge>sp=new ArrayList<DirectedEdge>();
    
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
    JFrame frame=new JFrame("DijkstraSp");
    DrawPanel panel=new DrawPanel();
    Co_ordinate[] co_ords=new Co_ordinate[SIZE];
    Color[][] edgeColor=new Color[SIZE][SIZE];//array to denote edge colotrs
    Co_ordinate[][] weightPosition=new Co_ordinate[SIZE][SIZE];
    //int[]treeVretices=new int[numVertices];
    String textMessage;
    String vertexMessage="VertexChecked: ";
    String edgeMessage;
    Co_ordinate point;//the point where the weight on the edge is drawn
    
    
    public DijkstraSP(EdgeWeightedDigraph G,int s){
        setUpGUI();
        edgeTo=new DirectedEdge[G.V()];
        distTo=new double[G.V()];
        pq=new IndexMinPQ<Double>(G.V());
        graph=G;
        
        for(int v=0;v<G.V();v++){
            distTo[v]=Double.POSITIVE_INFINITY;
        }
        distTo[s]=0.0;
        pq.insert(s,0.0);
        while(!pq.isEmpty()){
            int v=pq.delMin();
            relax(G,v);
        }
        
    }
    
    
    
    private void relax(EdgeWeightedDigraph G,int v){//vertex relaxation
        for(DirectedEdge e: G.adj(v)){
            int w=e.to();
            if(distTo[w]>distTo[v]+e.weight()){
                distTo[w]=distTo[v]+e.weight();
                edgeTo[w]=e;
                
                if(pq.contains(w))pq.decreaseKey(w,distTo[w]);
                else pq.insert(w,distTo[w]);
            }
        }
    }
    
    public double distTo(int v){
        return distTo[v];
    }
    public boolean hasPathTo(int v){
        return distTo[v]<Double.POSITIVE_INFINITY;
    }
    public Iterable<DirectedEdge>pathTo(int v){
        if(!hasPathTo(v))return null;
        Stack<DirectedEdge> path=new Stack<DirectedEdge>();
        for(DirectedEdge e=edgeTo[v];e!=null;e=edgeTo[e.from()]){
            path.push(e);
        }
        return path;
    }
    
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
                edgeColor[i][j]=new Color(255,255,255);//color as white
                //edgeColor[i][j]=Color.WHITE;
            }
        }
        //then declare the specific connections as LIGHT_GRAY
        setUpEdgeColor();
        //now set up the positions of the edges
        setWeightArray();
        
        
    }
    //returns an appropriate point to set the text of the weight in edge
    public Co_ordinate setWeightPosition(Co_ordinate c1,Co_ordinate c2){
        double x=(c1.get_X()+c2.get_X())/2.0+2;//set the midpoint of the co_ordinates
        double y=(c1.get_Y()+c2.get_Y())/2.0-5;//set the midpoint of the co_ordinates and deduct 5
        return new Co_ordinate(x,y);
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
    
   public void setUpEdgeColor(){
        edgeColor[0][1]=Color.LIGHT_GRAY;
        edgeColor[1][0]=Color.LIGHT_GRAY;
        edgeColor[0][2]=Color.LIGHT_GRAY;
        edgeColor[2][0]=Color.LIGHT_GRAY;
        edgeColor[0][5]=Color.LIGHT_GRAY;
        edgeColor[5][0]=Color.LIGHT_GRAY;
        edgeColor[1][2]=Color.LIGHT_GRAY;
        edgeColor[2][1]=Color.LIGHT_GRAY;
        edgeColor[2][4]=Color.LIGHT_GRAY;
        edgeColor[4][2]=Color.LIGHT_GRAY;
        edgeColor[3][2]=Color.LIGHT_GRAY;
        edgeColor[2][3]=Color.LIGHT_GRAY;
        edgeColor[3][4]=Color.LIGHT_GRAY;
        edgeColor[4][3]=Color.LIGHT_GRAY;
        edgeColor[5][3]=Color.LIGHT_GRAY;
        edgeColor[3][5]=Color.LIGHT_GRAY;
        
    }
    
    public void setUpCo_ordinates(Co_ordinate[]co_ords){
        co_ords[0]=new Co_ordinate(TOP_X,TOP_Y);
        co_ords[1]=new Co_ordinate(TOP_X + FRAME_WIDTH/4,TOP_Y+FRAME_HEIGHT/2);
        co_ords[2]=new Co_ordinate(TOP_X+FRAME_WIDTH-2*CIRCLE_RADIUS,TOP_Y);
        co_ords[3]=new Co_ordinate(TOP_X+FRAME_WIDTH/2,TOP_Y+FRAME_HEIGHT*3/4);
        co_ords[4]=new Co_ordinate(TOP_X+FRAME_WIDTH-2*CIRCLE_RADIUS,TOP_Y+FRAME_HEIGHT-2*CIRCLE_RADIUS);
        co_ords[5]=new Co_ordinate(TOP_X,TOP_Y+FRAME_HEIGHT-2*CIRCLE_RADIUS);
    }
    
    public void changeEdgeColor(int v,int w,Color color,double secToPause){
        edgeColor[v][w]=color;
        //edgeColor[w][v]=color;
        panel.repaint();
        pause(secToPause);
    }
    
    public void changeEdgeColor(DirectedEdge e,Color color,double secToPause){
        int v=e.from();
        int w=e.to();
        edgeColor[v][w]=color;
        //edgeColor[w][v]=color;
        panel.repaint();
        pause(secToPause);
    }
    public void changeVertexColor(int v,Color color,double secToPause){
        co_ords[v].setColor(color);
        panel.repaint();
        pause(secToPause);
    }
    
    public void pause(double factor){
        try{
            Thread.sleep((int)(3000*factor));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    public class DrawPanel extends JPanel{
        public void paintComponent(Graphics g2){
            //cast out g2 to be a Graphics2D object
            Graphics2D g=(Graphics2D)g2;
            //first clear out the previous window
            g.setColor(Color.WHITE);
            //first clear out the previous window
            g.fillRect(0,0,this.getWidth(),this.getHeight());
            
            
//            //draw the string message
//            g.setColor(Color.BLACK);
//            g.drawString(textMessage,(int)(TOP_X+FRAME_WIDTH*0.4),(int)(TOP_Y+ FRAME_HEIGHT*1.1));
////            
////            
////            //draw the mstMessage only if it is not null
//            if(mstMessage!=null){
//                g.setColor(Color.BLACK);
//                g.drawString(mstMessage,(int)(TOP_X+FRAME_WIDTH*0.4),(int)(TOP_Y+FRAME_HEIGHT));
//            }
//            
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
            //and if the vertice is on the pq or in the mst,hence ORANGE or RED,put its distance beside it
            for(int i=0;i<SIZE;i++){
                g.setColor(Color.BLACK);
                if((co_ords[i].getColor()==Color.ORANGE)||(co_ords[i].getColor()==Color.RED)){//if the vertex is on the pq or mst
                    g.drawString(i+" | "+distTo[i],co_ords[i].get_X(),co_ords[i].get_Y());
                }
                else{
                    g.drawString(Integer.toString(i),co_ords[i].get_X(),co_ords[i].get_Y());
                }
            }
            //draw the weights
            for(DirectedEdge e:graph.edges()){
                int v=e.from();
                int w=e.to();
                Co_ordinate point=weightPosition[w][v];
                g.setColor(Color.BLACK);
                g.drawString(Double.toString(e.weight()),point.get_X(),point.get_Y());
                
            }
            
//           
        }
        
    }//
    
    public static void main(String[] args)
    {
        In in = new In("tinyMST.txt");
        EdgeWeightedDigraph G;
        G = new EdgeWeightedDigraph(in);
        int s = Integer.parseInt(args[0]);
        DijkstraSP sp = new DijkstraSP(G, s);
        for (int t = 0; t < G.V(); t++)
        {
            StdOut.print(s + " to " + t);
            StdOut.printf(" (%4.2f): ", sp.distTo(t));
            if (sp.hasPathTo(t)){
                for (DirectedEdge e : sp.pathTo(t))
                    StdOut.print(e + " ");
                StdOut.println();
            }
        }
    }
}