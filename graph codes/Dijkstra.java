import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


public class Dijkstra{
    private Edge[] edgeTo;//shortest edge from tree vertex
    private double[]distTo;//distTo[w]=edgeTo[w].weight();
    private boolean[] marked; // true if v on tree
    private IndexMinPQ<Double> pq;
    private EdgeWeightedGraph graph;
    private int s;//the source vertex
    //private Queue<Edge>mst;//store the mst edges;
    private ArrayList<Edge>mst=new ArrayList<Edge>();
    
    
    //declare all the variabls and objects
    private int SIZE=6;
    private double TOP_X=80;
    private double TOP_Y=50;
    private int FRAME_HEIGHT=500;
    private int FRAME_WIDTH=600;
    private int PANEL_HEIGHT=800;
    private int PANEL_WIDTH=900;
    private int CIRCLE_RADIUS=40;
    private boolean programTerminated =false;
    private boolean algoChanged=false;
    
    
    
    
    //declare all the frames,panels and object arrays
    JFrame frame=new JFrame("Dikjstra");
    DrawPanel panel=new DrawPanel();
    Co_ordinateMST[] co_ords=new Co_ordinateMST[SIZE];
    Color[][] edgeColor=new Color[SIZE][SIZE];//array to denote edge colotrs
    Co_ordinateMST[][] weightPosition=new Co_ordinateMST[SIZE][SIZE];
    //int[]treeVretices=new int[numVertices];
    String textMessage=" ";
    String mstMessage="SPT Edges: ";//string to tell the current vertex visited
    String vertexMessage="Current vertex: ";
    Co_ordinateMST point;//the point where the weight on the edge is drawn
    Box controlBox=new Box(BoxLayout.Y_AXIS);
    JButton nextButton=new JButton("NEXT");
    
    
    public Dijkstra(EdgeWeightedGraph G,int source){
        //setUpGUI();
        graph=G;
        setUpGUI();
        s=source;
        edgeTo =new Edge[G.V()];
        distTo=new double[G.V()];
        marked=new boolean[G.V()];
        //mst=new Queue<Edge>();
        for(int v=0;v<G.V();v++){
            distTo[v]=Double.POSITIVE_INFINITY;
        }
        
        pq=new IndexMinPQ<Double>(G.V());
        
        //normal edge=LIGHT_GRAY
        //the shortestWeightEdge =RED;
        //the SPT edge=black
        
        //normal vertice=LIGHT_GRAY
        //PQ vertice=ORANGE
        //SPT vertice=RED
    }
        
        public void rollTheBall(){
        
        distTo[s]=0.0;
        pq.insert(s,0.0);//initialize pq with 0,weight 0.
        //0 has been added to the pq.so paint the vertice ORANGE
        textMessage="vertex " +0 +" is in the priority queue";
        changeVertexColor(0,Color.ORANGE,1.5);
        
        while(!pq.isEmpty()){
            int v=pq.delMin();
            relax(graph,v);//add closest vertext to the tree
        }
        textMessage="the SPT is complete";
        programTerminated=true;
        
    }
    
    private void relax(EdgeWeightedGraph G,int v){
        //add v to the tree.update data structure
        marked[v]=true; 
        textMessage="Vertex of minimum distance from the source is :"+v+" which has a distance of "+distTo[v];;
        vertexBlink(v,Color.RED);
        pause();
        textMessage=" So vertex "+v+" is in the SPT";
        vertexMessage="Current Vertex :"+v;
        //now v has been added to the SPT..so paint v RED
        pause();
        //the edgeTo[v] is an SPT edge.so paint it black unless the vertex is 0
        //hence the edge is null;
        if(v!=s){
            textMessage=edgeTo[v] +"  is now an SPT edge";
            mstMessage="SPT Edges:  ";
            //add the mst Edges to the String
            mst.add(edgeTo[v]);
            for(Edge e:mst)mstMessage+=e+"  ";
            changeEdgeColor(edgeTo[v],Color.BLACK,1.5);
            pause();
            
        }
        //now check all the edges adjacent to v to find the best mst path for v;s adjacent vertices
        textMessage="Now check all the vertices adjacent to vertex "+v;
        pause();
        for(Edge e:G.adj(v)){
            //paint the edge e to yellow to show that it is being checked
            if(!mst.contains(e)){
                textMessage="vertex "+v+" checking edge "+e;
                edgeBlink(e,Color.YELLOW);
                
                pause();
            }
            int w=e.other(v);
            if(marked[w]){
                //both vertices are in the mst.so declare the edge between them ineligible
                //by painting it GRAY unless the edge is an MST edge
                if(!mst.contains(e)){
                    textMessage="vertices "+v+" "+"and "+w+" are in the SPT.So the edge "+e+" cannot be in the SPT";
                    changeEdgeColor(e,Color.GRAY,0.05);
                    pause();
                }
                
                continue; //v-w is ineligible
            }
            if(distTo[w]>distTo[v]+e.weight()){
                //Edge e is the new best connection from tree to w
                //save the previous edge and distance of the vertice
                Edge prevEdge=edgeTo[w];
                Double prevDist=distTo[w];
                //now update the vertex v's edge and dist
                edgeTo[w]=e;
                distTo[w]=distTo[v]+e.weight();
                if(pq.contains(w)){
                    //the vertex is in the PQ.so change its best edge to e
                    //first color the previous edge to LIGHT_GRAY to highlight its insignificance
                    textMessage="A better edge  " + e + " connecting "+w+" to the SPT has been found";
                    edgeBlink(e,Color.YELLOW);
                    pause();
                    textMessage="So the previous edge "+prevEdge+" is out of consideration";
                    edgeBlink(prevEdge,Color.LIGHT_GRAY);
                   // changeEdgeColor(prevEdge,Color.LIGHT_GRAY,0.5);
                    
                    //then color the new Edge to RED
                    textMessage="The new best edge connecting vertex "+w+" with the SPT is "+e;
                    changeEdgeColor(edgeTo[w],Color.RED,0.02);
                    pause();
                    textMessage="So the priority of vertex "+w+" is decreased from "+prevDist+" to "+distTo[w];
                    pause();
                    pq.decreaseKey(w,distTo[w]);
                }
                else{
                    //w is added to the pq right now.so color it ORANGE.
                    textMessage="Add vertex "+w+" to the priority queue";
                    changeVertexColor(w,Color.ORANGE,0.05);
                    pause();
                    //then the change of the best matches edge to it to RED
                    textMessage=edgeTo[w]+"  is the best edge connecting "+w+" to the source";
                    changeEdgeColor(edgeTo[w],Color.RED,0.02);
                    pause();
                    textMessage="The priority of the vertex "+w+" is "+distTo[w];
                    pause();
                    pq.insert(w,distTo[w]);
                }
            }
            else{
                
            }
            
        }
    }
    
