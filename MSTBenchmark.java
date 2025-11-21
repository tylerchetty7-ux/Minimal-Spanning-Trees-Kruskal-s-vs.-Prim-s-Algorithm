import java.util.*;

public class MSTBenchmark {

    public static void main(String[] args) {

        int[] sizes = {20, 50, 100};
        int[] densities = {20, 40, 60, 80};

        System.out.println("\n================= MST BENCHMARK =================");
        System.out.println("V   Density   Kruskal(ms)   Prim(ms)   Same MST?");
        System.out.println("=================================================");

        for (int V : sizes) {
            for (int d : densities) {

                // generate graph
                List<Edge> graph = KruskalMST.generateGraph(V, d);

                // KRUSKAL
                long startK = System.nanoTime();
                int wK = KruskalMST.kruskal(new ArrayList<>(graph), V);
                long endK = System.nanoTime();
                double kruskalTime = (endK - startK) / 1_000_000.0;

                // PRIM
                long startP = System.nanoTime();
                int wP = PrimsMST.prims(new ArrayList<>(graph), V);
                long endP = System.nanoTime();
                double primTime = (endP - startP) / 1_000_000.0;

                boolean same = (wK == wP);

                System.out.printf(
                    "%-4d %-9d %-13.4f %-10.4f %s%n",
                    V, d, kruskalTime, primTime, same ? "YES" : "NO"
                );
            }
        }

        System.out.println("=================================================");
    }
}
