import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class BusquedaPaqueteFormulario extends JFrame {

    private static ConexionBD conexionBD = new ConexionBD(); //Instancia a la coneccion
    private static Connection con = conexionBD.conectar();

    // Campos de entrada y etiquetas para mostrar los resultados
    private JTextField txtReferencia;
    private JTextField txtNombreCamionero, txtMatricula, txtDescripcionPaquete, txtDestinatario, txtDireccionDestinatario, txtCodigoDepartamento,
            txtNombreDepartamento, txtFechaSalida,txtstatus;

    public BusquedaPaqueteFormulario() {
        setTitle("Formulario de Búsqueda de Paquete");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Campo para ingresar referencia
        JLabel lblReferencia = new JLabel("Ingrese referencia (Codigo de Paquete):");
        lblReferencia.setBounds(20, 20, 250, 20);
        add(lblReferencia);

        txtReferencia = new JTextField();
        txtReferencia.setBounds(270, 20, 200, 25);
        add(txtReferencia);

        // Botón para buscar
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(480, 20, 80, 25);
        add(btnBuscar);

        // Campos para mostrar los resultados
        JLabel lblNombreCamionero = new JLabel("Nombre del Camionero:");
        lblNombreCamionero.setBounds(20, 60, 200, 20);
        add(lblNombreCamionero);

        txtNombreCamionero = new JTextField();
        txtNombreCamionero.setBounds(270, 60, 200, 25);
        txtNombreCamionero.setEditable(false);
        add(txtNombreCamionero);

        // Botón para salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(480, 60, 80, 25);
        add(btnSalir);

        JLabel lblMatricula = new JLabel("Matrícula del Camión:");
        lblMatricula.setBounds(20, 100, 200, 20);
        add(lblMatricula);

        txtMatricula = new JTextField();
        txtMatricula.setBounds(270, 100, 200, 25);
        txtMatricula.setEditable(false);
        add(txtMatricula);

        JLabel lblDescripcionPaquete = new JLabel("Descripción del Paquete:");
        lblDescripcionPaquete.setBounds(20, 140, 200, 20);
        add(lblDescripcionPaquete);

        txtDescripcionPaquete = new JTextField();
        txtDescripcionPaquete.setBounds(270, 140, 200, 25);
        txtDescripcionPaquete.setEditable(false);
        add(txtDescripcionPaquete);

        JLabel lblDestinatario = new JLabel("Destinatario:");
        lblDestinatario.setBounds(20, 180, 200, 20);
        add(lblDestinatario);

        txtDestinatario = new JTextField();
        txtDestinatario.setBounds(270, 180, 200, 25);
        txtDestinatario.setEditable(false);
        add(txtDestinatario);

        JLabel lblDireccionDestinatario = new JLabel("Dirección del Destinatario:");
        lblDireccionDestinatario.setBounds(20, 220, 200, 20);
        add(lblDireccionDestinatario);

        txtDireccionDestinatario = new JTextField();
        txtDireccionDestinatario.setBounds(270, 220, 200, 25);
        txtDireccionDestinatario.setEditable(false);
        add(txtDireccionDestinatario);

        JLabel lblCodigoDepartamento = new JLabel("Código del Departamento:");
        lblCodigoDepartamento.setBounds(20, 260, 200, 20);
        add(lblCodigoDepartamento);

        txtCodigoDepartamento = new JTextField();
        txtCodigoDepartamento.setBounds(270, 260, 200, 25);
        txtCodigoDepartamento.setEditable(false);
        add(txtCodigoDepartamento);

        JLabel lblNombreDepartamento = new JLabel("Nombre del Departamento:");
        lblNombreDepartamento.setBounds(20, 300, 200, 20);
        add(lblNombreDepartamento);

        txtNombreDepartamento = new JTextField();
        txtNombreDepartamento.setBounds(270, 300, 200, 25);
        txtNombreDepartamento.setEditable(false);
        add(txtNombreDepartamento);

        JLabel lblFechaSalida = new JLabel("Fecha y Hora de Salida:");
        lblFechaSalida.setBounds(20, 340, 200, 20);
        add(lblFechaSalida);

        txtFechaSalida = new JTextField();
        txtFechaSalida.setBounds(270, 340, 200, 25);
        txtFechaSalida.setEditable(false);
        add(txtFechaSalida);

        // Campo para ingresar referencia
        JLabel lblstatus = new JLabel("Estado del paquete");
        lblstatus.setBounds(20, 380, 250, 20);
        add(lblstatus);

        txtstatus = new JTextField();
        txtstatus.setBounds(270, 380, 200, 25);
        txtstatus.setEditable(false);
        add(txtstatus);



        // Acción del botón buscar
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String referencia = txtReferencia.getText();
                buscarPaquete(referencia);
            }
        });

        //accion salir del programa
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new FormLogin().setVisible(true);
            }
        });
    }

    // Método para buscar el paquete en la base de datos
    public void buscarPaquete(String referencia) {

        String consultaSQL = "SELECT c.nombre AS nombre_camionero, ca.matricula, p.descripcion, " +
                "p.destinatario, p.direccion_destinatario, d.codigo_departamento, " +
                "d.nombre AS nombre_departamento, v.fecha_salida, cm.descripcion AS status" +
                " FROM Viaje v " +
                " JOIN Camionero c ON v.DPI_camionero = c.DPI " +
                " JOIN Camion ca ON v.matricula_camion = ca.matricula " +
                " JOIN Paquete p ON v.id_viaje = p.codigo_paquete " +
                " JOIN Departamento d ON p.codigo_departamento = d.codigo_departamento " +
                " JOIN Catalogo_Motivo_No_Entrega cm ON p.MotivoNoEntrega = cm.id_motivo"+
                " WHERE v.id_viaje = ?";

        try (
             PreparedStatement stmt = con.prepareStatement(consultaSQL)) {

            // Asignar la referencia a los parámetros de la consulta
            stmt.setString(1, referencia);

            // Ejecutar la consulta
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Mostrar los resultados en los campos de texto
                txtNombreCamionero.setText(rs.getString("nombre_camionero"));
                txtMatricula.setText(rs.getString("matricula"));
                txtDescripcionPaquete.setText(rs.getString("descripcion"));
                txtDestinatario.setText(rs.getString("destinatario"));
                txtDireccionDestinatario.setText(rs.getString("direccion_destinatario"));
                txtCodigoDepartamento.setText(rs.getString("codigo_departamento"));
                txtNombreDepartamento.setText(rs.getString("nombre_departamento"));
                txtFechaSalida.setText(rs.getTimestamp("fecha_salida").toString());
                txtstatus.setText(rs.getString("status"));
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron resultados para la referencia proporcionada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
