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

public class ParametrosForm extends JDialog {

    private static final Color COLOR_FONDO  = new Color(20, 20, 20);
    private static final Color COLOR_CAMPO  = new Color(38, 38, 38);
    private static final Color COLOR_ACENTO = new Color(0, 153, 255);
    private static final Color COLOR_TEXTO  = new Color(240, 240, 240);
    private static final Color COLOR_LABEL  = new Color(160, 160, 160);
    private static final Font  FUENTE       = new Font("Segoe UI", Font.PLAIN, 12);

    private JTextField costoPorKmField;
    private JTextField porcentajeExtraField;
    private JTextField costoInterprovincialField;

    private double costoPorKm;
    private double porcentajeExtra;
    private double costoInterprovincial;
    private boolean confirmado;

    public ParametrosForm() {
        super((JFrame) null, "Configurar parametros", true);
        setSize(380, 240);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_FONDO);
        confirmado = false;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // panel central con los tres campos de parametros
        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 8, 10));
        panelCampos.setBackground(COLOR_FONDO);
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        costoPorKmField           = crearCampo("1000");
        porcentajeExtraField      = crearCampo("0.20");
        costoInterprovincialField = crearCampo("50000");

        panelCampos.add(crearLabel("Costo por km:"));          panelCampos.add(costoPorKmField);
        panelCampos.add(crearLabel("% extra si > 300km:"));    panelCampos.add(porcentajeExtraField);
        panelCampos.add(crearLabel("Costo interprovincial:")); panelCampos.add(costoInterprovincialField);

        add(panelCampos, BorderLayout.CENTER);

        // panel inferior con los botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        JButton btnConfirmar = crearBoton("Confirmar", true);
        JButton btnCancelar  = crearBoton("Cancelar", false);

        btnConfirmar.addActionListener(e -> confirmar());
        btnCancelar.addActionListener(e -> System.exit(0));

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void confirmar() {
        try {
            costoPorKm           = Double.parseDouble(costoPorKmField.getText().trim());
            porcentajeExtra      = Double.parseDouble(porcentajeExtraField.getText().trim());
            costoInterprovincial = Double.parseDouble(costoInterprovincialField.getText().trim());
            confirmado = true;
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Todos los valores deben ser numeros.");
        }
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(COLOR_LABEL);
        label.setFont(FUENTE);
        return label;
    }

    private JTextField crearCampo(String valorPorDefecto) {
        JTextField field = new JTextField(valorPorDefecto);
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

    public double getCostoPorKm()           { return costoPorKm; }
    public double getPorcentajeExtra()      { return porcentajeExtra; }
    public double getCostoInterprovincial() { return costoInterprovincial; }
    public boolean isConfirmado()           { return confirmado; }
}