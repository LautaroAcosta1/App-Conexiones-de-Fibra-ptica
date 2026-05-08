package test;

import modelo.Conexion;
import modelo.Localidad;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConexionTest {

    @Test
    public void testConstructorYGetters() {

        Localidad a = new Localidad(
                "Buenos Aires",
                "Buenos Aires",
                10,
                20
        );

        Localidad b = new Localidad(
                "Cordoba",
                "Cordoba", 30,
                40
        );

        Conexion conexion = new Conexion(a, b, 1500);

        assertEquals(a, conexion.getOrigen());
        assertEquals(b, conexion.getDestino());
        assertEquals(1500, conexion.getCosto(), 0.001);
    }
}