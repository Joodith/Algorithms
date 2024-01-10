package com.graph.DisjointSet;
import java.util.*;
class Node{
    int u,v,weight;
    Node(int u,int v,int weight){
        this.u=u;
        this.v=v;
        this.weight=weight;
    }
}

class DisjointSetKruskal{
    int[] parent;
    int[] rank;
    int V;
    DisjointSetKruskal(int V){
        this.V=V;
        parent=new int[V+1];
        rank=new int[V+1];
        initialiseDisjointSet();
    }
    void initialiseDisjointSet(){
        for(int i=1;i<=V;i++){
            parent[i]=i;
            rank[i]=1;
        }
    }

    int findUltimateParent(int vertex){
        if(parent[vertex]!=vertex){
            parent[vertex]=findUltimateParent(parent[vertex]);
        }

        return parent[vertex];
    }

    boolean union(int u,int v){
        int parU=findUltimateParent(u);
        int parV=findUltimateParent(v);
        if(parU==parV) return false;
        if(rank[parU]>rank[parV]){
            parent[parV]=parU;

        }
        else if(rank[parV]>rank[parU]){
            parent[parU]=parV;

        }
        else{
            parent[parV]=parU;
            rank[parU]+=1;
        }
        return true;

    }


}
class Algorithm {
    public static int kruskalMST(int n,int [][]edges) {
        PriorityQueue<Node> queue=new PriorityQueue<>((x,y)->x.weight-y.weight);
        int minWeight=0;
        DisjointSetKruskal disjointSet=new DisjointSetKruskal(n);
        int r=edges.length;
        int c=edges[0].length;
        for(int i=0;i<r;i++){

            Node node=new Node(edges[i][0],edges[i][1],edges[i][2]);
            queue.add(node);

        }

        while(!queue.isEmpty()){
            Node node=queue.peek();
            queue.remove();
            if(disjointSet.union(node.u,node.v)){
                minWeight+=node.weight;
            }
        }
        return minWeight;

    }
}
public class KruskalAlgorithm {
    public static void main(String[] args){
       int n=5;
       int m=6;
       int [][] edges={{1,2,6},{2,3,5},{3,4,4},{1,4,1},{1,3,2},{3,5,3}};
       int minSpanningTreeWt=Algorithm.kruskalMST(n,edges);
        System.out.println("Minimum spanning tree weight : "+minSpanningTreeWt);
    }
}
