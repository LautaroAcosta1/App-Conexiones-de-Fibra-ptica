package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import modelo.Localidad;
import servicio.Planificador;

public class MainForm {

    private JFrame frame;
    private JMapViewer mapa;
    private Planificador planificador;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainForm window = new MainForm();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainForm() {
        planificador = new Planificador(1000, 0.20, 50000);
        initialize();
        cargarYDibujarLocalidades();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 682, 496);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("CONEXIONES FIBRA OPTICA - Mapa");

        mapa = new JMapViewer();
        mapa.setZoomControlsVisible(false);

        Coordinate centro = new Coordinate(-34, -58);
        mapa.setDisplayPosition(centro, 6);

        frame.getContentPane().add(mapa);

        JButton btnAgregarLocalidad = new JButton("AGREGAR LOCALIDAD");
        btnAgregarLocalidad.setBounds(10, 406, 164, 46);
        mapa.add(btnAgregarLocalidad);
        btnAgregarLocalidad.addActionListener(e -> {
            LocalidadForm dialog = new LocalidadForm(frame);
            dialog.setVisible(true);

            Localidad localidad = dialog.getLocalidad();

            if (localidad != null) {
                planificador.agregarLocalidad(localidad);
                dibujarMarcadores();
            }
        });

        JButton btnPlanificarRuta = new JButton("PLANIFICAR RUTA");
        btnPlanificarRuta.setBounds(492, 406, 164, 46);
        mapa.add(btnPlanificarRuta);

        JLabel lblCosto = new JLabel("EL COSTO TOTAL ES:");
        lblCosto.setBounds(10, 11, 205, 46);
        mapa.add(lblCosto);

        JButton btnBorrarLocalidades = new JButton("BORRAR HISTORIAL LOCALIDADES");
        btnBorrarLocalidades.setBounds(253, 406, 164, 46);
        mapa.add(btnBorrarLocalidades);
    }

    private void cargarYDibujarLocalidades() {
        try {
            planificador.cargarLocalidades();
        } catch (Exception e) {
            // si no existe el archivo todavia, arranca vacio
        }
        dibujarMarcadores();
    }

    private void dibujarMarcadores() {
        mapa.removeAllMapMarkers();
        for (Localidad l : planificador.getLocalidades()) {
            Coordinate coordenada = new Coordinate(
                    l.getLatitud(),
                    l.getLongitud()
            );
            MapMarkerDot marcador = new MapMarkerDot(
                    l.getNombre(),
                    coordenada
            );
            mapa.addMapMarker(marcador);
        }
    }
}