    public Iterable<Edge> edges(){
        
        Queue<Edge> mst = new Queue<Edge>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null) {
                mst.enqueue(e);
            }
        }
        return mst;
    }
    public double weight(){
        double weight = 0.0;
        for (Edge e : edges())
            weight += e.weight();
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
        //then declare the specific connections as LIGHT_GRAY
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
        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                edgeColor[i][j]=new Color(255,255,255);//color as white
                //edgeColor[i][j]=Color.WHITE;
            }
        }
        
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
    
    public void pause(double factor){
        try{
            Thread.sleep((int)(3000*factor));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void vertexBlink(int v,Color color){
        // v is the vertex whose color is vertexBlink
        //color is the initial color of the vertex
        changeVertexColor(v,Color.GREEN,0.2);
        changeVertexColor(v,Color.BLUE,0.2);
        changeVertexColor(v,Color.GREEN,0.2);
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
        changeEdgeColor(e,color,0.1);
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
    
    
    public class DrawPanel extends JPanel{
        public void paintComponent(Graphics g2){
            //cast out g2 to be a Graphics2D object
            Graphics2D g=(Graphics2D)g2;
            //first clear out the previous window
            g.setColor(Color.WHITE);
            //first clear out the previous window
            g.fillRect(0,0,this.getWidth(),this.getHeight());
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
            
            
//            //draw the string message
            g.setColor(Color.BLACK);
            g.drawString(textMessage,(int)(TOP_X+FRAME_WIDTH*0.2),(int)(TOP_Y+ FRAME_HEIGHT*1.2));
            g.drawString(vertexMessage,(int)(TOP_X+FRAME_WIDTH*0.2),(int)(TOP_Y+ FRAME_HEIGHT*1.1));
//            
//            
//            //draw the mstMessage only if it is not null
            if(mstMessage!=null){
                g.setColor(Color.BLACK);
                g.drawString(mstMessage,(int)(TOP_X+FRAME_WIDTH*0.2),(int)(TOP_Y+FRAME_HEIGHT));
            }
//            
            // make the painting brush thicker
            
            
            //draw the edges to meet their appropriate color description
            for(int i=0;i<SIZE;i++){
                for(int j=0;j<SIZE;j++){
                    if(edgeColor[i][j]==Color.WHITE){
                        continue;
                    }
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
                    g.drawString(i+" | "+String.format("%.2f",distTo[i]),co_ords[i].get_X()-70,co_ords[i].get_Y()+5);
                }
                else{
                    g.drawString(Integer.toString(i),co_ords[i].get_X()-20,co_ords[i].get_Y()+5);
                }
            }
            //draw the mst weights
            for(Edge e:graph.edges()){
                int v=e.either();
                int w=e.other(v);
                Co_ordinateMST point=weightPosition[w][v];
                g.setColor(Color.BLACK);
                
                g.drawString(Double.toString(e.weight()),point.get_X()-10,point.get_Y()+15);
            }
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
        In in = new In("tinyMST.txt");
        EdgeWeightedGraph G;
        G = new EdgeWeightedGraph(in);
        int s=Integer.parseInt("0");
        Dijkstra mst = new Dijkstra(G,s);
    }
}