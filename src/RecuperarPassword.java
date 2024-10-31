import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecuperarPassword extends JFrame implements ActionListener {

    // Instancia de la clase usuarios
    public static Usuario conetar_usuario = new Usuario();

    // Componentes de la interfaz
    JLabel lblUsuario, lblContrasenaNueva, lblConfirmar, lblMensaje;
    JTextField txtUsuario;
    JPasswordField txtContrasenaNueva, txtConfirmar;
    JButton btnActualizar, btnshowPass,btnRegresar;



    //crear ventana de recuperacion de contraseña
    public RecuperarPassword() {


        // Crear componentes
        lblUsuario = new JLabel("Usuario:");
        txtUsuario = new JTextField();
        lblContrasenaNueva = new JLabel("Nueva Contraseña:");
        txtContrasenaNueva = new JPasswordField();
        lblConfirmar = new JLabel("Confirmar Contraseña:");
        txtConfirmar = new JPasswordField();
        btnActualizar = new JButton("Actualizar");
        btnshowPass = new JButton("Mostrar");
        btnRegresar = new JButton("Regresar a login");
        lblMensaje = new JLabel();

        //Configurar componentes
        lblUsuario.setBounds(50, 50, 150, 30);
        txtUsuario.setBounds(215, 50, 150, 30);
        lblContrasenaNueva.setBounds(50, 100, 150, 30);
        txtContrasenaNueva.setBounds(215, 100, 150, 30);
        lblConfirmar.setBounds(50, 150, 175, 30);
        txtConfirmar.setBounds(215, 150, 150, 30);
        btnActualizar.setBounds(50, 200, 150, 30);
        btnshowPass.setBounds(215, 200, 150, 30);
        btnRegresar.setBounds(100, 250, 200, 30);
        //lblMensaje.setBounds(215, 300, 80, 30);

        //Agregar componentes a la ventana
        add(lblUsuario);
        add(txtUsuario);
        add(lblContrasenaNueva);
        add(txtContrasenaNueva);
        add(lblConfirmar);
        add(txtConfirmar);
        add(btnActualizar);
        add(btnshowPass);
        add(btnRegresar);
        //add(lblMensaje);

        //agregar la accion al boton de actualizar
        btnActualizar.addActionListener(this);

        // Configuración de la ventana
        setSize(400, 400);
        setTitle("Recuperar Contraseña");
        setLayout(null);
        setVisible(false);

        // Acción para cerrar la ventana
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        //Accion par mostrar contraseña
        btnshowPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnshowPass.getText().equals("Mostrar")) {
                    txtContrasenaNueva.setEchoChar((char) 0); // Muestra la contraseña
                    txtConfirmar.setEchoChar((char) 0); // Muestra la contraseña
                    btnshowPass.setText("Ocultar");
                } else {
                    txtContrasenaNueva.setEchoChar('*'); // Oculta la contraseña
                    txtConfirmar.setEchoChar('*'); // Oculta la contraseña
                    btnshowPass.setText("Mostrar");
                }
            }
        });

        //Accion para regresar al login
        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new FormLogin().setVisible(true);
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean passnew = false;


        // Aquí va la lógica para actualizar la contraseña
        String usuario = txtUsuario.getText();
        String contrasenaNueva = new String(txtContrasenaNueva.getPassword());
        String confirmarContrasena = new String(txtConfirmar.getPassword());

        if (usuario.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "Por favor, usuario");
        }else if(contrasenaNueva.isEmpty() || confirmarContrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La contraseña nueva no puede estar vacia. ");
        }else {
            // Verificar si las contraseñas coinciden y actualizar la base de datos
            if (contrasenaNueva.equals(confirmarContrasena)) {
                // Lógica para actualizar la contraseña en la base de datos
                passnew = CambioPassword(usuario, contrasenaNueva);
                if (passnew) {
                    JOptionPane.showMessageDialog(this, "Contraseña actualizada exitosamente.");
                    setVisible(false);
                    new FormLogin().setVisible(true);
                }


            } else {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden");
            }
        }


    }

    public boolean CambioPassword(String user, String passwordnew) {
        Connection conexion = null;  // Declaración de la conexión
        ConexionBD conexionBD = new ConexionBD(); //Instancia a la coneccion
        PreparedStatement pst = null;
        ResultSet query = null;
        boolean cambiopass = false;

        try {
            conexion = conexionBD.conectar();
            if (conexion == null) {
                throw new SQLException("No se pudo establecer conexion con la base de datos");
            }



            if (conetar_usuario.validarUsuario(user,passwordnew)){
                //crear consulta SQL
                String sql = "UPDATE Usuario SET password = ? WHERE usuario = ? and status = 'A'";
                pst = conexion.prepareStatement(sql);
                pst.setString(1, passwordnew);
                pst.setString(2, user);

                // Ejecutar el query
                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    cambiopass = true;
                    System.out.println("Contraseña actualizada exitosamente.");
                } else {
                    cambiopass = false;
                    System.out.println("No se actualizó ninguna fila. Verifique el nombre de usuario ingresado.");
                }

            }else {
                JOptionPane.showMessageDialog(this, "Usuario no existe");
            }

        } catch (
                SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Cerrar los recursos
            try {
                if (query != null) query.close();
                if (pst != null) pst.close();
                if (conexion != null) conexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return cambiopass;

    }

}

