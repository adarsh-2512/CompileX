package com.example.javacompiler;

import java.io.*;
import java.nio.file.*;

public class JavaCompilerService {

    public static String compileAndRun(String className, String code, String input) {
        try {
            // Save code to file
            Path javaFile = Paths.get(className + ".java");
            Files.write(javaFile, code.getBytes());

            // Compile the Java file
            Process compileProcess = new ProcessBuilder("javac", javaFile.toString()).start();
            compileProcess.waitFor();

            if (compileProcess.exitValue() != 0) {
                return new String(compileProcess.getErrorStream().readAllBytes());
            }

            // Run compiled Java class
            Process runProcess = new ProcessBuilder("java", className).start();

            // Pass input to the running process
            if (input != null && !input.isEmpty()) {
                try (BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(runProcess.getOutputStream()))) {
                    writer.write(input);
                    writer.newLine();
                }
            }

            runProcess.waitFor();

            // Get output and errors
            String output = new String(runProcess.getInputStream().readAllBytes());
            String errors = new String(runProcess.getErrorStream().readAllBytes());

            return output + errors;

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
