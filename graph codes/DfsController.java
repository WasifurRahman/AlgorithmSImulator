public class DfsController{
    Thread code;
    Thread draw;
    DepthFirstPaths path;
    
    public DfsController(){
        Graph G=new Graph(new In("tinyCG.txt"));
        int s=Integer.parseInt("0");
        path=new DepthFirstPaths(G,s);
        code=new Thread(new CodeThread());
        draw=new Thread(new DrawThread());
        code.start();
        draw.start();
        
    }
    class CodeThread implements Runnable{
        public void run(){
            path.rollTheBall();
        }
    }
    
    class DrawThread implements Runnable{
        
        public void run(){
            //pause(2.0);
            while(!path.hasProgramTerminated()){
                path.draw();
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
        DfsController t=new DfsController();
    }
}