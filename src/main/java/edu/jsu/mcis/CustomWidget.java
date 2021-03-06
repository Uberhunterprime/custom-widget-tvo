package edu.jsu.mcis;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class CustomWidget extends JPanel implements MouseListener {
    private java.util.List<ShapeObserver> observers;
    
    
    private final Color SELECTED_COLOR = Color.blue;
    private final Color DEFAULT_COLOR = Color.yellow;
    private boolean selected;
    private Point[] vertex;
	//private Point2[] vertex2;

    
    public CustomWidget() {
        observers = new ArrayList<>();
        
        selected = false;
        vertex = new Point[12];
        //vertex2 -= new Point[12];
        for(int i = 0; i < vertex.length; i++) { vertex[i] = new Point(); }
        Dimension dim = getPreferredSize();
        calculateVertices(dim.width, dim.height);
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(this);
    }

    
    public void addShapeObserver(ShapeObserver observer) {
        if(!observers.contains(observer)) observers.add(observer);
    }
    public void removeShapeObserver(ShapeObserver observer) {
        observers.remove(observer);
    }
    private void notifyObservers() {
        ShapeEvent event = new ShapeEvent(selected);
        for(ShapeObserver obs : observers) {
            obs.shapeChanged(event);
        }
    }
    
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    private void calculateVertices(int width, int height) {
        // Square size should be half of the smallest dimension (width or height).
        int side = Math.min(width, height) / 2;
        
		Point[] sign = {new Point(-1, -1), new Point(0, -2), new Point(1, -1), new Point(1, 1), new Point(0, 2), new Point(-1, 1)};
        for(int i = 0; i < vertex.length; i++) {
            vertex[i].setLocation(width/2 + sign[i].x * side/2, 
                                  height/2 + sign[i].y * side/2);
        }
		
		Point[] sign2 = {new Point(-1, -1), new Point(-.5, -2), new Point(.5, -2), new Point(1, -1), new Point(1, 1), new Point(.5, 2), new Point(-.5, -2), new Point(-1, 1)};
        for(int i = 0; i < vertex.length; i++) {
            vertex[i].setLocation(width/2 + sign2[i].x * side/2, 
                                  height/2 + sign2[i].y * side/2);
        }
		
		
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        calculateVertices(getWidth(), getHeight());
        Shape hex = getShapes();
        Shape oct = getShapes();
		
        g2d.setColor(Color.black);
        g2d.draw(hex);
        if(selected) {
            g2d.setColor(SELECTED_COLOR);
            g2d.fill(hex);
        }
        g2d.setColor(Color.black);
        g2d.draw(oct);
        else if(selected) {
            g2d.setColor(SELECTED_COLOR);
            g2d.fill(oct);
        }
        else {
            g2d.setColor(DEFAULT_COLOR);
            g2d.fill(hex);
            g2d.fill(oct);              
        }
    }

    public void mouseClicked(MouseEvent event) {
        Shape hex = getShapes();
        Shape oct = getShapes();
        if(shape.contains(event.getX(), event.getY())) {
            selected = !selected;
            notifyObservers();
        }
        else if(shape2.contains(event.getX(), event.getY())) {
            selected = !selected;
            notifyObservers();
        }
        repaint(shape.getBounds());
    }
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
    
    public Shape getShapes() {
        int[] x = new int[vertex.length];
        int[] y = new int[vertex.length];
        for(int i = 0; i < vertex.length; i++) {
            x[i] = vertex[i].x;
            y[i] = vertex[i].y;
        }
        Shape hex = new Polygon(x, y, vertex.length);
        Shape oct -= new Polygon(x, y, vertex.length);
        return shape;
    }
    public boolean isSelected() { return selected; }



	public static void main(String[] args) {
		JFrame window = new JFrame("Custom Widget");
        window.add(new CustomWidget());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(300, 300);
        window.setVisible(true);
	}
}
