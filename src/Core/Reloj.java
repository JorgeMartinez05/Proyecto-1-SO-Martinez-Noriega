/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Moises Liota
 */
public class Reloj extends Thread {

    private final Semaphore mutex;
    private final Planificador planificador;
    private int ciclo;
    private volatile boolean running = true;

    public Reloj(Semaphore mutex, Planificador planificador) {
        this.mutex = mutex;
        this.planificador = planificador;
        this.ciclo = 0;
    }

    public void shutdown() {
        running = false;
        this.interrupt();
    }
}
