package com.company.ukraine.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * #Summary:
 * #Author: Andrii_Marchenko1
 * #Author’s Email:
 * #Creation Date: 5/29/2018
 * #Comments:
 */
public class FileHelper {

    public static List<File> getFilesFromDirByExtensions(String filePath, String[] extensions, boolean recursive) throws FileNotFoundException {
        File file = new File(filePath);
        if (file.exists()) {
            return new LinkedList<>((file.isFile()
                    ? Collections.singletonList(file)
                    : FileUtils.listFiles(file, extensions, recursive)));
        } else {
            throw new FileNotFoundException();
        }
    }

    public static String normalizeFileName(String fileName){
        return fileName.replaceAll("[\\\\/:*?\"<>|]", "");
    }
}
