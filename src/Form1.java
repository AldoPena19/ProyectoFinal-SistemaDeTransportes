import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Form1 extends JFrame {
    private int t = 0;
    private JProgressBar progressBar;
    private JLabel label1, label3;
    private Timer timer;

    public Form1() {
        setTitle("Cargando...");
        setSize(420, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Crear y configurar los componentes
        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(105);
        progressBar.setValue(0);
        progressBar.setBounds(100, 100, 200, 30);

        label1 = new JLabel("");
        label1.setBounds(100, 150, 200, 30);

        label3 = new JLabel("");
        label3.setBounds(100, 200, 200, 30);

        add(progressBar);
        add(label1);
        add(label3);

        timer = new Timer(60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (t <= 105) {
                    progressBar.setValue(t);
                    t++;
                } else {
                    timer.stop();
                    dispose();  
                    new FormLogin().setVisible(true); 
                }

                if (t == 12) {
                    label3.setText("¡Bienvenido!");
                }

                if (t == 45) {
                    label3.setText("Espere un momento");
                }

                if (t == 82) {
                    label1.setText("Sistema envios Tubus en Guatemala");
                }
            }
        });
    }

    public void startLoading() {
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Form1 form = new Form1();
            form.setVisible(true);
            form.startLoading();
        });
    }
}
