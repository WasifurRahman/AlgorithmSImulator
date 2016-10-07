public class QuickController{
    Thread code;
    Thread draw;
    Quick quick;
    
    public QuickController(){
        quick=new Quick();
        code=new Thread(new CodeThread());
        draw=new Thread(new DrawThread());
        code.start();
        draw.start();
        
    }
    class CodeThread implements Runnable{
        public void run(){
            quick.rollTheBall();
        }
    }
    
    class DrawThread implements Runnable{
        
        public void run(){
            //pause(2.0);
            while(!quick.hasProgramTerminated()){
                quick.draw();
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
       QuickController t=new QuickController();
    }
}