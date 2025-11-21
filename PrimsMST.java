import java.util.*;

public class PrimsMST {

    static class Node implements Comparable<Node> {
        int vertex, weight;

        Node(int v, int w) {
            vertex = v;
            weight = w;
        }

        @Override
        public int compareTo(Node other) {
            return this.weight - other.weight;
        }
    }

    // build adjacency list from edge list
    public static List<List<Node>> buildAdjList(List<Edge> edges, int V) {
        List<List<Node>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        for (Edge e : edges) {
            adj.get(e.src).add(new Node(e.dest, e.weight));
            adj.get(e.dest).add(new Node(e.src, e.weight));
        }
        return adj;
    }

    public static int prims(List<Edge> edges, int V) {
        List<List<Node>> adj = buildAdjList(edges, V);

        boolean[] visited = new boolean[V];
        PriorityQueue<Node> pq = new PriorityQueue<>();

        pq.add(new Node(0, 0));
        int mstWeight = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            int u = cur.vertex;

            if (visited[u]) continue;
            visited[u] = true;
            mstWeight += cur.weight;

            for (Node neighbor : adj.get(u)) {
                if (!visited[neighbor.vertex]) {
                    pq.add(new Node(neighbor.vertex, neighbor.weight));
                }
            }
        }
        return mstWeight;
    }

    public static void main(String[] args) {
        int V = 10;
        int density = 40;

        List<Edge> graph = KruskalMST.generateGraph(V, density);

        long start = System.nanoTime();
        int weight = prims(graph, V);
        long end = System.nanoTime();

        System.out.println("\n--- PRIM'S MST ---");
        System.out.println("MST Weight = " + weight);
        System.out.println("Time (ms) = " + (end - start) / 1_000_000.0);
    }
}
