package com.company.ukraine.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.List;

public class GsonUtils {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public  <T> T deserializeClass(Class<T> resultClass, String jsonBody) {
        return gson.fromJson(jsonBody, resultClass);
    }

    public String readJsonFromFile(String file) {
        try {
            return inputStreamToString(new FileInputStream(new File(file).getAbsoluteFile()));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public List<File> getJsonFilesFromDir(String dir) {
        try {
            return FileHelper.getFilesFromDirByExtensions(dir, new String[]{"json"}, true);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("File/Directory '%s' does not exist.", dir));
        }
    }


    private String inputStreamToString(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return sb.toString();
    }
}
