public class InsertionController{
    Thread code;
    Thread draw;
    Insertion insertion;
    
    public InsertionController(){
        insertion=new Insertion();
        code=new Thread(new CodeThread());
        draw=new Thread(new DrawThread());
        code.start();
        draw.start();
        
    }
    class CodeThread implements Runnable{
        public void run(){
            insertion.rollTheBall();
        }
    }
    
    class DrawThread implements Runnable{
        
        public void run(){
            //pause(2.0);
            while(!insertion.hasProgramTerminated()){
                insertion.draw();
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
       InsertionController t=new InsertionController();
    }
}