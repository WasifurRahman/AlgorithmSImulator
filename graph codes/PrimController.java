public class PrimController{
    Thread code;
    Thread draw;
    PrimMST mst;
    
    public PrimController(){
         In in = new In("tinyMST.txt");
         EdgeWeightedGraph G;
         G = new EdgeWeightedGraph(in);
         mst = new PrimMST(G);
         
        
        code=new Thread(new CodeThread());
        draw=new Thread(new DrawThread());
        code.start();
        draw.start();
        
    }
    class CodeThread implements Runnable{
        public void run(){
            mst.rollTheBall();
        }
    }
    
    class DrawThread implements Runnable{
        
        public void run(){
            //pause(2.0);
            while(!mst.hasProgramTerminated()){
                mst.draw();
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
        PrimController t=new PrimController();
    }
}