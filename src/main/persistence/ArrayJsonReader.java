package persistence;

/*
Reads a JSONArray from a file. This class is abstract due to the fact that arrays storing different types of objects
are read, but all methods in common are consolidated here. Do not instantiate an object of this class.

Code adapted from Carter, P. (2021) “JSON Serialization Demo”.
 */

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class ArrayJsonReader {
    private String fileName;

    // EFFECTS: constructs reader to read from source file
    public ArrayJsonReader(String fileName) {
        this.fileName = fileName;
    }

    // EFFECTS: Reads a Saveable object from file and returns it. IOException is thrown should an error occur
    public List<Saveable> read() throws IOException {
        String jsonData = readFile(fileName);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseSaveables(jsonArray);
    }


    // EFFECTS: Parses a JSONArray into a List<Saveables>, with each Saveable being itself parsed by parseSaveable
    public List<Saveable> parseSaveables(JSONArray jsonArray) {
        List<Saveable> saveables = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            saveables.add(parseSaveable(jsonArray.getJSONObject(i)));
        }
        return saveables;
    }

    // EFFECTS: Parses a JSONObject into a Saveable. This method is abstract so that the classes that extend
    // ArrayJsonReader can implement their own version of it which processes a JSONObject into a specific Saveable
    // object.
    public abstract Saveable parseSaveable(JSONObject jsonObject);

    // EFFECTS: Reads file and returns the content as a String
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }
}
