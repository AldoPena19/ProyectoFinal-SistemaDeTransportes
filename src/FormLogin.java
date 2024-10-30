import java.awt.event.*;
import javax.swing.*;

public class FormLogin extends JFrame implements ActionListener {



    // Componentes de la interfaz
    JLabel labelUsuario, labelContrasena, labelOlvideContrasena;
    JTextField textUsuario;
    JPasswordField textContrasena;
    JButton botonLogin, btnshowPass;

    // Instancia de la clase ConexionBD
    Usuario conetar_usuario = new Usuario();
    //Intancia a recuperar contraseña
    RecuperarPassword Rpass = new RecuperarPassword();



    public FormLogin() {


        // Crear componentes
        labelUsuario = new JLabel("Usuario:");
        labelContrasena = new JLabel("Contraseña:");
        labelOlvideContrasena = new JLabel("¿Olvidé mi contraseña?");
        textUsuario = new JTextField();
        textContrasena = new JPasswordField();
        textContrasena.setEchoChar('*'); // Ocultar el texto de la contraseña
        botonLogin = new JButton("Login");
        btnshowPass = new JButton("Mostrar");


        // Definir la posición de los componentes
        labelUsuario.setBounds(50, 50, 80, 30);
        textUsuario.setBounds(150, 50, 150, 30);
        labelContrasena.setBounds(50, 100, 100, 30);
        textContrasena.setBounds(150, 100, 150, 30);
        botonLogin.setBounds(50, 150, 80, 30);
        btnshowPass.setBounds(150, 150, 150, 30);
        labelOlvideContrasena.setBounds(150, 185, 200, 30); // Posicionar "¿Olvidé mi contraseña?"

        // Añadir los componentes a la ventana
        add(labelUsuario);
        add(textUsuario);
        add(labelContrasena);
        add(textContrasena);
        add(botonLogin);
        add(btnshowPass);
        add(labelOlvideContrasena);

        // Añadir el evento de clic para el botón de login
        botonLogin.addActionListener(this);

        // Añadir el evento para "¿Olvidé mi contraseña?"
        labelOlvideContrasena.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Rpass.setVisible(true);// Mostrar ventana de recuperación
                setVisible(false);
            }
        });

        // Configuración de la ventana
        setSize(400, 300);
        setTitle("Formulario de Login");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLayout(null);
        setVisible(true);

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
                    textContrasena.setEchoChar((char) 0); // Muestra la contraseña
                    btnshowPass.setText("Ocultar");
                } else {
                    textContrasena.setEchoChar('*'); // Oculta la contraseña
                    btnshowPass.setText("Mostrar");
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonLogin) {
            String usuario = textUsuario.getText();
            String contrasena = new String(textContrasena.getText());
            int rol = conetar_usuario.obtenerRol(usuario, contrasena);

            // Validar datos
            if (usuario.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese su usuario y contraseña.");
                textUsuario.setText("");
                textContrasena.setText("");
            } else {
                // Intentar autenticar al usuario
                try {
                    if (conetar_usuario.validarUsuario(usuario, contrasena)) {
                        textUsuario.setText("");
                        textContrasena.setText("");
                        JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso" );

                        if (rol == 1) {
                            // Si la autenticación es exitosa, mostrar un mensaje y cerrar la ventana
                            JOptionPane.showMessageDialog(this, "El rol que tiene es: Adminsitrador");
                            setVisible(false);
                            new MainAppAdmin().setVisible(true);

                        } else if (rol == 2) {
                            // Si la autenticación es exitosa, mostrar un mensaje y cerrar la ventana
                            JOptionPane.showMessageDialog(this, "El rol que tiene es: Camionero");
                            setVisible(false);
                            new MainAppCamionero().setVisible(true);
                        } else if (rol == 3) {
                            // Si la autenticación es exitosa, mostrar un mensaje y cerrar la ventana
                            JOptionPane.showMessageDialog(this, "Bienvenido al Sistema"); // Ingreso Cliente
                            setVisible(false);
                            new BusquedaPaqueteFormulario().setVisible(true);
                        } else if (rol == 4) {
                            // Si la autenticación es exitosa, mostrar un mensaje y cerrar la ventana
                            JOptionPane.showMessageDialog(this, "El rol que tiene es: Operador");
                            setVisible(false);
                            new MainAppOper().setVisible(true);
                        }else {
                            JOptionPane.showMessageDialog(this,"No tiene Rol asignado, comuniquese con el administrador del sistema");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
                    }
                } catch (Exception ex) {
                    // Manejar excepciones
                    JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + ex.getMessage());
                }
            }
        }
    }


    public static void main(String[] args) {
        new FormLogin();
    }

}
