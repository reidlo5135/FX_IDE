package com.ide.fx_ide.service.compiler;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CompilerService {

    public List<String> compileAfterConvertFile(String beforeCompile) {
        System.out.println("Compile SVC compileAfterConvertFile beforeCompile : " + beforeCompile);
        try {
            String path = "C:\\.rde\\temp\\";
            File folder = new File(path);

            if(!folder.exists()) {
                if(folder.mkdirs()) {
                    System.out.println("Compile SVC compileAfterConvertFile folder : " + folder + " are generated");
                }
            }

            String fileName = "Main" + ".java";
            File file = new File(path, fileName);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
                writer.write(beforeCompile);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Compile SVC compileAfterConvertFile error occurred : " + e.getMessage());
            }

            List<String> result = compileFile(file);
            System.out.println("Compile SVC compileAfterConvertFile compileFile result : " + result);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Compile SVC convertFile error occurred : " + e.getMessage());
        }
        return null;
    }

    private static List<String> compileFile(File file) {
        try {
            String[] commands = new String[] {"java " + file.getName(),"exit"};

            ProcessBuilder pb = new ProcessBuilder("cmd");
            Map<String, String> env = pb.environment();

            pb.redirectErrorStream(true);
            pb.directory(new File("C:\\.rde\\temp\\"));
            System.out.println("PB environment : " + env);

            Process p = pb.start();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

            for(String cmd : commands) {
                System.out.println("cmd : " + cmd);
                writer.write(cmd + "\n");
                writer.flush();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String outputLine = "";
            List<String> result = new ArrayList<>();

            while((outputLine = reader.readLine()) != null){
                result.add(outputLine);
                if(outputLine.equals("")){
                    result.remove(outputLine);
                    continue;
                }
                if(outputLine.contains("Microsoft Windows")){
                    result.remove(outputLine);
                    continue;
                }
                if(outputLine.equals("(c) Microsoft Corporation. All rights reserved.")) {
                    result.remove(outputLine);
                    continue;
                }
                if(outputLine.contains("java")) {
                    result.remove(outputLine);
                    continue;
                }
                if(outputLine.contains("exit")) {
                    result.remove(outputLine);
                    break;
                }
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String convertCompileResultToString(List<String> compileResult) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("a HH:mm:ss");

        sb.append(simpleDateFormat.format(new Date())).append(": Executing ':Main.main()'...");
        sb.append("\n\n\n");

        for(String str : compileResult) {
            System.out.println("str : " + str);
            sb.append(str);
            sb.append("\n");
        }

        System.out.println("sb : " + sb);

        return sb.toString();
    }
}