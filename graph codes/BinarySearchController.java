public class BinarySearchController{
    Thread code;
    Thread draw;
    BinarySearch bSearch;
    
    public BinarySearchController(){
        bSearch=new BinarySearch();
        code=new Thread(new CodeThread());
        draw=new Thread(new DrawThread());
        code.start();
        draw.start();
        
    }
    class CodeThread implements Runnable{
        public void run(){
            bSearch.rollTheBall();
        }
    }
    
    class DrawThread implements Runnable{
        
        public void run(){
            //pause(2.0);
            while(!bSearch.hasProgramTerminated()){
                bSearch.draw();
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
       BinarySearchController t=new BinarySearchController();
    }
}