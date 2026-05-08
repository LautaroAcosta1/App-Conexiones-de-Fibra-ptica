package ui;

import modelo.Localidad;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class LocalidadForm extends JDialog {

    private JTextField nombreField;
    private JTextField provinciaField;
    private JTextField latitudField;
    private JTextField longitudField;

    private Localidad localidad;

    public LocalidadForm(JFrame parent) {
        super(parent, "Agregar Localidad", true);
        setSize(300, 220);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(0, 2, 5, 5));
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        nombreField    = new JTextField();
        provinciaField = new JTextField();
        latitudField   = new JTextField();
        longitudField  = new JTextField();

        add(new JLabel("Nombre:"));    add(nombreField);
        add(new JLabel("Provincia:")); add(provinciaField);
        add(new JLabel("Latitud:"));   add(latitudField);
        add(new JLabel("Longitud:"));  add(longitudField);

        JButton btnConfirmar = new JButton("Confirmar");
        JButton btnCancelar  = new JButton("Cancelar");

        btnConfirmar.addActionListener(e -> confirmar());
        btnCancelar.addActionListener(e -> dispose());

        add(btnConfirmar);
        add(btnCancelar);
    }

    private void confirmar() {
        try {
            String nombre    = nombreField.getText().trim();
            String provincia = provinciaField.getText().trim();
            double latitud   = Double.parseDouble(latitudField.getText().trim());
            double longitud  = Double.parseDouble(longitudField.getText().trim());

            if (nombre.isEmpty() || provincia.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre y la provincia no pueden estar vacios.");
                return;
            }

            localidad = new Localidad(nombre, provincia, latitud, longitud);
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,"Latitud y longitud deben ser numeros.");
        }
    }

    public Localidad getLocalidad() {
        return localidad;
    }
}