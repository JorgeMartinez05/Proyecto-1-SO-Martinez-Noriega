/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Enums;

/**
 *
 * @author jorge
 */
public enum TipoProceso {
    CPU_BOUND,
    IO_BOUND;

    public static TipoProceso fromString(String s) {
        if (s == null) return CPU_BOUND;
        s = s.trim().toUpperCase();
        if (s.contains("IO") || s.contains("I/O")) return IO_BOUND;
        return CPU_BOUND;
    }

    @Override
    public String toString() {
        return this == IO_BOUND ? "I/O Bound" : "CPU Bound";
    }
}
