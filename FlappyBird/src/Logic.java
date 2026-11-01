import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Logic implements ActionListener, KeyListener, MouseListener {
    // ===== Ukuran layar =====
    int frameWidth = 360;
    int frameHeight = 640;

    // ===== Ukuran dan posisi awal pemain =====
    int playerWidth = 34;
    int playerHeight = 24;

    // Posisi awal tepat di tengah layar
    int playerStartPosX = (frameWidth / 2) - (playerWidth / 2);
    int playerStartPosY = (frameHeight / 2);

    // ===== Ukuran dan posisi pipa =====
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    // Timer utama game dan timer kemunculan pipa
    Timer gameLoop;
    Timer pipesCooldown;
    int gravity = 1;

    // Kecepatan gerak pipa ke kiri
    int pipeVelocityX = -2;

    // ===== Objek permainan =====
    View view;
    Image birdImage;
    Player player;

    Image backgroundImg;
    Background background;

    Image footerImg;
    Footer footer;

    // Skor HUD di atas layar
    Score score;

    // ===== Main menu (2 tombol) =====
    Sprite startButton;
    int startWidth = 150;
    int startHeight = 60;
    Image startImage;

    Sprite exitButton;
    int exitWidth = 150;
    int exitHeight = 60;
    Image exitImage;

    // ===== Tampilan game over =====
    Sprite gameover;
    int gameoverWidth = 192;
    int gameoverHeight = 42;
    Image gameoverImage;

    // Penanda state permainan
    boolean inMenu = true;
    boolean gameOver = false;

    // Daftar pipa dan gambarnya
    Image lowerPipeImage;
    Image upperPipeImage;
    ArrayList<Pipe> pipes;

    // ===== Efek suara =====
    Sound wingSound;
    Sound swooshSound;
    Sound pointSound;
    Sound hitSound;
    Sound bgmSound;

    // Constructor: memuat semua gambar dan suara, lalu menyiapkan timer
    public Logic() {
        // Background
        backgroundImg = new ImageIcon(getClass().getResource("assets/sprites/background-night.png")).getImage();
        background = new Background(0, 0, frameWidth, frameHeight, -1, backgroundImg);

        // Footer / tanah
        footerImg = new ImageIcon(getClass().getResource("assets/sprites/base.png")).getImage();
        footer = new Footer(0, frameHeight-75, frameWidth, 75, -2, footerImg);

        // Burung
        birdImage = new ImageIcon(getClass().getResource("assets/sprites/bird.png")).getImage();
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);

        // Pipa
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/sprites/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/sprites/upperPipe.png")).getImage();
        pipes = new ArrayList<Pipe>();

        // Suara: dimuat & di-warm-up di awal supaya responsif
        wingSound = new Sound("assets/audio/wing.wav", 4);
        swooshSound = new Sound("assets/audio/swoosh.wav", 1);
        pointSound = new Sound("assets/audio/point.wav", 2);
        hitSound = new Sound("assets/audio/hit.wav", 1);
        bgmSound = new Sound("assets/audio/Flappy Bird Theme Song.wav", 1);
        // Mulai putar BGM di main menu
        if (bgmSound != null) bgmSound.loop();

        // Skor (disembunyikan selama di main menu)
        score = new Score(30);
        score.setVisible(false);

        // Main menu: hanya 2 tombol
        startImage = new ImageIcon(getClass().getResource("assets/sprites/start.png")).getImage();
        startButton = new Sprite(frameWidth/2 - startWidth/2, frameHeight - frameHeight/2 + 50,
                startWidth, startHeight, startImage);

        exitImage = new ImageIcon(getClass().getResource("assets/sprites/exit.png")).getImage();
        exitButton = new Sprite(frameWidth/2 - exitWidth/2, frameHeight - frameHeight/2 + 100,
                exitWidth, exitHeight, exitImage);

        // Tampilan game over
        gameoverImage = new ImageIcon(getClass().getResource("assets/sprites/gameover.png")).getImage();
        gameover = new Sprite(frameWidth/2 - gameoverWidth/2, 183,
                gameoverWidth, gameoverHeight, gameoverImage);

        // Timer kemunculan pipa setiap 1,7 detik
        pipesCooldown = new Timer(1700, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Pipa");
                placePipes();
            }
        });

        // Timer utama ±60 FPS
        gameLoop = new Timer(1000 / 60, this);
        // gameLoop & pipesCooldown baru dijalankan saat game dimulai (klik Continue)
    }

    // ===== Setter & getter =====
    public void setView(View view) { this.view = view; }
    public Player getPlayer() { return player;}
    public Background getBackground() {return background;}
    public Footer getFooter() {return footer;}
    public Score getScore() {return score;}
    public Sprite getContinueButton() {return startButton;}
    public Sprite getExitButton() {return exitButton;}
    public Sprite getGameover() {return gameover;}

    public ArrayList<Pipe> getPipes() {return pipes;}

    // Membuat sepasang pipa (atas dan bawah) dengan celah acak
    public void placePipes() {
        int openingSpace = pipeHeight/4;
        int playableHeight = frameHeight - footer.getHeight();

        int minGapY = 50;
        int maxGapY = playableHeight - openingSpace - 50;

        int randomGapY = minGapY + (int)(Math.random() * (maxGapY - minGapY));
        int randomPosY = randomGapY - pipeHeight;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPosY + openingSpace + pipeHeight, pipeWidth,
                pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    // Setter & getter status game over
    public void setGameOver(boolean gameOver) {this.gameOver = gameOver;}
    public boolean isGameOver() {return gameOver;}

    // Getter status main menu
    public boolean isInMenu() {return inMenu;}

    // Mengecek apakah pemain masih berada di atas tanah
    public boolean checkBounds() {
        if(player.getPosY() >= frameHeight - playerHeight - footer.getHeight()) return false;
        return true;
    }

    // Deteksi tabrakan antara pemain dan satu pipa (AABB sederhana)
    public boolean isHitPipe(Pipe pipe) {
        int m = 0;  // margin hitbox
        return player.getPosX() + m < pipe.getPosX() + pipe.getWidth()
                && player.getPosX() + player.getWidth() > pipe.getPosX()
                && player.getPosY() + m < pipe.getPosY() + pipe.getHeight()
                && player.getPosY() + player.getHeight() > pipe.getPosY();
    }

    // Mengakhiri permainan: hentikan timer dan tampilkan instruksi restart
    public void endgame() {
        player.setPosY(frameHeight - playerHeight - footer.getHeight());
        player.setVelocityY(0);
        setGameOver(true);
        if (view != null) view.setRestartLabelVisible(true);
        gameLoop.stop();
        pipesCooldown.stop();
    }

    // Memulai permainan, baik dari main menu maupun setelah game over
    public void startGame() {
        // Swoosh hanya saat transisi dari main menu ke game
        if (inMenu && swooshSound != null) swooshSound.play();

        inMenu = false;
        setGameOver(false);
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes.clear();
        score.setScore(0);
        score.setVisible(true);
        if (view != null) view.setRestartLabelVisible(false);
        // Hentikan BGM main menu saat game dimulai
        if (bgmSound != null) bgmSound.stop();
        pipesCooldown.restart();
        gameLoop.start();
    }

    // Restart setelah game over
    public void resetGame() {
        startGame();
    }

    // Memperbarui seluruh pergerakan game setiap frame
    public void move() {
        if(isInMenu() || isGameOver()) return;

        // Geser background dan footer
        background.setPosX(background.getPosX() + background.getVelocityX());
        if(background.getPosX() + background.getWidth() == 0) background.setPosX(0);

        footer.setPosX(footer.getPosX() + footer.getVelocityX());
        if(footer.getPosX() + background.getWidth() == 0) footer.setPosX(0);

        // Terapkan gravitasi ke pemain
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(Math.max(0, player.getPosY() + player.getVelocityY()));

        // Geser pipa, cek tabrakan, dan hitung skor
        for(int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipeVelocityX);

            if(isHitPipe(pipe)) {
                if (hitSound != null) hitSound.play();
                endgame();
                return;
            }

            if(!pipe.getPassed() && player.getPosX() > pipe.getPosX() + pipeWidth) {
                pipe.setPassed(true);

                // Skor hanya bertambah sekali per pasang pipa (pipa bawah)
                if(pipe.getPosY() > 0) {
                    score.setScore(score.getScore() + 1);
                    if (pointSound != null) pointSound.play();
                }
            }
        }

        // Jika menyentuh tanah, game berakhir
        if(!checkBounds()) {
            endgame();
        }
    }

    // Dipanggil gameLoop setiap frame
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        if (view != null) {
            view.repaint();
        }
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    // Input keyboard: Space untuk terbang, R untuk restart saat game over
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (isInMenu()) return; // di main menu, space tidak melakukan apa-apa

            if (!isGameOver()) {
                player.setVelocityY(-9);
                playWingSound();
            }
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_R && isGameOver()) {
            resetGame();
        }
    }

    // Putar suara kepakan sayap
    private void playWingSound() {
        if (wingSound == null) return;
        wingSound.play();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    // ===== Mouse: tombol Continue & Exit di main menu =====
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!isInMenu()) return;

        if (isInside(startButton, e.getX(), e.getY())) {
            startGame();
        } else if (isInside(exitButton, e.getX(), e.getY())) {
            System.exit(0);
        }
    }

    // Cek apakah koordinat mouse berada di dalam sebuah Sprite
    private boolean isInside(Sprite sprite, int x, int y) {
        return x >= sprite.getPosX() && x <= sprite.getPosX() + sprite.getWidth()
                && y >= sprite.getPosY() && y <= sprite.getPosY() + sprite.getHeight();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
