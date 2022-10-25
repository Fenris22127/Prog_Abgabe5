package de.medieninformatik.prog_abgabe5;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static de.medieninformatik.prog_abgabe5.SerializableList.deserialize;
import static de.medieninformatik.prog_abgabe5.SerializableList.serialize;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SerializableListTest {
    final String fileName = "data.ser";

    @Test
    void serializeStringTest() {
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

    @Test
    void serializeIntegerTest() {
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

    @Test
    void deserializeTest() {
        try {
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(fileName));

            System.out.println(deserialize(objectInput.getClass(), objectInput));
        }
        catch (Exception e) {
            e.getStackTrace();
        }

    }
}