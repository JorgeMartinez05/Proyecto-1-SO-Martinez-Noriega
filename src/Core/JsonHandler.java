/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import EDD.Lista;
import Modelos.Proceso;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Moises Liota
 */
public class JsonHandler {
     // Mapper robusto: pretty print + ignora/relaja cosas que podrían romper
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
            .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);

    /**
     * Escribe una EDD.Lista de Proceso a JSON.
     */
    public static void writeProcesosToJson(Lista procesos, String filePath) throws IOException {
        if (procesos == null) throw new IllegalArgumentException("procesos no puede ser null");
        if (filePath == null || filePath.isBlank()) throw new IllegalArgumentException("filePath inválido");

        Proceso[] arr = new Proceso[procesos.getSize()];
        for (int i = 0; i < procesos.getSize(); i++) {
            Object value = procesos.getValor(i);
            arr[i] = (Proceso) value;
        }

        ensureParentDirectory(Path.of(filePath));
        MAPPER.writeValue(new File(filePath), arr);
    }

    /**
     * Lee un archivo JSON (array de Proceso) y lo carga en una EDD.Lista.
     * Si falla, devuelve una lista vacía (y loguea el error).
     */
    public static Lista readProcesosFromJson(String filePath) {
        Lista procesos = new Lista();
        try {
            Proceso[] arr = MAPPER.readValue(new File(filePath), Proceso[].class);
            for (Proceso p : arr) {
                procesos.InsertarFinal(p);
            }
        } catch (IOException e) {
            System.out.println("No se pudieron cargar procesos: " + e.getMessage());
        }
        return procesos;
    }

    /**
     * Guarda un arreglo de enteros como JSON (útil para preferencias/config).
     */
    public static void saveToJson(int[] numberArray, String filePath) {
        if (numberArray == null) throw new IllegalArgumentException("numberArray no puede ser null");
        try {
            ensureParentDirectory(Path.of(filePath));
            MAPPER.writeValue(new File(filePath), numberArray);
        } catch (IOException e) {
            System.err.println("Error guardando arreglo: " + e.getMessage());
        }
    }

    /**
     * Lee un arreglo de enteros desde un archivo JSON.
     */
    public static int[] readFromJson(String filePath) {
        try {
            return MAPPER.readValue(new File(filePath), int[].class);
        } catch (IOException e) {
            System.err.println("Error leyendo arreglo: " + e.getMessage());
            return null;
        }
    }

    // ===== util =====
    private static void ensureParentDirectory(Path path) throws IOException {
        Path parent = path.toAbsolutePath().getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
    }
}
