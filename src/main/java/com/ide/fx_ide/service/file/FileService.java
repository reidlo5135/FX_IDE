package com.ide.fx_ide.service.file;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileService {

    public Map<String, String> setFileChooser(Stage stage) {
        Map<String, String> map = new HashMap<>();

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("java 파일", "*.java"));
        fileChooser.setInitialDirectory(new File("C:\\Build-Craft\\src\\main\\java\\com\\buildcraft"));

        File file = fileChooser.showOpenDialog(stage);

        map.put("name", file.getName());
        map.put("path", file.getPath());
        map.put("code", extract(file));

        return map;
    }

    public void setDirectoryChooser(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("C:\\"));

        File file = directoryChooser.showDialog(stage);
        System.out.println("directory name : " + file.getName());
        System.out.println("directory path : " + file.getPath());
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
