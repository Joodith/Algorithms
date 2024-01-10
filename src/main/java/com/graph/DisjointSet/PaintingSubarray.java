package com.graph.DisjointSet;

class DisjointSet{
    int[] parent;
    int V;
    DisjointSet(int V){
        this.V=V;
        this.parent=new int[V+1];
        initialise();
    }

    private void initialise() {
        for(int i=1;i<=V;i++){
            parent[i]=i;
        }
    }
    int findUltimateParent(int vertex){
        if(vertex>V){
            return vertex;
        }
        if(parent[vertex]!=vertex){
            parent[vertex]=findUltimateParent(parent[vertex]);
        }
        return parent[vertex];
    }
}
public class PaintingSubarray {
    private static int[] findFinalColoring(int n, int[][] queries) {
        DisjointSet disjointSet=new DisjointSet(n);
        int[] finalColor=new int[n+1];
        int m= queries.length;
        for(int i=m-1;i>=0;i--){
            int l=queries[i][0];
            int r=queries[i][1];
            int color=queries[i][2];
            int par=disjointSet.findUltimateParent(l);
            while (par<=r){
                finalColor[par]=color;
                disjointSet.parent[par]=par+1;
                par= disjointSet.findUltimateParent(par);
            }
        }
        return finalColor;
    }
    public static void main(String[] args){
        int n=5;
        int [][]Q={{1,4,1},{3,5,2},{2,4,3}};
        int[] res=findFinalColoring(n,Q);
        System.out.println("Finalised color of blocks");
        for(int i=1;i<=n;i++){
            System.out.printf("%d: %d\n",i,res[i]);
        }
    }

}
