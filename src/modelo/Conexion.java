package modelo;

public class Conexion {

    private Localidad origen;
    private Localidad destino;
    private double costo;

    public Conexion(Localidad origen,
                    Localidad destino,
                    double costo) {

        this.origen = origen;
        this.destino = destino;
        this.costo = costo;
    }

    public Localidad getOrigen() {
        return origen;
    }

    public Localidad getDestino() {
        return destino;
    }

    public double getCosto() {
        return costo;
    }

    @Override
    public String toString() {

        return origen.getNombre()
                + " -> "
                + destino.getNombre()
                + " | costo: "
                + costo;
    }
}