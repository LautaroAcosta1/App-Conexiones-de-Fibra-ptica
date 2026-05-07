package test;

import org.junit.Test;
import servicio.UnionFind;

import static org.junit.Assert.assertEquals;

public class UnionFindTest {

    @Test
    public void testFindInicial() {

        UnionFind uf = new UnionFind(5);

        assertEquals(0, uf.find(0));
        assertEquals(1, uf.find(1));
        assertEquals(2, uf.find(2));
        assertEquals(3, uf.find(3));
        assertEquals(4, uf.find(4));
    }

    @Test
    public void testUnion() {

        UnionFind uf = new UnionFind(5);

        uf.union(0, 1);

        assertEquals(
                uf.find(0),
                uf.find(1)
        );
    }

    @Test
    public void testMultiplesUniones() {

        UnionFind uf = new UnionFind(5);

        uf.union(0, 1);
        uf.union(1, 2);

        assertEquals(
                uf.find(0),
                uf.find(2)
        );
    }

    @Test
    public void testConjuntosDistintos() {

        UnionFind uf = new UnionFind(5);

        uf.union(0, 1);

        assertEquals(false,
                uf.find(0) == uf.find(3));
    }
}