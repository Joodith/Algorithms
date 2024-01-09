package com.graph.FlowAlg;

import java.util.ArrayList;
import java.util.List;

public class FordFulkersonDFS {
        private static class Edge {
            public int from, to;
            public Edge residual;
            public long flow;
            public final long capacity;

            Edge(int from, int to, long capacity) {
                this.from = from;
                this.to = to;
                this.capacity = capacity;
            }

            public boolean isResidual() {
                return capacity == 0;
            }

            public long remainingCapacity() {
                return capacity - flow;
            }

            public void augment(long bottleNeck) {
                flow += bottleNeck;
                residual.flow -= bottleNeck;
            }

            public String toString(int s, int t) {
                String u = (from == s) ? "s" : (from == t) ? "t" : String.valueOf(from);
                String v = (to == t) ? "t" : (to == s) ? "s" : String.valueOf(to);
                return String.format("Edge %s->%s | flow = %3d | capacity = %3d | isResidual = %s", u, v, flow, capacity, isResidual());
            }

        }

        public static abstract class NetworkFlowSolverBase{
            static final long INF=Long.MAX_VALUE/2;

            //n=no.of nodes  s=source t=sink/target
            final int n,s,t;
            protected int visitedTokens=1;
            protected int[]visited;
            protected boolean solved=false;
            protected long maxFlow;
            protected List<Edge>[] graph;
            NetworkFlowSolverBase(int n,int s,int t){
                this.n=n;
                this.s=s;
                this.t=t;
                initializeEmptyFlowGraph();
                visited=new int[n];
            }

            private void initializeEmptyFlowGraph() {
                graph=new List[n];
                for(int i=0;i<n;i++){
                    graph[i]=new ArrayList<Edge>();
                }
            }

            public void addEdge(int from,int to,long capacity){
                if(capacity<=0)
                    throw new IllegalArgumentException("Capacity must be greater than 0");
                Edge e1=new Edge(from,to,capacity);
                Edge e2=new Edge(to,from,0);
                e1.residual=e2;
                e2.residual=e1;
                graph[from].add(e1);
                graph[to].add(e2);
            }

            public List<Edge>[] getGraph(){
                execute();
                return graph;
            }


            public long getMaxFlow(){
                execute();
                return maxFlow;
            }

            private void execute() {
                if(solved) return;
                solved=true;
                solve();
            }

            public abstract void solve();

        }
        public static class FordFulkerSonDfsSolver extends NetworkFlowSolverBase{
            public FordFulkerSonDfsSolver(int n,int s,int t){
                super(n,s,t);
            }

            @Override
            public void solve() {
                for(long f=dfs(s,INF);f!=0;f=dfs(s,INF)){
                    visitedTokens+=1;
                    maxFlow+=f;
                }
            }

            private long dfs(int node,long flow){
                if(node==t) return flow;
                visited[node]=visitedTokens;
                List<Edge> edges=graph[node];
                for(Edge edge:edges){
                    if(edge.remainingCapacity()>0 && visited[edge.to]!=visitedTokens){
                        long bottleNeck=dfs(edge.to,Math.min(flow,edge.remainingCapacity()));
                        if(bottleNeck>0){
                            edge.augment(bottleNeck);
                            return bottleNeck;
                        }
                    }
                }
                return 0;
            }

        }
        public static void main(String []args){
            int n=12;
            int s=n-2;
            int t=n-1;
            NetworkFlowSolverBase solver=new FordFulkerSonDfsSolver(n,s,t);

            solver.addEdge(s,0,10);
            solver.addEdge(s,1,5);
            solver.addEdge(s,2,10);

            solver.addEdge(0,3,10);
            solver.addEdge(1,2,10);
            solver.addEdge(2,5,15);
            solver.addEdge(3,1,2);
            solver.addEdge(4,1,15);
            solver.addEdge(4,3,3);
            solver.addEdge(5,4,4);
            solver.addEdge(3,6,15);
            solver.addEdge(6,7,10);
            solver.addEdge(7,4,10);
            solver.addEdge(7,5,7);
            solver.addEdge(5,8,10);

            solver.addEdge(6,t,15);
            solver.addEdge(8,t,10);

            System.out.printf("Maximum flow : %d\n",solver.getMaxFlow());
            List<Edge>[] resultGraph= solver.getGraph();
            for(List<Edge> edges:resultGraph){
                for(Edge e:edges){
                    System.out.println(e.toString(s,t));
                }
            }

        }

    }


