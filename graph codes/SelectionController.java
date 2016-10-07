public class SelectionController{
    Thread code;
    Thread draw;
    Selection selection;
    
    public SelectionController(){
        selection=new Selection();
        code=new Thread(new CodeThread());
        draw=new Thread(new DrawThread());
        code.start();
        draw.start();
        
    }
    class CodeThread implements Runnable{
        public void run(){
            selection.rollTheBall();
        }
    }
    
    class DrawThread implements Runnable{
        
        public void run(){
            //pause(2.0);
            while(!selection.hasProgramTerminated()){
                selection.draw();
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
       SelectionController t=new SelectionController();
    }
}