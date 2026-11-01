import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // Konfigurasi jendela game
        JFrame frame = new JFrame("FlappyBird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Buat Logic lalu View, kemudian hubungkan View ke Logic
        Logic logika = new Logic();
        View tampilan = new View(logika);
        logika.setView(tampilan);

        // Tampilkan jendela game
        frame.add(tampilan);
        frame.pack();
        frame.setVisible(true);

        // Minta fokus keyboard setelah window benar-benar muncul
        tampilan.requestFocus();
    }
}
