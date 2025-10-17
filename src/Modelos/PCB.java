/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Enums.EstadoProceso;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    
    @JsonCreator
    public PCB(@JsonProperty("pc") int pc,
            @JsonProperty("ir") int ir,
            @JsonProperty("prioridad") int prioridad,
            @JsonProperty("tiempoEspera") int tiempoEspera,
            @JsonProperty("estado") EstadoProceso estado) {
        this.pc = pc;
        this.ir = ir;
        this.prioridad = prioridad;
        this.tiempoEspera = tiempoEspera;
        this.estado = (estado == null) ? EstadoProceso.LISTO : estado;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getIr() {
        return ir;
    }

    public void setIr(int ir) {
        this.ir = ir;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(int tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    public void incTiempoEspera() {
        this.tiempoEspera++;
    }

    public void resetTiempoEspera() {
        this.tiempoEspera = 0;
    }

    public EstadoProceso getEstado() {
        return estado;
    }

    public void setEstado(EstadoProceso estado) {
        this.estado = estado;
    }

    // Utilidades de ejecución
    public void tick() {
        this.pc++;
        this.ir++;
    }
}
