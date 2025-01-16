import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird extends JPanel implements MouseListener {

private Rectangle bird;
private int velocity = 0;
private final int GRAVITY = 1;
private final int FLAP_STRENGTH = -7;
private ArrayList<Rectangle> pipes;
private Random random;
private int pipeSpawnTimer = 0;
private int score = 0;

    public FlappyBird() {
        bird = new Rectangle(50,250,30,30);
        pipes = new ArrayList<>();
        random = new Random();

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

                movePipes();
                spawnPipes();
                checkCollisions();

                repaint();
            }
        });
        timer.start();
    }

    private void movePipes() {
        for (int i = 0; i < pipes.size(); i++) {
            Rectangle pipe = pipes.get(i);
            pipe.x -= 5;

            if (pipe.x + pipe.width < 0) {
                pipe.remove(i);
                i--;
                score++;
            }
        }
    }

    private void spawnPipes() {
        pipeSpawnTimer++;
        if (pipeSpawnTimer >= 100) {
            int pipeHeight = random.nextInt(300) +50;
            int gap = 150;
            pipes.add(new Rectangle(800, 0, 50, pipeHeight));
            pipes.add(new Rectangle(800, pipeHeight + gap, 50, 600 - pipeHeight - gap));
            pipeSpawnTimer = 0;
        }
    }

    private void checkCollisions() {
        for (Rectangle pipe : pipes) {
            if (bird.intersects(pipe)) {
                gameOver();
            }
        }
        if (bird.y < 0 || bird.y + bird.height > 600) {
            gameOver();
        }
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over! Your Score: " + score);
        System.exit(0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.CYAN);
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.RED);
        g.fillRect(bird.x,bird.y,bird.width,bird.height);

        g.setColor(Color.GREEN);
        for (Rectangle pipe : pipes) {
            g.fillRect(pipe.x,pipe.y,pipe.width,pipe.height);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 20);
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

