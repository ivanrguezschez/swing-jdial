package com.irs.swing.jdial;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComponent;

/**
 * Componente swing JDial 
 * 
 * @author IRS
 * @version 1.0.0
 */
public class JDial extends JComponent {
    
    /** Valor mínimo. */
    private int minValue;
    
    /** Valor máximo. */
    private int maxValue;
    
    /** Valor actual del dial. */
    private int value;
    
    /** Radio del dial. */
    private int radio;

    /**
     * Constructor por defecto.
     */
    public JDial() {
        this(0, 100, 0);
    }

    /**
     * Constructor.
     * 
     * @param minValue valor mínimo.
     * @param maxValue valor máximo.
     * @param value valor actual del dial.
     */
    public JDial(int minValue, int maxValue, int value) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = value;
        
        setForeground(Color.lightGray);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                spin(e);
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                spin(e);
            }
        });
    }
    
    protected void spin(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        
        double th = Math.atan((1.0 * y - radio) / (x - radio));
        int valor = ((int) (th / (2 * Math.PI) * (maxValue - minValue)));
        if (x < radio) {
            setValue(value + maxValue / 2);
        } else if (y < radio) {
            setValue(value + maxValue);
        } else {
            setValue(valor);   
        }
    }
    
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int tick = 10;
        radio = getSize().width / 2 - tick;
        g2.setPaint(getForeground().darker());
        g2.drawLine(radio * 2 + tick / 2, radio, radio * 2 + tick, radio);
        g2.setStroke(new BasicStroke(2));
        draw3DCircle(g2, 0, 0, radio, true);
        int knobRadius = radio / 7;
        double th = value * (2 * Math.PI) / (maxValue - minValue);
        int x = (int) (Math.cos(th) * radio - knobRadius * 3);
        int y = (int) (Math.sin(th) * radio - knobRadius * 3);
	g2.setStroke(new BasicStroke(1));
        draw3DCircle(g2, x + radio - knobRadius, y + radio - knobRadius, knobRadius, false);
    }
    
    private void draw3DCircle(Graphics g, int x, int y, int radio, boolean raised) {
        Color foreground = getForeground();
        Color light = foreground.brighter();
        Color dark = foreground.darker();
        
        g.setColor(foreground);
        g.fillOval(x, y, radio * 2, radio * 2);
        g.setColor(raised ? light : dark);
        g.drawArc(x, y, radio * 2, radio * 2, 45, 180);
        
        g.setColor(raised ? dark : light);
        g.drawArc(x, y, radio * 2, radio * 2, 225, 180);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 100);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        firePropertyChange("value", this.value, value);
        this.value = value;
        repaint();
        fireEvent();
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
    
    public void addDialListener(DialListener listener) {
        listenerList.add(DialListener.class, listener);
    }
    
    public void removeDialListener(DialListener listener) {
        listenerList.remove(DialListener.class, listener);
    }
    
    private void fireEvent() {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i++) {
            if (listeners[i] == DialListener.class) {
                ((DialListener) listeners[i + 1]).dialAdjusted(new DialEvent(this, value));
            }
        }
    }
    
}
