package ui;

import modelo.Conexion;
import modelo.Localidad;
import servicio.CalculadorCosto;
import servicio.GeneradorConexiones;
import servicio.Kruskal;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // crear localidades

        Localidad buenosAires =
                new Localidad(
                        "Buenos Aires",
                        "Buenos Aires",
                        0,
                        0
                );

        Localidad cordoba =
                new Localidad(
                        "Cordoba",
                        "Cordoba",
                        100,
                        200
                );

        Localidad rosario =
                new Localidad(
                        "Rosario",
                        "Santa Fe",
                        50,
                        80
                );

        Localidad mendoza =
                new Localidad(
                        "Mendoza",
                        "Mendoza",
                        300,
                        400
                );

        // lista de localidades

        List<Localidad> localidades =
                new ArrayList<>();

        localidades.add(buenosAires);
        localidades.add(cordoba);
        localidades.add(rosario);
        localidades.add(mendoza);

        // crear calculador

        CalculadorCosto calculador =
                new CalculadorCosto(
                        10,     // costo por km
                        0.2,    // 20% extra
                        500     // interprovincial
                );

        // generar conexiones

        GeneradorConexiones generador =
                new GeneradorConexiones();

        List<Conexion> conexiones =
                generador.generar(
                        localidades,
                        calculador
                );

        System.out.println(
                "CONEXIONES GENERADAS:"
        );

        for (Conexion c : conexiones) {

            System.out.println(
                    c.getOrigen().getNombre()
                            + " -> "
                            + c.getDestino().getNombre()
                            + " | costo: "
                            + c.getCosto()
            );
        }

        // ejecutar Kruskal

        Kruskal kruskal =
                new Kruskal();

        List<Conexion> mst =
                kruskal.mst(
                        localidades,
                        conexiones
                );

        // mostrar MST

        System.out.println();
        System.out.println(
                "ARBOL GENERADOR MINIMO:"
        );

        double total = 0;

        for (Conexion c : mst) {

            System.out.println(
                    c.getOrigen().getNombre()
                            + " -> "
                            + c.getDestino().getNombre()
                            + " | costo: "
                            + c.getCosto()
            );

            total += c.getCosto();
        }

        System.out.println();
        System.out.println(
                "COSTO TOTAL: " + total
        );
    }
}