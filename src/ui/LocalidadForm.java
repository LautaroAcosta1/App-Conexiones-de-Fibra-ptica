package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modelo.Localidad;

public class LocalidadForm extends JDialog {

    private static final Color COLOR_FONDO  = new Color(20, 20, 20);
    private static final Color COLOR_CAMPO  = new Color(38, 38, 38);
    private static final Color COLOR_ACENTO = new Color(0, 153, 255);
    private static final Color COLOR_TEXTO  = new Color(240, 240, 240);
    private static final Color COLOR_LABEL  = new Color(160, 160, 160);
    private static final Font  FUENTE       = new Font("Segoe UI", Font.PLAIN, 12);

    private JTextField nombreField;
    private JTextField provinciaField;
    private JTextField latitudField;
    private JTextField longitudField;
    private Localidad localidad;

    public LocalidadForm(JFrame parent) {
        super(parent, "Agregar Localidad", true);
        setSize(320, 260);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_FONDO);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // panel con los campos del formulario
        JPanel panelCampos = new JPanel(new GridLayout(4, 2, 8, 10));
        panelCampos.setBackground(COLOR_FONDO);
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        nombreField    = crearCampo();
        provinciaField = crearCampo();
        latitudField   = crearCampo();
        longitudField  = crearCampo();

        panelCampos.add(crearLabel("Nombre:"));    panelCampos.add(nombreField);
        panelCampos.add(crearLabel("Provincia:")); panelCampos.add(provinciaField);
        panelCampos.add(crearLabel("Latitud:"));   panelCampos.add(latitudField);
        panelCampos.add(crearLabel("Longitud:"));  panelCampos.add(longitudField);

        add(panelCampos, BorderLayout.CENTER);

        // panel inferior con los botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        JButton btnConfirmar = crearBoton("Confirmar", true);
        JButton btnCancelar  = crearBoton("Cancelar", false);

        btnConfirmar.addActionListener(e -> confirmar());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);
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
            JOptionPane.showMessageDialog(this, "Latitud y longitud deben ser numeros.");
        }
    }

    
    //templates/presets para no repetir codigo para cada textField y label
    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(COLOR_LABEL);
        label.setFont(FUENTE);
        return label;
    }

    private JTextField crearCampo() {
        JTextField field = new JTextField();
        field.setBackground(COLOR_CAMPO);
        field.setForeground(COLOR_TEXTO);
        field.setCaretColor(COLOR_TEXTO);
        field.setFont(FUENTE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60), 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        return field;
    }

    private JButton crearBoton(String texto, boolean acento) {
        JButton btn = new JButton(texto);
        btn.setFont(FUENTE);
        btn.setForeground(COLOR_TEXTO);
        btn.setBackground(acento ? COLOR_ACENTO : new Color(45, 45, 45));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        return btn;
    }

    public Localidad getLocalidad() {
        return localidad;
    }
}