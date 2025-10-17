/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Enums.EstadoProceso;

/**
 *
 * @author jorge
 */
public class PCB {
   private int pc;                
    private int ir;                
    private int prioridad;
    private int tiempoEspera;
    private EstadoProceso estado;

    public PCB() {
        this.pc = 0;
        this.ir = 0;
        this.prioridad = 0;
        this.tiempoEspera = 0;
        this.estado = EstadoProceso.LISTO;
    } 
}
