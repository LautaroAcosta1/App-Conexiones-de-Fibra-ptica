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

        double lat1 = Math.toRadians(a.getLatitud());
        double lon1 = Math.toRadians(a.getLongitud());

        double lat2 = Math.toRadians(b.getLatitud());
        double lon2 = Math.toRadians(b.getLongitud());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double haversine =
                Math.sin(dLat / 2) * Math.sin(dLat / 2)
                        + Math.cos(lat1)
                        * Math.cos(lat2)
                        * Math.sin(dLon / 2)
                        * Math.sin(dLon / 2);

        double c =
                2 * Math.atan2(
                        Math.sqrt(haversine),
                        Math.sqrt(1 - haversine)
                );

        double radioTierra = 6371;

        return radioTierra * c;
    }
}