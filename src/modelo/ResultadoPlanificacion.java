package modelo;

import java.util.List;

// agrupa el resultado de planificar: las conexiones del MST y el costo total
// existe porque planificar() necesita devolver dos cosas a la vez, una alternativa para evitar errores.
// (puede eliminarse mas adelante, por ahora no tocar)
public class ResultadoPlanificacion {

    private List<Conexion> conexiones;
    private double costoTotal;

    public ResultadoPlanificacion(List<Conexion> conexiones, double costoTotal) {
        this.conexiones = conexiones;
        this.costoTotal = costoTotal;
    }

    //Las conexiones que forman el arbol generador minimo
    public List<Conexion> getConexiones() {
        return conexiones;
    }

    // la suma de los costos de todas las conexiones del MST
    public double getCostoTotal() {
        return costoTotal;
    }
}