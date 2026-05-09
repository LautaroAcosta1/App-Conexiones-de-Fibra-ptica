package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import modelo.Conexion;
import modelo.Localidad;
import modelo.ResultadoPlanificacion;
import servicio.Planificador;
import javax.swing.JPanel;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class MainForm {

    private JFrame frame;
    private JMapViewer mapa;
    private Planificador planificador;
    private JLabel lblCosto;

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
        
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1040, 549);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("CONEXIONES FIBRA OPTICA - Mapa");

        mapa = new JMapViewer();
        mapa.setZoomControlsVisible(false);

        Coordinate centro = new Coordinate(-34, -58);
        mapa.setDisplayPosition(centro, 6);

        frame.getContentPane().add(mapa);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 477, 1014, 33);
        mapa.add(panel);
        
               JButton btnAgregarLocalidad = new JButton("AGREGAR LOCALIDAD");
               panel.add(btnAgregarLocalidad);
               btnAgregarLocalidad.addActionListener(e -> {
                   LocalidadForm dialog = new LocalidadForm(frame);
                   dialog.setVisible(true);

                   Localidad localidad = dialog.getLocalidad();

                   if (localidad != null) {
                       planificador.agregarLocalidad(localidad);
                       dibujarMarcadores();
                   }
               });
                
               JButton btnBorrarLocalidades = new JButton("LIMPIAR LOCALIDADES");
               panel.add(btnBorrarLocalidades);
               btnBorrarLocalidades.addActionListener(e -> {   
            	    try {
            	        planificador.borrarLocalidades();
            	        dibujarMarcadores();
            	        mapa.removeAllMapPolygons();
            	        lblCosto.setText("EL COSTO TOTAL ES: -");
            	    } catch (IOException e1) {
            	        JOptionPane.showMessageDialog(frame, "Error al borrar el historial.");
            	    }
            	});
                        
               JButton btnPlanificarRuta = new JButton("PLANIFICAR RUTA");
               panel.add(btnPlanificarRuta);
               btnPlanificarRuta.addActionListener(e -> { 
            	   cargarYDibujarConexiones();
               });
               
              JButton btnGuardarLocalidades = new JButton("GUARDAR LOCALIDADES");
              panel.add(btnGuardarLocalidades);
              btnGuardarLocalidades.addActionListener(e -> {
            	   try {
            		   planificador.guardarLocalidades();
            	   } catch (Exception e1) {
            		   
            	   }
              });
                                
              
               JButton btnImportarLocalidad = new JButton("IMPORTAR LOCALIDADES");
               panel.add(btnImportarLocalidad);
               btnImportarLocalidad.addActionListener(e -> {
            	  cargarYDibujarLocalidades(); 
               });
                                
              JPanel panel_1 = new JPanel();
              panel_1.setBounds(0, 0, 1024, 46);
              mapa.add(panel_1);
                                
              lblCosto = new JLabel("EL COSTO TOTAL ES: -");
              panel_1.add(lblCosto);
              
    }
    

    private void cargarYDibujarConexiones() {
    	try {
    		ResultadoPlanificacion resultado = planificador.planificar();
    		dibujarConexiones(resultado);
    		lblCosto.setText("El costo es : " + resultado.getCostoTotal());
    	} catch (Exception e) {
    	
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
    		Coordinate coordOrigen = new Coordinate(c.getOrigen().getLatitud(), 
    													 c.getOrigen().getLongitud());
    		Coordinate coordDestino = new Coordinate(c.getDestino().getLatitud(),
    													  c.getDestino().getLongitud());
    		
    		MapPolygonImpl linea = new MapPolygonImpl(coordOrigen, coordDestino, coordOrigen);
    		linea.setColor(Color.red);
    		linea.setBackColor(Color.black);
    		mapa.addMapPolygon(linea);
    	}
    }


    private void dibujarMarcadores() {
        mapa.removeAllMapMarkers();
        for (Localidad l : planificador.getLocalidades()) {
            Coordinate coordenada = new Coordinate(l.getLatitud(), l.getLongitud());
            MapMarkerDot marcador = new MapMarkerDot(l.getNombre(),coordenada);
            mapa.addMapMarker(marcador);
        }
    }
}