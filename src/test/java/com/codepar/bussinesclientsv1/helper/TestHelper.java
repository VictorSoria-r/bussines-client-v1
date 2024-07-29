package com.codepar.bussinesclientsv1.helper;

import java.nio.file.Files;
import java.nio.file.Paths;

public class TestHelper {
    public static String readJsonFile(String filePath) throws Exception {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
