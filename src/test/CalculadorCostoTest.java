package test;

import modelo.Localidad;
import org.junit.Test;
import servicio.CalculadorCosto;

import static org.junit.Assert.assertEquals;

public class CalculadorCostoTest {

    @Test
    public void testMismaProvinciaSinExtra() {

        Localidad a = new Localidad(
                "A",
                "Buenos Aires",
                0,
                0
        );

        Localidad b = new Localidad(
                "B",
                "Buenos Aires",
                3,
                4
        );

        CalculadorCosto calculador =
                new CalculadorCosto(10, 0.2, 500);

        double costo = calculador.calcularCosto(a, b);

        // distancia = 5
        // costo = 5 * 10 = 50

        assertEquals(50, costo, 0.001);
    }

    @Test
    public void testDistintaProvincia() {

        Localidad a = new Localidad(
                "A",
                "Buenos Aires",
                0,
                0
        );

        Localidad b = new Localidad(
                "B",
                "Cordoba",
                3,
                4
        );

        CalculadorCosto calculador =
                new CalculadorCosto(10, 0.2, 500);

        double costo = calculador.calcularCosto(a, b);

        // 50 + 500

        assertEquals(550, costo, 0.001);
    }

    @Test
    public void testDistanciaMayorA300() {

        Localidad a = new Localidad(
                "A",
                "Buenos Aires",
                0,
                0
        );

        Localidad b = new Localidad(
                "B",
                "Buenos Aires",
                0,
                400
        );

        CalculadorCosto calculador =
                new CalculadorCosto(1, 0.5, 500);

        double costo = calculador.calcularCosto(a, b);

        // distancia = 400
        // costo base = 400
        // +50%

        assertEquals(600, costo, 0.001);
    }
}