package com.irs.swing.jdial;

import java.util.EventObject;

/**
 * Clase que representa el evento que se produce al mover el dial.
 * 
 * @author IRS
 * @version 1.0.0
 */
public class DialEvent extends EventObject {
    
    private int value;
    
    public DialEvent(JDial source, int value) {
        super(source);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
