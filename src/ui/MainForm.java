package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import modelo.Conexion;
import modelo.Localidad;
import modelo.ResultadoPlanificacion;
import servicio.Planificador;

public class MainForm {

    private JFrame frame;
    private JMapViewer mapa;
    private Planificador planificador;
    private JLabel lblCostoNumero;

    private static final Color COLOR_FONDO  = new Color(20, 20, 20);
    private static final Color COLOR_BOTON  = new Color(45, 45, 45);
    private static final Color COLOR_ACENTO = new Color(0, 153, 255);
    private static final Color COLOR_TEXTO  = new Color(240, 240, 240);
    private static final Font  FUENTE_LABEL = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font  FUENTE_COSTO = new Font("Segoe UI", Font.BOLD, 15);
    private static final Font  FUENTE_BOTON = new Font("Segoe UI", Font.PLAIN, 12);

    private static final Coordinate CENTRO_ARGENTINA = new Coordinate(-38.0, -63.0);

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
        ParametrosForm parametros = new ParametrosForm();
        parametros.setVisible(true);

        if (!parametros.isConfirmado()) {
            System.exit(0);
        }

        planificador = new Planificador(
                parametros.getCostoPorKm(),
                parametros.getPorcentajeExtra(),
                parametros.getCostoInterprovincial());

        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Fibra Optica — Planificador de Conexiones");
        frame.setLayout(new BorderLayout());

        // panel superior como background del label de costo
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(COLOR_FONDO);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        frame.add(panelSuperior, BorderLayout.NORTH);

        // panel que contiene el costo
        JPanel panelCosto = new JPanel();
        panelCosto.setBackground(new Color(38, 38, 38));
        panelCosto.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60), 1, true),
                BorderFactory.createEmptyBorder(6, 20, 6, 20)
        ));
        panelCosto.setLayout(new BoxLayout(panelCosto, BoxLayout.X_AXIS));

        // texto fijo en gris claro
        JLabel lblCostoTitulo = new JLabel("COSTO TOTAL  ");
        lblCostoTitulo.setForeground(new Color(160, 160, 160));
        lblCostoTitulo.setFont(FUENTE_LABEL);

        // numero en azul acento y mas grande
        lblCostoNumero = new JLabel("—");
        lblCostoNumero.setForeground(COLOR_ACENTO);
        lblCostoNumero.setFont(FUENTE_COSTO);

        panelCosto.add(lblCostoTitulo);
        panelCosto.add(lblCostoNumero);
        panelSuperior.add(panelCosto);

        // mapa al centro
        mapa = new JMapViewer();
        mapa.setZoomControlsVisible(false);
        mapa.setDisplayPosition(CENTRO_ARGENTINA, 5);
        mapa.setPreferredSize(new java.awt.Dimension(1040, 500));
        frame.add(mapa, BorderLayout.CENTER);

        // panel inferior con botones
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(COLOR_FONDO);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        frame.add(panelInferior, BorderLayout.SOUTH);

        JButton btnAgregar = crearBoton("+ Agregar localidad", true);
        panelInferior.add(btnAgregar);
        btnAgregar.addActionListener(e -> {
            LocalidadForm dialog = new LocalidadForm(frame);
            dialog.setVisible(true);
            Localidad localidad = dialog.getLocalidad();
            if (localidad != null) {
                planificador.agregarLocalidad(localidad);
                dibujarMarcadores();
            }
        });

        JButton btnPlanificar = crearBoton("Planificar ruta", true);
        panelInferior.add(btnPlanificar);
        btnPlanificar.addActionListener(e -> cargarYDibujarConexiones());

        JButton btnGuardar = crearBoton("Guardar", false);
        panelInferior.add(btnGuardar);
        btnGuardar.addActionListener(e -> {
            try {
                planificador.guardarLocalidades();
                JOptionPane.showMessageDialog(frame, "Localidades guardadas correctamente.");
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(frame, "Error al guardar.");
            }
        });
        

        JButton btnImportar = crearBoton("Importar", false);
        panelInferior.add(btnImportar);
        btnImportar.addActionListener(e -> cargarYDibujarLocalidades());

        JButton btnLimpiar = crearBoton("Limpiar", false);
        panelInferior.add(btnLimpiar);
        btnLimpiar.addActionListener(e -> {
            try {
                planificador.borrarLocalidades();
                dibujarMarcadores();
                mapa.removeAllMapPolygons();
                lblCostoNumero.setText("—");
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(frame, "Error al limpiar.");
            }
        });

        // pack ajusta la ventana al tamaño preferido del mapa automaticamente
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
    
    // lo pongo porque ahorra lineas, es un template con las settings esteticas default
    private JButton crearBoton(String texto, boolean acento) {
        JButton btn = new JButton(texto);
        btn.setFont(FUENTE_BOTON);
        btn.setForeground(COLOR_TEXTO);
        btn.setBackground(acento ? COLOR_ACENTO : COLOR_BOTON);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        return btn;
    }

    private void cargarYDibujarConexiones() {
        try {
            ResultadoPlanificacion resultado = planificador.planificar();
            dibujarConexiones(resultado);
            lblCostoNumero.setText("$" + String.format("%.2f", resultado.getCostoTotal()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }
    }

    private void cargarYDibujarLocalidades() {
        try {
            planificador.cargarLocalidades();
        } catch (Exception e) {
            // si no existe el archivo todavia, arranca vacio
        }
        dibujarMarcadores();
    }

    private void dibujarConexiones(ResultadoPlanificacion resultado) {
        mapa.removeAllMapPolygons();
        for (Conexion c : resultado.getConexiones()) {
            Coordinate origen  = new Coordinate(c.getOrigen().getLatitud(),  c.getOrigen().getLongitud());
            Coordinate destino = new Coordinate(c.getDestino().getLatitud(), c.getDestino().getLongitud());
            MapPolygonImpl linea = new MapPolygonImpl(origen, destino, origen);
            linea.setColor(COLOR_ACENTO);
            linea.setBackColor(new Color(0, 0, 0, 0));
            mapa.addMapPolygon(linea);
        }
    }

    private void dibujarMarcadores() {
        mapa.removeAllMapMarkers();
        for (Localidad l : planificador.getLocalidades()) {
            Coordinate coordenada = new Coordinate(l.getLatitud(), l.getLongitud());
            MapMarkerDot marcador = new MapMarkerDot(l.getNombre(), coordenada);
            marcador.setBackColor(COLOR_ACENTO);
            mapa.addMapMarker(marcador);
        }
    }
}