package servicio;

import modelo.Conexion;
import modelo.Localidad;
import modelo.ResultadoPlanificacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Planificador {

    private List<Localidad> localidades;
    private CalculadorCosto calculador;
    private RepositorioLocalidades repositorio;

    // recibe los parametros de costo para configurar el calculador desde el arranque
    public Planificador(double costoPorKm, double porcentajeExtra, double costoInterprovincial) {
        this.localidades = new ArrayList<>();
        this.repositorio = new RepositorioLocalidades();
        this.calculador = new CalculadorCosto(costoPorKm, porcentajeExtra, costoInterprovincial);
    }

    // genera todas las conexiones posibles y corre Kruskal para obtener el MST
    public ResultadoPlanificacion planificar() {
        if (localidades.size() < 2) {
            throw new IllegalStateException("Se necesitan al menos dos localidades.");
        }

        GeneradorConexiones generador = new GeneradorConexiones();
        List<Conexion> todasLasConexiones = generador.generar(localidades, calculador);

        Kruskal kruskal = new Kruskal();
        List<Conexion> mst = kruskal.mst(localidades, todasLasConexiones);

        double costoTotal = mst.stream().mapToDouble(Conexion::getCosto).sum();

        return new ResultadoPlanificacion(mst, costoTotal);
    }

    // Permite al usuario cambiar una conexion del MST por otra y recalcula el costo total
    public ResultadoPlanificacion reemplazarConexion(ResultadoPlanificacion actual,
                                                      Conexion aEliminar,
                                                      Conexion aAgregar) {
        List<Conexion> nuevas = new ArrayList<>(actual.getConexiones());
        nuevas.remove(aEliminar);
        nuevas.add(aAgregar);

        double costoTotal = nuevas.stream().mapToDouble(Conexion::getCosto).sum();

        return new ResultadoPlanificacion(nuevas, costoTotal);
    }

    // agrega una localidad a la lista en memoria
    public void agregarLocalidad(Localidad localidad) {
        localidades.add(localidad);
    }

    // Limpia la lista en memoria y sobreescribe el archivo con la lista vacia
    public void borrarLocalidades() throws IOException {
        localidades.clear();
        repositorio.guardar(localidades);
    }

    // Devuelve la lista actual de localidades
    public List<Localidad> getLocalidades() {
        return localidades;
    }

    // Persiste la lista actual en el archivo JSON
    public void guardarLocalidades() throws IOException {
        repositorio.guardar(localidades);
    }

    // Carga las localidades guardadas en el archivo JSON a la lista en memoria
    public void cargarLocalidades() throws IOException {
        List<Localidad> cargadas = repositorio.cargar();
        if (cargadas != null) {
            localidades = cargadas;
        }
    }
}