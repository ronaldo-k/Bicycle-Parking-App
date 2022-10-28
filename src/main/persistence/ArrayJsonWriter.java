package persistence;

/*
Writes and saves a JSONArray to a file. This class operates on objects whose classes implement the Saveable
interface, allowing a single class to be used to save both Users and ParkingSpots with different ArrayJsonWriter
objects.

Code adapted from Carter, P. (2021) “JSON Serialization Demo”.
 */

import org.json.JSONArray;

import java.io.*;
import java.util.List;

public class ArrayJsonWriter {
    private String fileName;
    private PrintWriter printWriter;

    // EFFECTS: Constructs a JsonWriter whose destination file is fileName.
    public ArrayJsonWriter(String fileName) {
        this.fileName = fileName;
    }

    // MODIFIES: this
    // EFFECTS:  Creates a printWriter that writes to file with fileName.
    public void open() throws FileNotFoundException {
        printWriter = new PrintWriter(new File(fileName));
    }

    // MODIFIES: this
    // EFFECTS:  Writes a List<Saveable> as a JSONArray on printWriter
    public void write(List<Saveable> saveables) {
        JSONArray jsonArray = new JSONArray();
        for (Saveable saveable : saveables) {
            jsonArray.put(saveable.toJson());
        }
        printWriter.write(jsonArray.toString());
    }

    // MODIFIES: this
    // EFFECTS:  Closes printWriter.
    public void close() {
        printWriter.close();
    }
}
