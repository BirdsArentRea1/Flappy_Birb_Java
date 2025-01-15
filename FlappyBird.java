import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FlappyBird extends JPanel {

private Rectangle bird;
private int velocity = 0;
private final int GRAVITY = 1;
private final int FLAP_STRENGTH = -15;

    public FlappyBird() {
        bird = new Rectangle(50,250,30,30);

        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(this);
        frame.setVisible(true);

        addMouseListener(this);

        Timer timer = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bird.y += velocity;
                velocity += GRAVITY;    
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(bird.x,bird.y,bird.width,bird.height);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        velocity = FLAP_STRENGTH;
    }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FlappyBird();
            }
        });
    }
}

