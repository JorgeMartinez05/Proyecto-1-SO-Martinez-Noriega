/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import EDD.Lista;
import EDD.Nodo;
import Interfaces.VistaSimulacion;
import Modelos.Proceso;
import Politicas.FIFO;

/**
 *
 * @author Moises Liota
 */
public final class Planificador {

    private final Lista readyList;
    private final Lista blockedList;
    private final Lista exitList;
    private final Lista allProcessList;

    private final Simulador controlador; // tu UI/“Simulador”
    private int selectedAlgorithm = -1;

    public Planificador(Lista readyList, Lista blockedList, Lista exitList,
            Lista allProcess, Simulador controlador) {
        this.controlador = controlador;
        this.readyList = readyList;
        this.blockedList = blockedList;
        this.exitList = exitList;
        this.allProcessList = allProcess;
        setSelectedAlgorithm(controlador.getPolitica());
    }

    // ===== getters =====
    public Lista getReadyList() {
        return readyList;
    }

    public Lista getBlockedList() {
        return blockedList;
    }

    public Lista getExitList() {
        return exitList;
    }

    public Lista getAllProcessList() {
        return allProcessList;
    }

    public int getSelectedAlgorithm() {
        return selectedAlgorithm;
    }

    public void setSelectedAlgorithm(int idx) {
        this.selectedAlgorithm = idx;
        ordenarReady(); // reordenar al cambiar
    }

    private void ordenarReady() {
        switch (selectedAlgorithm) {
            case 0 ->
                FIFO.ordenar(readyList);
            default ->
                FIFO.ordenar(readyList);
        }
    }

    /**
     * Devuelve y quita el próximo proceso listo.
     * @return 
     */
    public Proceso getProcess() {
        // sincroniza política con la UI si cambió
        if (selectedAlgorithm != controlador.getPolitica()) {
            setSelectedAlgorithm(controlador.getPolitica());
        }
        if (readyList.EsVacio()) {
            updateReadyList();
            updateProcessList();
            return null;
        }
        ordenarReady();
        Proceso out = (Proceso) readyList.getpFirst().getDato();
        readyList.Eliminar_Inicio();
        out.setEstado("EJECUTANDO");  // Proceso mapea a PCB/enum
        updateReadyList();
        updateProcessList();
        return out;
    }

    // ===== Actualizaciones a colas/PCB (igual que ya tenías) =====
    public void updatePCB(Proceso process, int pc, int mar, String state) {
        process.setEstado(state);
        process.setPc(pc);
        process.setMar(mar);
        process.setTiempoEspera(0);
        switch (state.toUpperCase()) {
            case "BLOQUEADO" ->
                blockedList.InsertarFinal(process);
            case "LISTO", "READY" ->
                readyList.InsertarFinal(process);
            default ->
                exitList.InsertarFinal(process);
        }
        updateAllUI();
    }

    public void updatePCB(Proceso process, String state) {
        process.setEstado(state);
        process.setTiempoEspera(0);
        switch (state.toUpperCase()) {
            case "BLOQUEADO" ->
                blockedList.InsertarFinal(process);
            case "LISTO", "READY" ->
                readyList.InsertarFinal(process);
            default ->
                exitList.InsertarFinal(process);
        }
        updateAllUI();
    }

    public void updateWaitingTime() {
        if (selectedAlgorithm != controlador.getPolitica()) {
            setSelectedAlgorithm(controlador.getPolitica());
            updateReadyList();
        }
        Nodo p = readyList.getpFirst();
        while (p != null) {
            Proceso pr = (Proceso) p.getDato();
            pr.setTiempoEspera(pr.getTiempoEspera() + 1);
            p = p.getPnext();
        }
        updateProcessList();
    }

    public void updateBlockToReady(int id) {
        Nodo p = blockedList.getpFirst();
        while (p != null) {
            Proceso pr = (Proceso) p.getDato();
            if (pr.getId() == id) {
                pr.setEstado("LISTO");
                pr.setTiempoEspera(0);
                blockedList.EliminarPorReferencia(pr);
                readyList.InsertarFinal(pr);
                break;
            }
            p = p.getPnext();
        }
        updateBlockedList();
        updateReadyList();
        updateProcessList();
    }

    // ===== UI =====
    private void updateAllUI() {
        updateReadyList();
        updateBlockedList();
        updateexitList();
        updateProcessList();
    }

    public void updateProcessList() {
        Nodo p = allProcessList.getpFirst();
        StringBuilder sb = new StringBuilder();
        while (p != null) {
            Proceso pr = (Proceso) p.getDato();
            sb.append(stringInterfaz(pr));
            p = p.getPnext();
        }
    }

    public void updateReadyList() {
        Nodo p = readyList.getpFirst();
        StringBuilder sb = new StringBuilder();
        while (p != null) {
            Proceso pr = (Proceso) p.getDato();
            sb.append("\n ----------------------------------\n ")
                    .append("ID: ").append(pr.getId())
                    .append("\n Nombre: ").append(pr.getNombre());
            p = p.getPnext();
        }
    }

    public void updateBlockedList() {
        Nodo p = blockedList.getpFirst();
        StringBuilder sb = new StringBuilder();
        while (p != null) {
            Proceso pr = (Proceso) p.getDato();
            sb.append("\n ----------------------------------\n ")
                    .append("ID: ").append(pr.getId())
                    .append("\n Nombre: ").append(pr.getNombre());
            p = p.getPnext();
        }
    }

    public void updateexitList() {
        Nodo p = exitList.getpFirst();
        StringBuilder sb = new StringBuilder();
        while (p != null) {
            Proceso pr = (Proceso) p.getDato();
            sb.append("\n ----------------------------------\n ")
                    .append("Id: ").append(pr.getId())
                    .append("\n Nombre: ").append(pr.getNombre());
            p = p.getPnext();
        }
    }

    public static String stringInterfaz(Proceso p) {
        return "\n ----------------------------------\n Id: " + p.getId()
                + "\n Estado: " + p.getEstado()
                + "\n Nombre: " + p.getNombre()
                + "\n PC: " + p.getPc()
                + "\n MAR: " + p.getMar()
                + "\n Espera: " + p.getTiempoEspera();
    }
}
