package modelo;

import java.util.Objects;

public class Localidad {

    private String nombre;
    private String provincia;
    private double latitud;
    private double longitud;

    public Localidad(String nombre,
                     String provincia,
                     double latitud,
                     double longitud) {

        this.nombre = nombre;
        this.provincia = provincia;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    @Override
    public String toString() {

        return nombre
                + " ("
                + provincia
                + ")";
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Localidad that = (Localidad) o;

        return Objects.equals(nombre, that.nombre)
                && Objects.equals(
                provincia,
                that.provincia
        );
    }

    @Override
    public int hashCode() {

        return Objects.hash(
                nombre,
                provincia
        );
    }
}