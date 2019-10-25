import javax.swing.*;
import java.awt.*;

class DrawCircle extends JFrame {
    private final int OVAL_WIDTH = 80;
    private final int OVAL_HEIGHT = 80;

    public DrawCircle() {
        super();
        initialize();
    }

    private void initialize() {
        this.setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new DrawPanel());
        this.setTitle("绘图实例");
    }

    class DrawPanel extends JPanel {
        public void paint(Graphics g) {
            super.paint(g);
            System.out.print(WIDTH + "  " + HEIGHT);
            g.drawString("奥运五环", 80, 5);
            g.setColor(Color.red);
            g.drawOval(10, 10, OVAL_WIDTH, OVAL_HEIGHT);
            g.setColor(Color.orange);
            g.drawOval(80, 10, OVAL_WIDTH, OVAL_HEIGHT);
            g.setColor(Color.yellow);
            g.drawOval(150, 10, OVAL_WIDTH, OVAL_HEIGHT);
            g.setColor(Color.green);
            g.drawOval(50, 70, OVAL_WIDTH, OVAL_HEIGHT);
            g.setColor(Color.blue);
            g.drawOval(120, 70, OVAL_WIDTH, OVAL_HEIGHT);
        }
    }
}

public class JavaPaint {


    public static void main(String[] args){
        new DrawCircle().setVisible(true);
    }
}
