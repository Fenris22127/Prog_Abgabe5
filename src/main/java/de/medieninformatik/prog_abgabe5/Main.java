package de.medieninformatik.prog_abgabe5;

import java.util.ArrayList;

/**
 * Started das Programm.
 *
 * @author Elisa Johanna Woelk (m30192)
 * @author J. Constantin Fritzsch (m30113)
 * @version 1.0
 */
public class Main {
    /**
     * Ruft die Klasse {@link SerializableList} auf, um {@link java.util.List Listen} zu serialisieren/deserialisieren.
     * @param args <-
     */
    public static void main(String[] args) {
        SerializableList<?> serList = new SerializableList<>(new ArrayList<>());
        if (serList.isEmpty()) System.out.println(
                        System.lineSeparator() +
                        "Passed list is empty!" +
                        System.lineSeparator());
    }
}
