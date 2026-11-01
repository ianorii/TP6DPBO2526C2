import javax.swing.*;
import java.awt.*;

public class Score extends JLabel {
    // Nilai skor saat ini
    private int score;

    // Constructor: fontSize menentukan besar teks skor
    public Score(int fontSize) {
        super("0");
        this.score = 0;

        setFont(new Font("Arial", Font.BOLD, fontSize));
        setForeground(Color.WHITE);
        setOpaque(false);     // latar transparan, mengikuti gambar di belakangnya
        setFocusable(false);  // JLabel tidak perlu menerima fokus keyboard
    }

    // Setter & getter skor
    public void setScore(int score) {
        this.score = score;
        setText(String.valueOf(score));
    }

    public int getScore() {return score;}
}
