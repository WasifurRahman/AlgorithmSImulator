import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class StartTheShow{
    private final static int BinarySearch=1;
    private final static int SelectionSort=2;
    private final static int InsertionSort=3;
    private final static int MergeSort=4;
    private final static int QuickSort=5;
    private final static int BFS=6;
    private final static int DFS=7;
    private final static int Kruskal=8;
    private final static int Prim=9;
    private final static int Dijkstra=10;
    private final static int stop=11;
    
    private static int currentSim;
    private boolean startNewSim=false;
    private boolean showMoreSim=true;
    
    JLabel imageLabel=new JLabel();
    Image image;
    JFrame frame=new JFrame();
    JFrame helperFrame=new JFrame();
    
    JButton bSearchButton;
    JButton selectionButton;
    JButton insertionButton;
    JButton mergeButton;
    JButton quickButton;
    JButton bfsButton;
    JButton dfsButton;
    JButton primButton;
    JButton kruskalButton;
    JButton dijkstraButton;
    JButton stopButton;
    
    
    
    public StartTheShow(){
        setUpGUI();
        startSim();
    }
    
    
    private void setUpGUI(){
        frame=new JFrame("COMMON ALGORITHM SIMULATION");
        frame.setSize(1000,1200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        helperFrame.setSize(1500,1500);
        helperFrame.setVisible(true);
        helperFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        helperFrame.getContentPane().add(BorderLayout.CENTER,imageLabel);
        
        
        Box optionBox=new Box(BoxLayout.Y_AXIS);
        frame.getContentPane().add(BorderLayout.CENTER,optionBox);
        
        bSearchButton=new JButton("Binary Search");
        bSearchButton.setFont(new Font("Serif", Font.PLAIN, 20));
        bSearchButton.addActionListener(new BinarySearchListener());
        optionBox.add(bSearchButton);
        
        
        selectionButton=new JButton("Selection Sort");
        selectionButton.setFont(new Font("Serif", Font.PLAIN, 20));
        selectionButton.addActionListener(new SelectionListener());
        optionBox.add(selectionButton);
        
        insertionButton=new JButton("Insertion Sort");
        insertionButton.setFont(new Font("Serif", Font.PLAIN, 20));
        insertionButton.addActionListener(new InsertionListener());
        optionBox.add(insertionButton);
        
        mergeButton=new JButton("Merge Sort");
        mergeButton.setFont(new Font("Serif", Font.PLAIN, 20));
        mergeButton.addActionListener(new MergeListener());
        optionBox.add(mergeButton);
        
        
        quickButton=new JButton("Quick Sort");
        quickButton.setFont(new Font("Serif", Font.PLAIN, 20));
        quickButton.addActionListener(new QuickListener());
        optionBox.add(quickButton);
        
        bfsButton=new JButton("Breadth First Search");
        bfsButton.setFont(new Font("Serif", Font.PLAIN, 20));
        bfsButton.addActionListener(new BFSListener());
        optionBox.add(bfsButton);
        
        dfsButton=new JButton("Depth First Search");
        dfsButton.setFont(new Font("Serif", Font.PLAIN, 20));
        dfsButton.addActionListener(new DFSListener());
        optionBox.add(dfsButton);
        
        
        kruskalButton=new JButton("Kruskal MST");
        kruskalButton.setFont(new Font("Serif", Font.PLAIN, 20));
        kruskalButton.addActionListener(new KruskalListener());
        optionBox.add(kruskalButton);
        
        
        primButton=new JButton("Prim MST");
        primButton.setFont(new Font("Serif", Font.PLAIN, 20));
        primButton.addActionListener(new PrimListener());
        optionBox.add(primButton);
        
        
        dijkstraButton=new JButton("Dijkstra SP");
        dijkstraButton.setFont(new Font("Serif", Font.PLAIN, 20));
        dijkstraButton.addActionListener(new DijkstraListener());
        optionBox.add(dijkstraButton);
        
        
        stopButton=new JButton("STOP");
        stopButton.setFont(new Font("Serif", Font.PLAIN, 20));
        stopButton.addActionListener(new StopListener());
        optionBox.add(stopButton);
        
        frame.repaint();
        
    }
    
    private void startSim(){
        while(showMoreSim){
            
            if(!startNewSim)continue;
            
            if(currentSim==BinarySearch){
                System.out.println("j");
                BinarySearchController t=new BinarySearchController();
                startNewSim=false;
            }
            
            else if(currentSim==SelectionSort){
                SelectionController t=new SelectionController();
                startNewSim=false;
            }
            
            else if(currentSim==InsertionSort){
                InsertionController t=new InsertionController();
                startNewSim=false;
            }
            
            else if(currentSim==MergeSort){
                MergeController t=new MergeController();
                startNewSim=false;
            }
            
            else if(currentSim==QuickSort){
                QuickController t=new QuickController();
                startNewSim=false;
            }
            
            else if(currentSim==BFS){
                BfsController t=new BfsController();
                startNewSim=false;
            }
            
            else if(currentSim==DFS){
                DfsController t=new DfsController();
                startNewSim=false;
            }
            
            else if(currentSim==Kruskal){
                KruskalController t=new KruskalController();
                startNewSim=false;
            }
            
            else if(currentSim==Prim){
                PrimController t=new PrimController();
                startNewSim=false;
            }
            
            else if(currentSim==Dijkstra){
                DijkstraController t=new DijkstraController();
                startNewSim=false;
            }
             else if(currentSim==stop){
                
                showMoreSim=false;
            }
        }
    }
    
     private void loadImage(String imageName){
        try{
            image = ImageIO.read(new File("E:\\project cse\\new Challenge\\Images\\"+imageName));
            imageLabel = new JLabel(new ImageIcon(image));
            helperFrame.getContentPane().add(imageLabel,BorderLayout.CENTER);
        }catch(Exception e){
        }
    }
    
    
    class BinarySearchListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            loadImage("Binary Search.PNG");
            helperFrame.repaint();
            
            BinarySearchController t=new BinarySearchController();
            currentSim=BinarySearch;
            startNewSim=true;
        }
    }
    class SelectionListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            loadImage("Selection sort.PNG");
            helperFrame.repaint();
            SelectionController t=new SelectionController();
            currentSim=SelectionSort;
            startNewSim=true;
        }
    }
    class InsertionListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            loadImage("Insertion sort.PNG");
            helperFrame.repaint();
            InsertionController t=new InsertionController();
            currentSim=InsertionSort;
            startNewSim=true;
        }
    }
    class MergeListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            loadImage("Merge Sort.PNG");
            helperFrame.repaint();
            MergeController t=new MergeController();
            currentSim=MergeSort;
            startNewSim=true;
        }
    }
    class QuickListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            loadImage("Quick Sort.PNG");
            helperFrame.repaint();
            QuickController t=new QuickController();
            currentSim=QuickSort;
            startNewSim=true;
        }
    }
    class BFSListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            loadImage("BFS.PNG");
            helperFrame.repaint();
            BfsController t=new BfsController();
            currentSim=BFS;
            startNewSim=true;
        }
    }
    class DFSListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            loadImage("DFS.PNG");
            helperFrame.repaint();
            DfsController t=new DfsController();
            currentSim=DFS;
            startNewSim=true;
        }
    }
    class KruskalListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            loadImage("Kruskal's Algorithm.PNG");
            helperFrame.repaint();
            KruskalController t=new KruskalController();
            currentSim=Kruskal;
            startNewSim=true;
        }
    }
    class PrimListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            loadImage("Prim's Algorithm.PNG");
            helperFrame.repaint();
            PrimController t=new PrimController();
            currentSim=Prim;
            startNewSim=true;
        }
    }
    class DijkstraListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            loadImage("Dijkstra's algorithm.PNG");
            helperFrame.repaint();
            DijkstraController t=new DijkstraController();
            currentSim=Dijkstra;
            startNewSim=true;
            //System.out.println("g");
        }
    }
    
    class StopListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            showMoreSim=false;
       
        }
    }
    
    public static void main(String[]args){
        StartTheShow t=new StartTheShow();
    }
    
}