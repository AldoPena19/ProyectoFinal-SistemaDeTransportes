import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class MainAppOper extends JFrame {

    private JTabbedPane tabbedPane;

    public MainAppOper() {
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
        tabbedPane.addTab("Viajes", crearPanelViajes());

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
        inputPanel.add(new JLabel("Descripción:"));
        inputPanel.add(descripcionField);
        inputPanel.add(new JLabel("Destinatario:"));
        inputPanel.add(destinatarioField);
        inputPanel.add(new JLabel("Dirección Destinatario:"));
        inputPanel.add(direccionDestinatarioField);
        inputPanel.add(new JLabel("Código Departamento:"));
        inputPanel.add(codigoDepartamentoField);
        inputPanel.add(new JLabel("Motivo No Entrega:"));
        inputPanel.add(motivoNoEntregaField);

        panel.add(inputPanel, BorderLayout.NORTH);
        agregarBotonesPaquetes(panel, display, codigoPaqueteField, descripcionField, destinatarioField,
                direccionDestinatarioField, codigoDepartamentoField, motivoNoEntregaField);

        return panel;
    }

    private void agregarBotonesPaquetes(JPanel panel, JTextArea display, JTextField codigoPaqueteField,
                                        JTextField descripcionField, JTextField destinatarioField,
                                        JTextField direccionDestinatarioField, JTextField codigoDepartamentoField, JTextField motivoNoEntregaField) {
        JButton agregarBtn = new JButton("Agregar Paquete");
        agregarBtn.addActionListener(e -> {
            try {
                Paquete paquete = new Paquete(
                        Integer.parseInt(codigoPaqueteField.getText()),
                        descripcionField.getText(),
                        destinatarioField.getText(),
                        direccionDestinatarioField.getText(),
                        Integer.parseInt(codigoDepartamentoField.getText()),
                        1
                );
                Paquete.agregarPaquete(paquete);
                display.append("Paquete agregado: " + paquete.getDescripcion() + "\n");
            } catch (SQLException ex) {
                display.append("Error al agregar paquete: " + ex.getMessage() + "\n");
            }
        });

        JButton eliminarBtn = new JButton("Eliminar Paquete");
        eliminarBtn.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(codigoPaqueteField.getText());
                Paquete.eliminarPaquete(codigo);
                display.append("Paquete con código " + codigo + " eliminado.\n");
            } catch (SQLException ex) {
                display.append("Error al eliminar paquete: " + ex.getMessage() + "\n");
            }
        });

        JButton actualizarBtn = new JButton("Actualizar Paquete");
        actualizarBtn.addActionListener(e -> {
            try {
                Paquete paquete = new Paquete(
                        Integer.parseInt(codigoPaqueteField.getText()),
                        descripcionField.getText(),
                        destinatarioField.getText(),
                        direccionDestinatarioField.getText(),
                        Integer.parseInt(codigoDepartamentoField.getText()),
                        Integer.parseInt(motivoNoEntregaField.getText())
                );
                Paquete.actualizarPaquete(paquete);
                display.append("Paquete con código " + paquete.getCodigoPaquete() + " actualizado.\n");
            } catch (SQLException ex) {
                display.append("Error al actualizar paquete: " + ex.getMessage() + "\n");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(agregarBtn);
        buttonPanel.add(eliminarBtn);
        buttonPanel.add(actualizarBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }


    private JPanel crearPanelMotivos() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea display = new JTextArea();
        panel.add(new JScrollPane(display), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        JTextField idMotivoField = new JTextField();
        JTextField descripcionField = new JTextField();

        inputPanel.add(new JLabel("ID Motivo:"));
        inputPanel.add(idMotivoField);
        inputPanel.add(new JLabel("Descripción:"));
        inputPanel.add(descripcionField);

        panel.add(inputPanel, BorderLayout.NORTH);
        agregarBotonesMotivos(panel, display, idMotivoField, descripcionField);


        return panel;
    }

    private void agregarBotonesMotivos(JPanel panel, JTextArea display, JTextField idMotivoField, JTextField descripcionField) {
        JButton agregarBtn = new JButton("Agregar Motivo");
        agregarBtn.addActionListener(e -> {
            try {
                MotivoNoEntrega motivo = new MotivoNoEntrega(
                        0,
                        descripcionField.getText()
                );
                MotivoNoEntrega.agregarMotivo(motivo);
                display.append("Motivo agregado: " + motivo.getDescripcion() + "\n");
            } catch (SQLException ex) {
                display.append("Error al agregar motivo: " + ex.getMessage() + "\n");
            }
        });

        JButton actualizarBtn = new JButton("Actualizar Motivo");
        actualizarBtn.addActionListener(e -> {
            try {
                MotivoNoEntrega motivo = new MotivoNoEntrega(
                        Integer.parseInt(idMotivoField.getText()),
                        descripcionField.getText()
                );
                MotivoNoEntrega.actualizarMotivo(motivo);
                display.append("Motivo actualizado: " + motivo.getDescripcion() + "\n");
            } catch (SQLException ex) {
                display.append("Error al actualizar motivo: " + ex.getMessage() + "\n");
            }
        });

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
        buttonPanel.add(agregarBtn);
        buttonPanel.add(actualizarBtn);
        buttonPanel.add(mostrarBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel crearPanelViajes() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea display = new JTextArea();
        panel.add(new JScrollPane(display), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        JTextField idViajeField = new JTextField();
        JTextField camioneroDPIField = new JTextField();
        JTextField camionMatriculaField = new JTextField();
        JTextField fechaSalidaField = new JTextField();
        JTextField fechaRegresoField = new JTextField();

        inputPanel.add(new JLabel("ID Viaje:"));
        inputPanel.add(idViajeField);
        inputPanel.add(new JLabel("Camionero DPI:"));
        inputPanel.add(camioneroDPIField);
        inputPanel.add(new JLabel("Camión Matrícula:"));
        inputPanel.add(camionMatriculaField);
        inputPanel.add(new JLabel("Fecha de Salida (yyyy-MM-dd HH:mm:ss):"));
        inputPanel.add(fechaSalidaField);
        inputPanel.add(new JLabel("Fecha de Regreso (yyyy-MM-dd HH:mm:ss):"));
        inputPanel.add(fechaRegresoField);

        panel.add(inputPanel, BorderLayout.NORTH);
        agregarBotonesViajes(panel, display, idViajeField, camioneroDPIField, camionMatriculaField,
                fechaSalidaField, fechaRegresoField);

        return panel;
    }

    private void agregarBotonesViajes(JPanel panel, JTextArea display, JTextField idViajeField,
                                      JTextField camioneroDPIField, JTextField camionMatriculaField,
                                      JTextField fechaSalidaField, JTextField fechaRegresoField) {
        JButton agregarBtn = new JButton("Agregar Viaje");
        agregarBtn.addActionListener(e -> {
            try {
                Viaje viaje = new Viaje(
                        0,
                        camioneroDPIField.getText(),
                        camionMatriculaField.getText(),
                        fechaSalidaField.getText().isEmpty() ? new Timestamp(0) : Timestamp.valueOf(fechaSalidaField.getText()),
                        fechaRegresoField.getText().isEmpty() ? new Timestamp(0) : Timestamp.valueOf(fechaSalidaField.getText())
                );
                Viaje.agregarViaje(viaje);
                display.append("Viaje agregado: " + viaje.getIdViaje() + "\n");
            } catch (SQLException ex) {
                display.append("Error al agregar viaje: " + ex.getMessage() + "\n");
            }
        });

        JButton actualizarBtn = new JButton("Actualizar Viaje");
        actualizarBtn.addActionListener(e -> {
            try {
                Viaje viaje = new Viaje(
                        Integer.parseInt(idViajeField.getText()),
                        camioneroDPIField.getText(),
                        camionMatriculaField.getText(),
                        Timestamp.valueOf(fechaSalidaField.getText()),
                        Timestamp.valueOf(fechaRegresoField.getText())
                );
                Viaje.actualizarViaje(viaje);
                display.append("Viaje actualizado: " + viaje.getIdViaje() + "\n");
            } catch (SQLException ex) {
                display.append("Error al actualizar viaje: " + ex.getMessage() + "\n");
            }
        });

        JButton mostrarBtn = new JButton("Mostrar viajes");
        mostrarBtn.addActionListener(e -> {
            try {
                List<Viaje> viajes = Viaje.obtenerViaje();

                // Crear un StringBuilder para construir el texto
                StringBuilder texto = new StringBuilder();

                // Agregar encabezado
                texto.append(String.format("%-6s %-20s %-15s %-30s %-30s%n",
                        "ID", "DPI","Matricula","Fecha Salida","Fecharegreso"));
                texto.append("-".repeat(150)).append("\n"); // Reducido el número de guiones

                // Agregar cada motivo
                for (Viaje viaje : viajes) {
                    texto.append(String.format("%-6d %-20s %-15s %-30s %-30s%n",
                            viaje.getIdViaje(),      // Asumiendo que es el nombre correcto del método
                            viaje.getCamionero(),
                            viaje.getMatricula(),
                            viaje.getFechaSalida(),
                            viaje.getFechaRegreso()
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
        buttonPanel.add(agregarBtn);
        buttonPanel.add(actualizarBtn);
        buttonPanel.add(mostrarBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }


}
