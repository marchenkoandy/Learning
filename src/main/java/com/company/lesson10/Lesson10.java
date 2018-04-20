package com.company.lesson10;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.InflaterInputStream;

/**
 * #Summary:
 * #Author: Andrii_Marchenko1
 * #Author’s Email:
 * #Creation Date: 4/20/2018
 * #Comments:
 */
public class Lesson10 {
    public Properties readPropFile() {
        Properties properties = new Properties();
        try {
            String fileName= "src\\main\\java\\com\\company\\lesson10\\example.properties";
            File file = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public List<String> readFileAsList() {
        List<String> list = new ArrayList<>();
        File file = new File("src\\main\\java\\com\\company\\lesson10\\example.properties");
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void writeToFile(List<String> list) {
        FileWriter fileWriter = null;
        File file = new File("src\\main\\java\\com\\company\\lesson10\\example1.txt");
        try {
            fileWriter = new FileWriter(file);
            fileWriter.append("123");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Lesson10 lesson10 = new Lesson10();
        System.out.println(lesson10.readPropFile().getProperty("prop.1"));
        List<String> list = lesson10.readFileAsList();
        lesson10.writeToFile(list);
        System.out.println(list);
    }
}
