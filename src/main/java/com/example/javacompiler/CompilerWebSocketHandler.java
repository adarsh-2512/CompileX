package com.example.javacompiler;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.*;
import java.util.concurrent.Executors;

public class CompilerWebSocketHandler extends TextWebSocketHandler {

    private Process process;
    private BufferedWriter writer;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg = message.getPayload();

        if (msg.startsWith("RUN:")) {
            String code = msg.substring(4);

            // Save code to Main.java
            File file = new File("Main.java");
            try (FileWriter fw = new FileWriter(file)) {
                fw.write(code);
            }

            // Compile
            Process compile = Runtime.getRuntime().exec("javac Main.java");
            BufferedReader compileReader = new BufferedReader(new InputStreamReader(compile.getErrorStream()));
            StringBuilder compileErrors = new StringBuilder();
            String line;
            while ((line = compileReader.readLine()) != null) compileErrors.append(line).append("\n");

            if (compileErrors.length() > 0) {
                session.sendMessage(new TextMessage("Compilation Error:\n" + compileErrors));
                return;
            }

            // Run program
            process = Runtime.getRuntime().exec("java Main");
            writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // Stream stdout
            Executors.newSingleThreadExecutor().submit(() -> {
                String outLine;
                try {
                    while ((outLine = reader.readLine()) != null) {
                        session.sendMessage(new TextMessage(outLine));
                    }
                } catch (IOException e) {}
            });

            // Stream stderr
            Executors.newSingleThreadExecutor().submit(() -> {
                String errLine;
                try {
                    while ((errLine = errReader.readLine()) != null) {
                        session.sendMessage(new TextMessage("Error: " + errLine));
                    }
                } catch (IOException e) {}
            });

            session.sendMessage(new TextMessage(">>> Program started. Type input below."));

        } else if (msg.startsWith("INPUT:")) {
            try {
                if (process != null && process.isAlive()) {
                    writer.write(msg.substring(6) + "\n");
                    writer.flush();
                } else {
                    session.sendMessage(new TextMessage(">>> Process has terminated."));
                }
            } catch (IOException e) {
                session.sendMessage(new TextMessage(">>> Error sending input: " + e.getMessage()));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (process != null) process.destroy();
    }
}
