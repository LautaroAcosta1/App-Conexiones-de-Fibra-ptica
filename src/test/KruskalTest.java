package test;

import modelo.Conexion;
import modelo.Localidad;
import org.junit.Test;
import servicio.Kruskal;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class KruskalTest {

    @Test
    public void testCantidadAristasMST() {

        Localidad a =
                new Localidad("A", "BA", 0, 0);

        Localidad b =
                new Localidad("B", "BA", 0, 1);

        Localidad c =
                new Localidad("C", "BA", 0, 2);

        List<Localidad> localidades =
                new ArrayList<>();

        localidades.add(a);
        localidades.add(b);
        localidades.add(c);

        List<Conexion> conexiones =
                new ArrayList<>();

        conexiones.add(
                new Conexion(a, b, 1));

        conexiones.add(
                new Conexion(b, c, 2));

        conexiones.add(
                new Conexion(a, c, 10));

        Kruskal kruskal =
                new Kruskal();

        List<Conexion> mst =
                kruskal.mst(
                        localidades,
                        conexiones
                );

        // MST de n nodos tiene n-1 aristas

        assertEquals(2, mst.size());
    }

    @Test
    public void testCostoMinimo() {

        Localidad a =
                new Localidad("A", "BA", 0, 0);

        Localidad b =
                new Localidad("B", "BA", 0, 1);

        Localidad c =
                new Localidad("C", "BA", 0, 2);

        List<Localidad> localidades =
                new ArrayList<>();

        localidades.add(a);
        localidades.add(b);
        localidades.add(c);

        List<Conexion> conexiones =
                new ArrayList<>();

        conexiones.add(
                new Conexion(a, b, 1));

        conexiones.add(
                new Conexion(b, c, 2));

        conexiones.add(
                new Conexion(a, c, 10));

        Kruskal kruskal =
                new Kruskal();

        List<Conexion> mst =
                kruskal.mst(
                        localidades,
                        conexiones
                );

        double total = 0;

        for (Conexion c1 : mst) {
            total += c1.getCosto();
        }

        assertEquals(3, total, 0.001);
    }
}