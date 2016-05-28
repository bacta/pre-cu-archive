package com.ocdsoft.bacta.swg.server.game.util;

import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kyle on 5/20/2016.
 */
public class MakeClientStringFile {

    public static void main(String[] arg) throws IOException {

        Path path = Paths.get(System.getProperty("user.dir"), "clientstrings2.properties");

        Files.deleteIfExists(path);
        Files.createFile(path);
        Map<Integer, String> stringList = new HashMap<>();
        final AtomicInteger count = new AtomicInteger();

         Files.readAllLines(Paths.get("C:\\dev\\pre-cu-master\\pre-cu\\src\\main\\resources\\newmessagenames.properties")).forEach(line -> {
             int value = count.getAndIncrement();
             int hashCode = SOECRC32.hashCode(line);
             if(!stringList.containsKey(hashCode)) {
                 stringList.put(hashCode, line);
                 String newLine = Integer.toHexString(hashCode) + "=" + line + "\n";
                 try {
                     Files.write(path, newLine.getBytes(), StandardOpenOption.APPEND);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             } else {
                 if(!stringList.get(hashCode).equals(line)) {
                     System.out.println("Hash collision: " + SOECRC32.hashCode(line) + ": " + line + " " + stringList.get(hashCode));
                 }
             }

             if(value % 1000 == 0) {
                 System.out.println("Processed " + value + " strings");
             }

         });

    }

}
