/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Enums.EstadoProceso;
import Enums.TipoProceso;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author jorge
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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

    @JsonCreator
    public Proceso(
            @JsonProperty("id") int id,
            @JsonProperty("nombre") String nombre,
            @JsonProperty("tipo") Object tipo, // acepta String o enum
            @JsonProperty("instrucciones") int instrucciones,
            @JsonProperty("ciclosParaExcepcion") int ciclosParaExcepcion,
            @JsonProperty("ciclosParaSatisfacerExcepcion") int ciclosParaSatisfacerExcepcion,
            @JsonProperty("prioridad") Integer prioridadLegacy, // compat: antes venía en Proceso
            @JsonProperty("pcb") PCB pcb
    ) {
        this.id = id;
        this.nombre = nombre;

        switch (tipo) {
            case String string -> this.tipo = TipoProceso.fromString(string);
            case TipoProceso tipoProceso -> this.tipo = tipoProceso;
            default -> this.tipo = TipoProceso.CPU_BOUND;
        }

        this.instrucciones = instrucciones;
        this.ciclosParaExcepcion = ciclosParaExcepcion;
        this.ciclosParaSatisfacerExcepcion = ciclosParaSatisfacerExcepcion;

        if (pcb == null) {
            pcb = new PCB();
        }
        if (prioridadLegacy != null) {
            pcb.setPrioridad(prioridadLegacy);
        }
        this.pcb = pcb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoProceso getTipoEnum() {
        return tipo;
    }

    public void setTipoEnum(TipoProceso tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo.toString();
    }

    public void setTipo(String tipoStr) {
        this.tipo = TipoProceso.fromString(tipoStr);
    }

    public int getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(int instrucciones) {
        this.instrucciones = instrucciones;
    }

    public int getCiclosParaExcepcion() {
        return ciclosParaExcepcion;
    }

    public void setCiclosParaExcepcion(int v) {
        this.ciclosParaExcepcion = v;
    }

    public int getCiclosParaSatisfacerExcepcion() {
        return ciclosParaSatisfacerExcepcion;
    }

    public void setCiclosParaSatisfacerExcepcion(int v) {
        this.ciclosParaSatisfacerExcepcion = v;
    }

    public PCB getPcb() {
        return pcb;
    }

    public void setPcb(PCB pcb) {
        this.pcb = pcb == null ? new PCB() : pcb;
    }

    public int getPrioridad() {
        return pcb.getPrioridad();
    }

    public void setPrioridad(int prio) {
        pcb.setPrioridad(prio);
    }

    public int getPc() {
        return pcb.getPc();
    }

    public void setPc(int pc) {
        pcb.setPc(pc);
    }

    public int getMar() {
        return pcb.getIr();
    }

    public void setMar(int ir) {
        pcb.setIr(ir);
    }

    public int getTiempoEspera() {
        return pcb.getTiempoEspera();
    }

    public void setTiempoEspera(int t) {
        pcb.setTiempoEspera(t);
    }

    public String getEstado() {
        return pcb.getEstado().name();
    }

    public void setEstado(String estado) {
        if (estado == null) {
            return;
        }
        switch (estado.trim().toUpperCase()) {
            case "EJECUTANDO":
                pcb.setEstado(EstadoProceso.EJECUTANDO);
                break;
            case "BLOQUEADO":
                pcb.setEstado(EstadoProceso.BLOQUEADO);
                break;
            case "SALIDA":
                pcb.setEstado(EstadoProceso.SALIDA);
                break;
            default:
                pcb.setEstado(EstadoProceso.LISTO);
        }
    }

    public EstadoProceso getEstadoEnum() {
        return pcb.getEstado();
    }

    public void setEstadoEnum(EstadoProceso e) {
        pcb.setEstado(e);
    }

    @JsonProperty("pc")
    private void setPcLegacy(int pc) {
        if (this.pcb == null) {
            this.pcb = new PCB();
        }
        this.pcb.setPc(pc);
    }

    @JsonProperty("mar")
    private void setMarLegacy(int mar) {
        if (this.pcb == null) {
            this.pcb = new PCB();
        }
        this.pcb.setIr(mar); // mar → ir
    }

    @JsonProperty("tiempoEspera")
    private void setTiempoEsperaLegacy(int t) {
        if (this.pcb == null) {
            this.pcb = new PCB();
        }
        this.pcb.setTiempoEspera(t);
    }

    @JsonProperty("estado")
    private void setEstadoLegacy(String e) {
        setEstado(e);
    }

    @JsonProperty("prioridad")
    private void setPrioridadLegacy(int p) {
        if (this.pcb == null) {
            this.pcb = new PCB();
        }
        this.pcb.setPrioridad(p);
    }

    public boolean esIO() {
        return tipo == TipoProceso.IO_BOUND;
    }

    public int restantes() {
        return instrucciones - pcb.getIr();
    }

    @Override
    public String toString() {
        return String.format(
                "Proceso{id=%d, nombre='%s', tipo=%s, estado=%s, pc=%d, ir=%d, prio=%d, espera=%d}",
                id, nombre, tipo, pcb.getEstado(), pcb.getPc(), pcb.getIr(),
                pcb.getPrioridad(), pcb.getTiempoEspera()
        );
    }
}
