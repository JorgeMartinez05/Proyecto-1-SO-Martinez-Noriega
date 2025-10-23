/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Politicas;

import EDD.Lista;
import EDD.Nodo;
import Modelos.Proceso;

/**
 *
 * @author jorge
 */
public final class FIFO {
    private FIFO() {}

    // Para UI mantenemos “fairness” por espera descendente
    public static void ordenar(Lista ready) {
        if (ready.getSize() <= 1) return;
        boolean swap;
        do {
            swap = false;
            Nodo cur = ready.getpFirst();
            while (cur != null && cur.getPnext() != null) {
                Proceso a = (Proceso) cur.getDato();
                Proceso b = (Proceso) cur.getPnext().getDato();
                if (a.getTiempoEspera() < b.getTiempoEspera()) {
                    cur.setDato(b);
                    cur.getPnext().setDato(a);
                    swap = true;
                }
                cur = cur.getPnext();
            }
        } while (swap);
    }
}
