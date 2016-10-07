public class DijkstraController{
    Thread code;
    Thread draw;
    Dijkstra spt;
    
    public DijkstraController(){
         In in = new In("tinyMST.txt");
         EdgeWeightedGraph G;
         G = new EdgeWeightedGraph(in);
         spt = new Dijkstra(G,0);
         
        
        code=new Thread(new CodeThread());
        draw=new Thread(new DrawThread());
        code.start();
        draw.start();
        
    }
    class CodeThread implements Runnable{
        public void run(){
            spt.rollTheBall();
        }
    }
    
    class DrawThread implements Runnable{
        
        public void run(){
            //pause(2.0);
            while(!spt.hasProgramTerminated()){
                spt.draw();
            }
        }
    }
    public void pause(double factor){
        
        try{
            Thread.sleep((int)(1000*factor));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public static void main(String[]args){
        DijkstraController t=new DijkstraController();
    }
}