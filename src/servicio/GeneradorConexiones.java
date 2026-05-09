package servicio;

import modelo.Conexion;
import modelo.Localidad;

import java.util.ArrayList;
import java.util.List;

public class GeneradorConexiones {
	

    public List<Conexion> generar(
            List<Localidad> localidades,
            CalculadorCosto calculador) {

        List<Conexion> conexiones = new ArrayList<>();

        for (int i = 0; i < localidades.size(); i++) {

            for (int j = i + 1; j < localidades.size(); j++) {

                Localidad a = localidades.get(i);
                Localidad b = localidades.get(j);

                double costo =
                        calculador.calcularCosto(a, b);

                Conexion conexion =
                        new Conexion(a, b, costo);

                conexiones.add(conexion);
            }
        }

        return conexiones;
    }
}