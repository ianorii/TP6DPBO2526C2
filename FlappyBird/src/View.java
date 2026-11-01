import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JPanel {
    private int width = 360;
    private int height = 640;

    private Logic logic;
    private JLabel restartLabel;

    // Constructor: menyiapkan komponen label dan listener input
    public View(Logic logic) {
        this.logic = logic;
        setPreferredSize(new Dimension(width, height));
        setLayout(null); // supaya JLabel bisa diposisikan dengan setBounds

        // Skor HUD di atas layar, rata tengah
        Score score = logic.getScore();
        score.setHorizontalAlignment(SwingConstants.CENTER);
        score.setBounds(0, 10, width, 40);
        add(score);

        // Tulisan instruksi restart saat game over
        restartLabel = new JLabel("Tekan \"R\" untuk restart", SwingConstants.CENTER);
        restartLabel.setFont(new Font("Arial", Font.BOLD, 20));
        restartLabel.setForeground(Color.WHITE);
        restartLabel.setBounds(0, 260, width, 30);
        restartLabel.setVisible(false);
        add(restartLabel);

        setFocusable(true);
        addKeyListener(logic);
        addMouseListener(logic);
    }

    // Dipanggil Logic saat masuk / keluar dari game over
    public void setRestartLabelVisible(boolean visible) {
        restartLabel.setVisible(visible);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // Menggambar seluruh isi layar game
    public void draw(Graphics g) {
        // Background (digambar dua kali agar tampak menyambung saat bergeser)
        Background background = logic.getBackground();
        g.drawImage(background.getImage(), background.getPosX(), background.getPosY(),
                background.getWidth(), background.getHeight(), null);

        g.drawImage(background.getImage(), background.getPosX() + background.getWidth(),
                background.getPosY(), background.getWidth(), background.getHeight(), null);

        // Semua pipa
        ArrayList<Pipe> pipes = logic.getPipes();
        if(pipes != null) {
            for(int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(),
                        pipe.getWidth(), pipe.getHeight(), null);
            }
        }

        // Pemain (burung)
        Player player = logic.getPlayer();
        if(player != null) {
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(),
                    player.getWidth(), player.getHeight(), null);
        }

        // Footer / tanah (juga dua kali agar tampak menyambung)
        Footer footer = logic.getFooter();
        g.drawImage(footer.getImage(), footer.getPosX(), footer.getPosY(),
                footer.getWidth(), footer.getHeight(), null);

        g.drawImage(footer.getImage(), footer.getPosX()+footer.getWidth(), footer.getPosY(),
                footer.getWidth(), footer.getHeight(), null);

        // Main menu: tampil pertama kali saat game dijalankan (2 tombol)
        if(logic.isInMenu()) {
            Sprite continueBtn = logic.getContinueButton();
            g.drawImage(continueBtn.getImage(), continueBtn.getPosX(), continueBtn.getPosY(),
                    continueBtn.getWidth(), continueBtn.getHeight(), null);

            Sprite exitBtn = logic.getExitButton();
            g.drawImage(exitBtn.getImage(), exitBtn.getPosX(), exitBtn.getPosY(),
                    exitBtn.getWidth(), exitBtn.getHeight(), null);
            return;
        }

        // Tampilan ketika game over
        if(logic.isGameOver()) {
            Sprite gameover = logic.getGameover();
            g.drawImage(gameover.getImage(), gameover.getPosX(), gameover.getPosY(),
                    gameover.getWidth(), gameover.getHeight(), null);
        }
    }
}
