package negocio;

public class UnionFind {
    private int[] padre;

    public UnionFind(int n) {
        padre = new int[n];
        for (int i = 0; i < n; i++) padre[i] = i;
    }

    public int find(int x) {
        if (padre[x] != x) padre[x] = find(padre[x]); // compresión de camino
        return padre[x];
    }

    public boolean union(int a, int b) {
        int raizA = find(a);
        int raizB = find(b);
        if (raizA == raizB) return false;
        padre[raizB] = raizA;
        return true;
    }
}
