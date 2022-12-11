package com.ide.fx_ide.service.file;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileService {
    private static final String EXTENSION = ".java";

    public Map<String, String> setFileChooser(Stage stage) {
        Map<String, String> map = new HashMap<>();

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("java 파일", "*.java"));
        fileChooser.setInitialDirectory(new File("C:\\"));

        File file = fileChooser.showOpenDialog(stage);

        map.put("name", file.getName());
        map.put("path", file.getPath());
        map.put("code", extract(file));

        return map;
    }

    public String setDirectoryChooser(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("C:\\"));

        File file = directoryChooser.showDialog(stage);
        System.out.println("directory path : " + file.getPath());

        return file.getPath();
    }

    public static void createNewDirectory() {
        File file = new File("C:\\");
    }

    public static void createNewFile(Map<String, String> data) {
        System.out.println("createNewFile data : " + data);
        String path = data.get("path") + "/" + data.get("name") + EXTENSION;

        try {
            File file = new File(path);
            if(!file.exists()) file.createNewFile();

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));

            bufferedWriter.write(data.get("code"));
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static String extract(File file) {
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while((line = br.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
        } catch (IOException fne) {
            fne.printStackTrace();
        }
        return text.toString();
    }
}
