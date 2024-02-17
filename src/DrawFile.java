import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DrawFile extends JPanel {

    private File file;
    private Vertex[] vertices;
    private Edge[] edges;

    public DrawFile(String fileName) {
        this.file = new File(fileName);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        drawAxes(g, width, height);
        try {
            Scanner scanner = new Scanner(this.file);
            int numberVertexes = readNumberOfVertexes(scanner);
            readVertices(scanner, numberVertexes);
            int numberEdges = readNumberOfEdges(scanner);
            readEdges(scanner, numberEdges);
            scanner.close();

            drawEdges(g2d, width, height);

        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        }
    }

    private void drawAxes(Graphics g, int width, int height) {
        g.setColor(Color.RED);
        g.drawLine(width / 2, 0, width / 2, height);
        g.drawLine(0, height / 2, width, height / 2);
    }

    private int readNumberOfVertexes(Scanner scanner) {
        return Integer.parseInt(scanner.nextLine());
    }

    private void readVertices(Scanner scanner, int numberVertexes) {
        vertices = new Vertex[numberVertexes];
        for (int i = 0; i < numberVertexes; i++) {
            String[] splitText = scanner.nextLine().split(" ");
            int x = Integer.parseInt(splitText[0]);
            int y = Integer.parseInt(splitText[1]);
            vertices[i] = new Vertex(x, y);
        }
    }

    private int readNumberOfEdges(Scanner scanner) {
        return Integer.parseInt(scanner.nextLine());
    }

    private void readEdges(Scanner scanner, int numberEdges) {
        edges = new Edge[numberEdges];
        for (int i = 0; i < numberEdges; i++) {
            String[] splitText = scanner.nextLine().split(" ");
            int startIndex = Integer.parseInt(splitText[0]);
            int endIndex = Integer.parseInt(splitText[1]);
            edges[i] = new Edge(vertices[startIndex], vertices[endIndex]);
        }
    }

    private void drawEdges(Graphics2D g2d, int width, int height) {
        g2d.setColor(Color.BLACK);
        for (Edge edge : edges) {
            int x1 = edge.getStart().getX() + width / 2;
            int y1 = height / 2 - edge.getStart().getY();
            int x2 = edge.getEnd().getX() + width / 2;
            int y2 = height / 2 - edge.getEnd().getY();
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

    public static void main(String[] args) {

        Scanner scannerFile = new Scanner(System.in);
        System.out.println("Ingrese ruta del archivo a dibujar");
        String filePath = scannerFile.nextLine();
        scannerFile.close();

        JFrame frame = new JFrame("File Drawer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DrawFile drawFile = new DrawFile(filePath);
        frame.add(drawFile);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
