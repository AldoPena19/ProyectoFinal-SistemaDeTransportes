import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class MainAppCamionero extends JFrame {

    private JTabbedPane tabbedPane;

    public MainAppCamionero() {
        setTitle("Sistema de Gestión de Transportes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Botón para buscar
        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(720, 0, 80, 20);
        add(btnSalir);

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Paquetes", crearPanelPaquetes());
        tabbedPane.addTab("Motivos de No Entrega", crearPanelMotivos());

        add(tabbedPane);
        setVisible(false);

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new FormLogin().setVisible(true);
            }
        });
    }


    private JPanel crearPanelPaquetes() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea display = new JTextArea();
        panel.add(new JScrollPane(display), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        JTextField codigoPaqueteField = new JTextField();
        JTextField descripcionField = new JTextField();
        JTextField destinatarioField = new JTextField();
        JTextField direccionDestinatarioField = new JTextField();
        JTextField codigoDepartamentoField = new JTextField();
        JTextField motivoNoEntregaField = new JTextField();

        inputPanel.add(new JLabel("Código Paquete:"));
        inputPanel.add(codigoPaqueteField);
        inputPanel.add(new JLabel("Motivo No Entrega:"));
        inputPanel.add(motivoNoEntregaField);

        display.setEditable(false);

        panel.add(inputPanel, BorderLayout.NORTH);
        agregarBotonesPaquetes(panel, display, codigoPaqueteField, motivoNoEntregaField);

        return panel;
    }

    private void agregarBotonesPaquetes(JPanel panel, JTextArea display, JTextField codigoPaqueteField, JTextField motivoNoEntregaField) {


        JButton actualizarBtn = new JButton("Actualizar Paquete");
        actualizarBtn.addActionListener(e -> {
            try {
                Paquete paquete = new Paquete(
                        Integer.parseInt(codigoPaqueteField.getText()),
                        Integer.parseInt(motivoNoEntregaField.getText())
                );
                Paquete.actualizarPaqueteCamionero(paquete);
                display.append("Paquete con código " + paquete.getCodigoPaquete() + " actualizado.\n");
            } catch (SQLException ex) {
                display.append("Error al actualizar paquete: " + ex.getMessage() + "\n");
            }
        });

        JButton mostrarBtn = new JButton("Mostrar Paquetes");
        mostrarBtn.addActionListener(e -> {
            try {
                List<Paquete> paquetes = Paquete.obtenerPaquetes();

                // Crear un StringBuilder para construir el texto
                StringBuilder texto = new StringBuilder();

                // Agregar encabezado
                texto.append(String.format("%-6s %-20s %-15s %-30s %-30s %-30s%n",
                        "Codigo", "Descripcion", "Destinatario", "Direccion", "Departamento", "Estado Entrega"));
                texto.append("-".repeat(150)).append("\n"); // Reducido el número de guiones

                // Agregar cada motivo
                for (Paquete paquete : paquetes) {
                    texto.append(String.format("%-6s %-20s %-15s %-30s %-30s %-30s%n",
                            paquete.getCodigoPaquete(),
                            paquete.getDescripcion(),
                            paquete.getDestinatario(),
                            paquete.getDireccionDestinatario(),
                            paquete.getNomDepartamento(),
                            paquete.getEstadoEntrega()
                    ));
                }

                // Actualizar el área de texto
                display.setText(texto.toString());

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error al mostrar los paquetes: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(actualizarBtn);
        buttonPanel.add(mostrarBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }


    private JPanel crearPanelMotivos() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea display = new JTextArea();
        panel.add(new JScrollPane(display), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        JTextField idMotivoField = new JTextField();
        JTextField descripcionField = new JTextField();

        display.setEditable(false);

        panel.add(inputPanel, BorderLayout.NORTH);
        agregarBotonesMotivos(panel, display, idMotivoField, descripcionField);


        return panel;
    }

    private void agregarBotonesMotivos(JPanel panel, JTextArea display, JTextField idMotivoField, JTextField descripcionField) {


        JButton mostrarBtn = new JButton("Mostrar Motivos");
        mostrarBtn.addActionListener(e -> {
            try {
                List<MotivoNoEntrega> motivos = MotivoNoEntrega.obtenerMotivo();

                // Crear un StringBuilder para construir el texto
                StringBuilder texto = new StringBuilder();

                // Agregar encabezado
                texto.append(String.format("%-6s %-20s%n",
                        "Código", "Descripción"));
                texto.append("-".repeat(30)).append("\n"); // Reducido el número de guiones

                // Agregar cada motivo
                for (MotivoNoEntrega motivo : motivos) {
                    texto.append(String.format("%-6d %-20s%n",
                            motivo.getId(),      // Asumiendo que es el nombre correcto del método
                            motivo.getDescripcion()
                    ));
                }

                // Actualizar el área de texto
                display.setText(texto.toString());

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error al mostrar los motivos: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(mostrarBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }


}
