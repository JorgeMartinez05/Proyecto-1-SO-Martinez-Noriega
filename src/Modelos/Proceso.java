/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Enums.TipoProceso;

/**
 *
 * @author jorge
 */
public class Proceso {
    private int id;
    private String nombre;
    private TipoProceso tipo;                  
    private int instrucciones;                 
    private int ciclosParaExcepcion;
    private int ciclosParaSatisfacerExcepcion;

    // Estado de ejecución separado:
    private PCB pcb;

    public Proceso() {
        this.id = -1;
        this.nombre = "";
        this.tipo = TipoProceso.CPU_BOUND;
        this.instrucciones = 0;
        this.ciclosParaExcepcion = 0;
        this.ciclosParaSatisfacerExcepcion = 0;
        this.pcb = new PCB();                 
    }
}
