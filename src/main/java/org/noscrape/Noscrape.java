package org.noscrape;

import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class Noscrape {

    private final String font;
    private final Map<Character, Integer> mapping = new HashMap<>();
    private final List<Integer> puaRange;

    public Noscrape(String font) throws IOException {
        Path fontPath = Paths.get(font);
        if (!Files.isReadable(fontPath)) {
            throw new IOException("Font could not be found at: " + font);
        }
        this.font = font;
        this.puaRange = new ArrayList<>();
        for (int i = 0xE000; i <= 0xF8FF; i++) {
            this.puaRange.add(i);
        }
    }

    public String obfuscate(String s) {
        List<Integer> availableChars = new ArrayList<>(this.puaRange);
        StringBuilder obfuscated = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (!this.mapping.containsKey(c)) {
                int randomIndex = new Random().nextInt(availableChars.size());
                int puaChar = availableChars.remove(randomIndex);
                this.mapping.put(c, puaChar);
            }

            int puaCode = this.mapping.get(c);
            obfuscated.append(Character.toChars(puaCode));
        }

        return obfuscated.toString();
    }

    public String render() throws IOException, InterruptedException {
        String binary = LibLoader.loadLibrary();

        JSONObject param = new JSONObject();
        param.put("font", this.font);
        param.put("translation", this.mapping);

        String jsonParam = param.toString();

        ProcessBuilder processBuilder = new ProcessBuilder(binary, jsonParam);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Rendering library failed with exit code: " + exitCode);
            }
            return result.toString();
        }
    }
}
