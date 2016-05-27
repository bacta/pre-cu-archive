package com.ocdsoft.bacta.swg.server.game.util;

import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kyle on 5/20/2016.
 */
public class MakeCommandStringFile {

    public static void main(String[] arg) throws IOException {

        Path path = Paths.get(System.getProperty("user.dir"), "commandnames.properties");

        Files.deleteIfExists(path);
        Files.createFile(path);
        Map<Integer, String> stringList = new HashMap<>();
        final AtomicInteger count = new AtomicInteger();

         Files.readAllLines(Paths.get("C:\\dev\\swg-src_orig_noshare\\whitengold\\dsrc\\sku.0\\sys.shared\\compiled\\game\\datatables\\command\\command_table.tab")).forEach(line -> {
             int value = count.getAndIncrement();
             String[] fields = line.split("\t");
             String command = fields[0];
             int hashCode = SOECRC32.hashCode(command.toLowerCase());
             if(!stringList.containsKey(hashCode)) {
                 stringList.put(hashCode, command);
                 //String newLine = Integer.toHexString(hashCode) + "=" + command + "\n";
                 String newLine = command + ";" + Integer.toHexString(hashCode).toUpperCase() + "\n";
                 try {
                     Files.write(path, newLine.getBytes(), StandardOpenOption.APPEND);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             } else {
                 if(!stringList.get(hashCode).equals(command)) {
                     System.out.println("Hash collision: " + SOECRC32.hashCode(command) + ": " + command + " " + stringList.get(hashCode));
                 }
             }

             if(value % 1000 == 0) {
                 System.out.println("Processed " + value + " strings");
             }

         });

    }

}
