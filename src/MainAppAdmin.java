import java.awt.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import javax.swing.*;

public class MainAppAdmin extends JFrame {

    private JTabbedPane tabbedPane;

    public MainAppAdmin() {
        setTitle("Sistema de Gestión de Transportes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Camioneros", crearPanelCamioneros());
        tabbedPane.addTab("Camiones", crearPanelCamiones());
        tabbedPane.addTab("Paquetes", crearPanelPaquetes());
        tabbedPane.addTab("Motivos de No Entrega", crearPanelMotivos());
        tabbedPane.addTab("Viajes", crearPanelViajes());

        add(tabbedPane);
        setVisible(false);
    }

    private JPanel crearPanelCamioneros() {

        JPanel panel = new JPanel(new BorderLayout());
        JTextArea display = new JTextArea();
        panel.add(new JScrollPane(display), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        JTextField dpiField = new JTextField();
        JTextField nombreField = new JTextField();
        JTextField telefonoField = new JTextField();
        JTextField direccionField = new JTextField();
        JTextField salarioField = new JTextField();
        JTextField zonaField = new JTextField();

        inputPanel.add(new JLabel("DPI:"));
        inputPanel.add(dpiField);
        inputPanel.add(new JLabel("Nombre:"));
        inputPanel.add(nombreField);
        inputPanel.add(new JLabel("Teléfono:"));
        inputPanel.add(telefonoField);
        inputPanel.add(new JLabel("Dirección:"));
        inputPanel.add(direccionField);
        inputPanel.add(new JLabel("Salario:"));
        inputPanel.add(salarioField);
        inputPanel.add(new JLabel("Zona:"));
        inputPanel.add(zonaField);

        panel.add(inputPanel, BorderLayout.NORTH);
        agregarBotonesCamioneros(panel, display, dpiField, nombreField, telefonoField, direccionField, salarioField, zonaField);


        return panel;
    }

    private void agregarBotonesCamioneros(JPanel panel, JTextArea display, JTextField dpiField, JTextField nombreField,
                                          JTextField telefonoField, JTextField direccionField, JTextField salarioField, JTextField zonaField) {


        JButton agregarBtn = new JButton("Agregar Camionero");
        agregarBtn.addActionListener(e -> {
            try {
                Camionero camionero = new Camionero(
                        dpiField.getText(),
                        nombreField.getText(),
                        telefonoField.getText(),
                        direccionField.getText(),
                        Double.parseDouble(salarioField.getText()),
                        zonaField.getText()
                );
                Camionero.agregarCamionero(camionero);
                nombreField.setText("");
                telefonoField.setText("");
                direccionField.setText("");
                salarioField.setText("");
                zonaField.setText("");
                display.append("Camionero agregado: " + camionero.getNombre() + "\n");

                dpiField.setText("");
            } catch (SQLException ex) {
                display.append("Error al agregar camionero: " + ex.getMessage() + "\n");
            } catch (NumberFormatException ex) {
                display.append("Error en el formato del salario: " + ex.getMessage() + "\n");
            }


        });


        JButton eliminarBtn = new JButton("Eliminar Camionero");
        eliminarBtn.addActionListener(e -> {
            try {

                String dpi = dpiField.getText();
                if (dpi.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Favor ingrese DPI de camionero a eliminar");
                } else {
                    Camionero.eliminarCamionero(dpi);
                    display.append("Camionero con DPI " + dpi + " eliminado.\n");
                }

            } catch (SQLException ex) {
                display.append("Error al eliminar camionero: " + ex.getMessage() + "\n");
            }
        });

        JButton actualizarBtn = new JButton("Actualizar Camionero");
        actualizarBtn.addActionListener(e -> {
            try {
                Camionero camionero = new Camionero(
                        dpiField.getText(),
                        nombreField.getText(),
                        telefonoField.getText(),
                        direccionField.getText(),
                        Double.parseDouble(salarioField.getText()),
                        zonaField.getText()
                );
                Camionero.actualizarCamionero(camionero);
                display.append("Camionero con DPI " + camionero.getDpi() + " actualizado.\n");
            } catch (SQLException ex) {
                display.append("Error al actualizar camionero: " + ex.getMessage() + "\n");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(agregarBtn);
        buttonPanel.add(eliminarBtn);
        buttonPanel.add(actualizarBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel crearPanelCamiones() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea display = new JTextArea();
        panel.add(new JScrollPane(display), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        JTextField matriculaField = new JTextField();
        JTextField modeloField = new JTextField();
        JTextField tipoField = new JTextField();
        JTextField tonelajeField = new JTextField();

        inputPanel.add(new JLabel("Matrícula:"));
        inputPanel.add(matriculaField);
        inputPanel.add(new JLabel("Modelo:"));
        inputPanel.add(modeloField);
        inputPanel.add(new JLabel("Tipo:"));
        inputPanel.add(tipoField);
        inputPanel.add(new JLabel("Tonelaje:"));
        inputPanel.add(tonelajeField);

        panel.add(inputPanel, BorderLayout.NORTH);
        agregarBotonesCamiones(panel, display, matriculaField, modeloField, tipoField, tonelajeField);

        return panel;
    }


    private void agregarBotonesCamiones(JPanel panel, JTextArea display, JTextField matriculaField, JTextField modeloField,
                                        JTextField tipoField, JTextField tonelajeField) {
        JButton agregarBtn = new JButton("Agregar Camión");
        agregarBtn.addActionListener(e -> {
            try {
                Camion camion = new Camion(
                        matriculaField.getText(),
                        modeloField.getText(),
                        tipoField.getText(),
                        Double.parseDouble(tonelajeField.getText())
                );
                Camion.agregarCamion(camion);
                display.append("Camión agregado: " + camion.getMatricula() + "\n");
            } catch (SQLException ex) {
                display.append("Error al agregar camión: " + ex.getMessage() + "\n");
            }
        });

        JButton eliminarBtn = new JButton("Eliminar Camión");
        eliminarBtn.addActionListener(e -> {
            try {
                String matricula = matriculaField.getText();
                Camion.eliminarCamion(matricula);
                display.append("Camión con matrícula " + matricula + " eliminado.\n");
            } catch (SQLException ex) {
                display.append("Error al eliminar camión: " + ex.getMessage() + "\n");
            }
        });

        JButton actualizarBtn = new JButton("Actualizar Camión");
        actualizarBtn.addActionListener(e -> {
            try {
                Camion camion = new Camion(
                        matriculaField.getText(),
                        modeloField.getText(),
                        tipoField.getText(),
                        Double.parseDouble(tonelajeField.getText())
                );
                Camion.actualizarCamion(camion);
                display.append("Camión con matrícula " + camion.getMatricula() + " actualizado.\n");
            } catch (SQLException ex) {
                display.append("Error al actualizar camión: " + ex.getMessage() + "\n");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(agregarBtn);
        buttonPanel.add(eliminarBtn);
        buttonPanel.add(actualizarBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
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

        JButton eliminarBtn = new JButton("Eliminar Motivo");
        eliminarBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idMotivoField.getText());
                MotivoNoEntrega.eliminarMotivo(id);
                display.append("Motivo eliminado: " + id + "\n");
            } catch (SQLException ex) {
                display.append("Error al eliminar motivo: " + ex.getMessage() + "\n");
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
        buttonPanel.add(eliminarBtn);
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

        JButton eliminarBtn = new JButton("Eliminar Viaje");
        eliminarBtn.addActionListener(e -> {
            try {
                int idViaje = Integer.parseInt(idViajeField.getText());
                Viaje.eliminarViaje(idViaje);
                display.append("Viaje eliminado: " + idViaje + "\n");
            } catch (SQLException ex) {
                display.append("Error al eliminar viaje: " + ex.getMessage() + "\n");
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
        buttonPanel.add(eliminarBtn);
        buttonPanel.add(mostrarBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        new MainAppAdmin();
    }
}
