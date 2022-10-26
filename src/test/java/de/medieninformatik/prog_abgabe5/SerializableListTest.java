package de.medieninformatik.prog_abgabe5;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

import static de.medieninformatik.prog_abgabe5.SerializableList.deserialize;
import static de.medieninformatik.prog_abgabe5.SerializableList.serialize;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testet die Klasse {@link SerializableList}.
 *
 * @author Elisa Johanna Woelk (m30192)
 * @author J. Constantin Fritzsch (m30113)
 * @version 1.0
 */
class SerializableListTest {
    /**
     * Der Name der Datei, welche bei den Tests erstellt werden soll.
     */
    final String fileName = "data.ser";

    /**
     * Testet die Serialisierung und Deserialisierung von einer {@link java.util.List Liste} mit {@link String Strings}.
     */
    @Test
    void stringTest() {
        serializeStringTest();
        deserializeStringTest();
    }

    /**
     * Testet die Serialisierung und Deserialisierung von einer {@link java.util.List Liste} mit
     * {@link Integer Integers}.
     */
    @Test
    void integerTest() {
        serializeIntegerTest();
        deserializeIntegerTest();
    }

    /**
     * Testet die Serialisierung und Deserialisierung von einer {@link java.util.List Liste} mit {@link File Files}.
     */
    @Test
    void fileTest() {
        serializeFileTest();
        deserializeFileTest();
    }

    /**
     * Testet die Serialisierung von einer {@link java.util.List Liste} mit {@link String Strings}. <br>
     * Wurde schon eine Datei mit dem {@link #fileName Dateinamen} erstellt, wird diese gelöscht, um keine verfälschten
     * Testergebnisse zu erhalten.
     */
    @Test
    void serializeStringTest() {
        if (new File(fileName).exists()) assertTrue(new File(fileName).delete());
        ArrayList<String> testList = new ArrayList<>();
        testList.add("Elem1");
        testList.add("Elem2");
        testList.add("Elem3");
        testList.add("Elem4");

        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)){
            serialize(testList, objectOut);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertTrue(new File(fileName).exists());
    }

    /**
     * Testet die Serialisierung von einer {@link java.util.List Liste} mit {@link Integer Integers}. <br>
     * Wurde schon eine Datei mit dem {@link #fileName Dateinamen} erstellt, wird diese gelöscht, um keine verfälschten
     * Testergebnisse zu erhalten.
     */
    @Test
    void serializeIntegerTest() {
        if (new File(fileName).exists()) assertTrue(new File(fileName).delete());
        ArrayList<Integer> testList = new ArrayList<>();
        testList.add(1);
        testList.add(2);
        testList.add(3);
        testList.add(4);

        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)){
            serialize(testList, objectOut);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertTrue(new File(fileName).exists());
    }

    /**
     * Testet die Serialisierung von einer {@link java.util.List Liste} mit {@link File Files}. <br>
     * Wurde schon eine Datei mit dem {@link #fileName Dateinamen} erstellt, wird diese gelöscht, um keine verfälschten
     * Testergebnisse zu erhalten.
     */
    @Test
    void serializeFileTest() {
        if (new File(fileName).exists()) assertTrue(new File(fileName).delete());
        ArrayList<File> testList = new ArrayList<>();
        testList.add(new File("textfile1.txt"));
        testList.add(new File("textfile2.txt"));
        testList.add(new File("textfile3.txt"));
        testList.add(new File("textfile4.txt"));

        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)){
            serialize(testList, objectOut);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertTrue(new File(fileName).exists());
    }

    /**
     * Testet die Serialisierung von einer {@link java.util.List Liste} mit {@link Thread Threads}. <br>
     * Wurde schon eine Datei mit dem {@link #fileName Dateinamen} erstellt, wird diese gelöscht, um keine verfälschten
     * Testergebnisse zu erhalten. <br>
     * {@link SuppressWarnings Unterdrückt} die Warnung die erscheint, wenn ein Thread instanziiert wird, ohne die
     * {@link Thread#run()} Methode zu überschreiben.
     */
    @SuppressWarnings("InstantiatingAThreadWithDefaultRunMethod")
    @Test
    void serializeThreadTest() {
        if (new File(fileName).exists()) assertTrue(new File(fileName).delete());
        ArrayList<Thread> testList = new ArrayList<>();
        testList.add(new Thread());
        testList.add(new Thread());
        testList.add(new Thread());
        testList.add(new Thread());

        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)){

            Exception exception = assertThrows(NotSerializableException.class, () -> serialize(testList, objectOut));
            String expectedMessage = "Elements are not serializable!";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Testet die Deserialisierung einer {@link File Datei} mit {@link String Strings}.
     */
    @Test
    void deserializeStringTest() {
        try {
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(fileName));
            String expected ="[Elem1, Elem2, Elem3, Elem4]";
            String actual = Objects.requireNonNull(deserialize(String.class, objectInput)).toString();
            assertEquals(expected, actual);
        }
        catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * Testet die Deserialisierung einer {@link File Datei} mit {@link Integer Integers}.
     */
    @Test
    void deserializeIntegerTest() {
        try {
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(fileName));
            String expected ="[1, 2, 3, 4]";
            String actual = Objects.requireNonNull(deserialize(Integer.class, objectInput)).toString();
            assertEquals(expected, actual);
        }
        catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * Testet die Deserialisierung einer {@link File Datei} mit {@link File Files}.
     */
    @Test
    void deserializeFileTest() {
        try {
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(fileName));
            String expected ="[textfile1.txt, textfile2.txt, textfile3.txt, textfile4.txt]";
            String actual = Objects.requireNonNull(deserialize(File.class, objectInput)).toString();
            assertEquals(expected, actual);
        }
        catch (Exception e) {
            e.getStackTrace();
        }
    }

}