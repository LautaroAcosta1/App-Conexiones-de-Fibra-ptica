package test;

import modelo.Localidad;
import org.junit.Test;
import servicio.CalculadorCosto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CalculadorCostoTest {

    @Test
    public void testMismaLocalidadCostoCero() {

        Localidad a = new Localidad(
                "Pilar",
                "Buenos Aires",
                -34.4587,
                -58.9142
        );

        CalculadorCosto calculador =
                new CalculadorCosto(10, 0.2, 500);

        double costo = calculador.calcularCosto(a, a);

        assertEquals(0, costo, 0.01);
    }

    @Test
    public void testMismaProvinciaSinExtra() {

        Localidad pilar = new Localidad(
                "Pilar",
                "Buenos Aires",
                -34.4587,
                -58.9142
        );

        Localidad sanIsidro = new Localidad(
                "San Isidro",
                "Buenos Aires",
                -34.4721,
                -58.5276
        );

        CalculadorCosto calculador =
                new CalculadorCosto(10, 0.2, 500);

        double costo =
                calculador.calcularCosto(
                        pilar,
                        sanIsidro
                );

        // ~36 km * 10

        assertEquals(360, costo, 40);
    }

    @Test
    public void testDistintaProvincia() {

        Localidad buenosAires = new Localidad(
                "Buenos Aires",
                "Buenos Aires",
                -34.6037,
                -58.3816
        );

        Localidad cordoba = new Localidad(
                "Cordoba",
                "Cordoba",
                -31.4201,
                -64.1888
        );

        CalculadorCosto calculador =
                new CalculadorCosto(10, 0.2, 500);

        double costo =
                calculador.calcularCosto(
                        buenosAires,
                        cordoba
                );

        // debe incluir costo interprovincial
        assertTrue(costo > 500);
    }

    @Test
    public void testDistanciaMayorA300() {

        Localidad buenosAires = new Localidad(
                "Buenos Aires",
                "Buenos Aires",
                -34.6037,
                -58.3816
        );

        Localidad mendoza = new Localidad(
                "Mendoza",
                "Mendoza",
                -32.8895,
                -68.8458
        );

        CalculadorCosto calculador =
                new CalculadorCosto(1, 0.5, 500);

        double costo =
                calculador.calcularCosto(
                        buenosAires,
                        mendoza
                );

        // debe aplicar el porcentaje extra
        assertTrue(costo > 1000);
    }

    @Test
    public void testSimetria() {

        Localidad a = new Localidad(
                "Pilar",
                "Buenos Aires",
                -34.4587,
                -58.9142
        );

        Localidad b = new Localidad(
                "San Isidro",
                "Buenos Aires",
                -34.4721,
                -58.5276
        );

        CalculadorCosto calculador =
                new CalculadorCosto(10, 0.2, 500);

        double costoAB =
                calculador.calcularCosto(a, b);

        double costoBA =
                calculador.calcularCosto(b, a);

        assertEquals(costoAB, costoBA, 0.01);
    }
}