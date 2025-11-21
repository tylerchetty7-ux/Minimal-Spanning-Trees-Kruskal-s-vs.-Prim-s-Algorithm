import java.util.*;

class Edge implements Comparable<Edge> {
    int src, dest, weight;

    public Edge(int s, int d, int w) {
        src = s;
        dest = d;
        weight = w;
    }

    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

class UnionFind {
    int[] parent, rank;

    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) parent[i] = i;
    }

    int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]);
        return parent[x];
    }

    void union(int x, int y) {
        int rx = find(x), ry = find(y);
        if (rx != ry) {
            if (rank[rx] < rank[ry])
                parent[rx] = ry;
            else if (rank[rx] > rank[ry])
                parent[ry] = rx;
            else {
                parent[ry] = rx;
                rank[rx]++;
            }
        }
    }
}

public class KruskalMST {

    // Generate random graph
    public static List<Edge> generateGraph(int V, int densityPercent) {
        Random rand = new Random();
        List<Edge> edges = new ArrayList<>();

        int maxEdges = V * (V - 1) / 2;
        int targetEdges = maxEdges * densityPercent / 100;

        Set<String> used = new HashSet<>();

        while (targetEdges > 0) {
            int u = rand.nextInt(V);
            int v = rand.nextInt(V);
            if (u == v) continue;

            String key = Math.min(u, v) + "-" + Math.max(u, v);
            if (used.contains(key)) continue;

            used.add(key);

            int w = rand.nextInt(50) + 1;
            edges.add(new Edge(u, v, w));

            targetEdges--;
        }
        return edges;
    }

    public static int kruskal(List<Edge> edges, int V) {
        Collections.sort(edges);
        UnionFind uf = new UnionFind(V);

        int mstWeight = 0;
        int count = 0;

        for (Edge e : edges) {
            int x = uf.find(e.src);
            int y = uf.find(e.dest);

            if (x != y) {
                uf.union(x, y);
                mstWeight += e.weight;
                count++;
                if (count == V - 1) break;
            }
        }
        return mstWeight;
    }

    public static void main(String[] args) {
        int V = 10;
        int density = 40;

        List<Edge> graph = generateGraph(V, density);

        long start = System.nanoTime();
        int weight = kruskal(graph, V);
        long end = System.nanoTime();

        System.out.println("\n--- KRUSKAL MST ---");
        System.out.println("MST Weight = " + weight);
        System.out.println("Time (ms) = " + (end - start) / 1_000_000.0);
    }
}
