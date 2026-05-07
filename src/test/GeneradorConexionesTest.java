package test;

import modelo.Conexion;
import modelo.Localidad;
import org.junit.Test;
import servicio.CalculadorCosto;
import servicio.GeneradorConexiones;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GeneradorConexionesTest {

    @Test
    public void testCantidadConexiones() {

        List<Localidad> localidades =
                new ArrayList<>();

        localidades.add(
                new Localidad("A", "BA", 0, 0));

        localidades.add(
                new Localidad("B", "BA", 0, 1));

        localidades.add(
                new Localidad("C", "BA", 0, 2));

        localidades.add(
                new Localidad("D", "BA", 0, 3));

        CalculadorCosto calculador =
                new CalculadorCosto(1, 0, 0);

        GeneradorConexiones generador =
                new GeneradorConexiones();

        List<Conexion> conexiones =
                generador.generar(
                        localidades,
                        calculador
                );

        // n(n-1)/2
        // 4*3/2 = 6

        assertEquals(6, conexiones.size());
    }

    @Test
    public void testUnaLocalidad() {

        List<Localidad> localidades =
                new ArrayList<>();

        localidades.add(
                new Localidad("A", "BA", 0, 0));

        CalculadorCosto calculador =
                new CalculadorCosto(1, 0, 0);

        GeneradorConexiones generador =
                new GeneradorConexiones();

        List<Conexion> conexiones =
                generador.generar(
                        localidades,
                        calculador
                );

        assertEquals(0, conexiones.size());
    }

    @Test
    public void testListaVacia() {

        List<Localidad> localidades =
                new ArrayList<>();

        CalculadorCosto calculador =
                new CalculadorCosto(1, 0, 0);

        GeneradorConexiones generador =
                new GeneradorConexiones();

        List<Conexion> conexiones =
                generador.generar(
                        localidades,
                        calculador
                );

        assertEquals(0, conexiones.size());
    }
}