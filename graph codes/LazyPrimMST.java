import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class LazyPrimMST{
    private boolean[] marked;
    private Queue<Edge>mst;
    private MinPQ<Edge> pq;
    private double weight;
    
    private EdgeWeightedGraph graph;
    
    
    
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
    JFrame frame=new JFrame("PRIM MST");
    DrawPanel panel=new DrawPanel();
    Co_ordinateMST[] co_ords=new Co_ordinateMST[SIZE];
    Color[][] edgeColor=new Color[SIZE][SIZE];//array to denote edge colotrs
    Co_ordinateMST[][] weightPosition=new Co_ordinateMST[SIZE][SIZE];
    //int[]treeVretices=new int[numVertices];
    String textMessage;
    String mstMessage="MST Edges: ";//string to tell the current vertex visited
    Co_ordinateMST point;//the point where the weight on the edge is drawn
    
    
    public LazyPrimMST(EdgeWeightedGraph G){
        setUpGUI();
        pq=new MinPQ<Edge>();
        marked=new boolean[G.V()];
        mst=new Queue<Edge>();
        graph=G;
        //at first start by visiting 0.
        visit(G,0);
        while(!pq.isEmpty()){
            Edge e=pq.delMin();
            int v=e.either();
            int w=e.other(v);
            //change the color of the edge e to DARK_GRAY to show that it is considered for being and MST edge
            //changeEdgeColor(v,w,Color.DARK_GRAY,1.5);
            
            if(marked[v] && marked[w]){
                //both endpoints are in MST.so it cannot be an MST edge.mark it to the default LIGHT_GRAY
                //changeEdgeColor(v,w,Color.LIGHT_GRAY,1.5);
                continue;
            }
            mst.enqueue(e);//edge has been added to the MST.so color it BLACK
            changeEdgeColor(v,w,Color.BLACK,1.5);
            
            weight+=e.weight();
            if(!marked[v])visit(G,v);
            if(!marked[w])visit(G,w);
        }
    }
    
    private void visit(EdgeWeightedGraph G,int v){
        marked[v]=true;
        //the verice v has been added to the MST.so paint it RED
        co_ords[v].setColor(Color.RED);
        panel.repaint();
        pause(1.5);
        //now all the edges not already in PQ but adjacent to v is added in PQ.so paint them RED.
        for(Edge e:G.adj(v)){
            int x=e.either();
            int y=e.other(v);
            
            //changeEdgeColor(x,y,Color.ORANGE,1.5);
         
            //if the edge is not in PQ already,paint it RED
            if(!marked[e.other(v)]){
                
                pq.insert(e);
                changeEdgeColor(x,y,Color.RED,1.5);
               
            }
            
            
        }
    }
    public Iterable<Edge> edges(){
        return mst;
    }
    
    public double weight(){
        return weight;
    }
    
    public void setUpGUI(){
        //sets up the frame and panel
        frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        frame.getContentPane().add(BorderLayout.CENTER,panel);
        
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
        setUpEdgeColor();
        //now set up the positions of the edges
        setWeightArray();
        
        
    }
    //returns an appropriate point to set the text of the weight in edge
    public Co_ordinateMST setWeightPosition(Co_ordinateMST c1,Co_ordinateMST c2){
        double x=(c1.get_X()+c2.get_X())/2.0+2;//set the midpoint of the co_ordinates
        double y=(c1.get_Y()+c2.get_Y())/2.0-5;//set the midpoint of the co_ordinates and deduct 5
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
//            
//            //draw the mstMessage only if it is not null
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
            for(int i=0;i<SIZE;i++){
                g.setColor(Color.BLACK);
                g.drawString(Integer.toString(i),co_ords[i].get_X(),co_ords[i].get_Y());
            }
            //draw the mst weights
            for(Edge e:graph.edges()){
                int v=e.either();
                int w=e.other(v);
                Co_ordinateMST point=weightPosition[w][v];
                //System.out.println(point.get_X());
                //System.out.println(point.get_Y());
                g.setColor(Color.BLACK);
                g.drawString(Double.toString(e.weight()),point.get_X(),point.get_Y());
                //g.drawString(e.weight()+"",200,300);
            }
            
//           
        }
        
    }//end of inner class
    
    public static void main(String[]args){
        In in = new In("tinyMST.txt");
        EdgeWeightedGraph G;
        G = new EdgeWeightedGraph(in);
        LazyPrimMST mst = new LazyPrimMST(G);
        
    }
}