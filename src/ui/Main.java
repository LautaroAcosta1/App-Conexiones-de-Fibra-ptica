package ui;

import modelo.Conexion;
import modelo.Localidad;
import servicio.CalculadorCosto;
import servicio.GeneradorConexiones;
import servicio.Kruskal;
import servicio.RepositorioLocalidades;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args)
            throws Exception {

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

        // guardar en Json

        RepositorioLocalidades repo =
                new RepositorioLocalidades();

        repo.guardar(localidades);

        System.out.println(
                "Localidades guardadas."
        );

        // cargar desde el Json

        List<Localidad> cargadas =
                repo.cargar();

        System.out.println();
        System.out.println(
                "LOCALIDADES CARGADAS:"
        );

        for (Localidad l : cargadas) {
            System.out.println(l);
        }

        // crear calculador

        CalculadorCosto calculador =
                new CalculadorCosto(
                        10,
                        0.2,
                        500
                );

        // generar conexiones

        GeneradorConexiones generador =
                new GeneradorConexiones();

        List<Conexion> conexiones =
                generador.generar(
                        cargadas,
                        calculador
                );

        System.out.println();
        System.out.println(
                "CONEXIONES GENERADAS:"
        );

        for (Conexion c : conexiones) {
            System.out.println(c);
        }

        // ejecutar Kruskal

        Kruskal kruskal =
                new Kruskal();

        List<Conexion> mst =
                kruskal.mst(
                        cargadas,
                        conexiones
                );

        // mostrar MST

        System.out.println();
        System.out.println(
                "ARBOL GENERADOR MINIMO:"
        );

        double total = 0;

        for (Conexion c : mst) {

            System.out.println(c);

            total += c.getCosto();
        }

        System.out.println();
        System.out.println(
                "COSTO TOTAL: " + total
        );
    }
}