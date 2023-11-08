package org.example;

import java.io.*;

public class Main {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
// set up the command and parameter
        String pythonScriptPath = "C:\\Users\\ADM\\Downloads\\Speech.py";
        String[] cmd = new String[2 + args.length];
        cmd[0] = "python";
        cmd[1] = pythonScriptPath;
        for(int i = 0; i < args.length; i++) {
            cmd[i+2] = args[i];
        }

// create runtime to execute external command
        System.out.println("ready to speech");
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(cmd);

// retrieve output from python script
        BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line = "";
        while((line = bfr.readLine()) != null) {
// display each output line form python script
            System.out.println(line);
        }
    }
}