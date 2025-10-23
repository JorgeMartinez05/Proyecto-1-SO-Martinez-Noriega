/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import Interfaces.VistaSimulacion;
import javax.swing.SwingUtilities;

/**
 *
 * @author jorge
 */
public class Simulador {

    private VistaSimulacion vista;

    public Simulador(VistaSimulacion vista) {
        this.vista = vista;
    }

    // ---- Acceso a la vista ----
    public VistaSimulacion getVista() {
        return vista;
    }

    public void setVista(VistaSimulacion vista) {
        this.vista = vista;
    }

    // ---- Helpers de seguridad ----
    private boolean hasView() {
        return this.vista != null;
    }

    private void onEDT(Runnable r) {
        if (r == null) return;
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeLater(r);
        }
    }

    public int getPolitica() {
        return hasView() ? vista.getPolitica() : 0;
    }

    public int getTiempo() {
        return hasView() ? vista.getTiempoInstrucion() : 0;
    }

}
