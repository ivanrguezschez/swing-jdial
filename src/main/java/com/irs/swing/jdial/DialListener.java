package com.irs.swing.jdial;

import java.util.EventListener;

/**
 * Interface de escucha de los enventos del componente JDial.
 * 
 * @author IRS
 * @version 1.0.0
 */
public interface DialListener extends EventListener {
    
    void dialAdjusted(DialEvent e);
}
