public class CC{
    private boolean[] marked;
    private int[]id;
    private int count;
    
    public CC(Graph G){
        marked=new boolean[G.V()];
        id=new int[G.V()];
        for(int s=0;s<G.V();s++){
            if(!marked[s]){
                dfs(G,s);
                count++;
            }
        }
    }
    private void dfs(Graph G,int v){
        marked[v]=true;
        id[v]=count;
        for(int w:G.adj(v)){
            if(!marked[w])dfs(G,w);
        }
    }
    
    public boolean connected(int w,int v){
        return id[v]==id[w];
    }
    
    public boolean marked(int w){
        return marked[w];
    }
    public int count(){
        return count;
    }
}