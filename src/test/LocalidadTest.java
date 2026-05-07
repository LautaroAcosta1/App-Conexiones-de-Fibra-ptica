package test;

import modelo.Localidad;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LocalidadTest {

    @Test
    public void testConstructorYGetters() {

        Localidad l = new Localidad(
                "Buenos Aires",
                "Buenos Aires",
                10,
                20
        );

        assertEquals("Buenos Aires", l.getNombre());
        assertEquals("Buenos Aires", l.getProvincia());
        assertEquals(10, l.getLatitud(), 0.001);
        assertEquals(20, l.getLongitud(), 0.001);
    }
}