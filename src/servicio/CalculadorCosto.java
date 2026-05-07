package servicio;

import modelo.Localidad;

public class CalculadorCosto {

    private double costoPorKm;
    private double porcentajeExtra;
    private double costoInterprovincial;

    public CalculadorCosto(double costoPorKm,
                           double porcentajeExtra,
                           double costoInterprovincial) {

        this.costoPorKm = costoPorKm;
        this.porcentajeExtra = porcentajeExtra;
        this.costoInterprovincial = costoInterprovincial;
    }

    public double calcularCosto(Localidad a, Localidad b) {

        double distancia = calcularDistancia(a, b);

        double costo = distancia * costoPorKm;

        if (distancia > 300) {
            costo *= (1 + porcentajeExtra);
        }

        if (!a.getProvincia().equals(b.getProvincia())) {
            costo += costoInterprovincial;
        }

        return costo;
    }

    private double calcularDistancia(Localidad a, Localidad b) {

        double dx = a.getLatitud() - b.getLatitud();
        double dy = a.getLongitud() - b.getLongitud();

        return Math.sqrt(dx * dx + dy * dy);
    }
}