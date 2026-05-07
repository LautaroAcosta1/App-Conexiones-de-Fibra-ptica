package servicio;

import modelo.Conexion;
import modelo.Localidad;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kruskal {

    public List<Conexion> mst(
            List<Localidad> localidades,
            List<Conexion> conexiones) {

        // ordenar conexiones por costo
        conexiones.sort(
                Comparator.comparingDouble(
                        Conexion::getCosto
                )
        );

        List<Conexion> resultado =
                new ArrayList<>();

        // asiganar IDs numericos
        Map<Localidad, Integer> ids =
                new HashMap<>();

        for (int i = 0; i < localidades.size(); i++) {
            ids.put(localidades.get(i), i);
        }

        UnionFind uf =
                new UnionFind(localidades.size());

        for (Conexion conexion : conexiones) {

            int a =
                    ids.get(conexion.getOrigen());

            int b =
                    ids.get(conexion.getDestino());

            // si no forman ciclo...
            if (uf.find(a) != uf.find(b)) {

                resultado.add(conexion);

                uf.union(a, b);
            }
        }

        return resultado;
    }
}