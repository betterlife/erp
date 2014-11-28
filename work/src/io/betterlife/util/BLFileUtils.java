package io.betterlife.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author: Lawrence Liu
 * Date: 11/28/14
 */
public class BLFileUtils {
    private static BLFileUtils ourInstance = new BLFileUtils();

    public static BLFileUtils getInstance() {
        return ourInstance;
    }

    private BLFileUtils() {}

    public  String writeToAbsolutePath(final String absolutePath, final String fileContent,
                                       final String encoding) throws IOException {
        FileOutputStream outputStream  = null;
        try {
            outputStream = FileUtils.openOutputStream(new File(absolutePath));
            IOUtils.write(fileContent, outputStream, encoding);
        } finally {
            if (null != outputStream) {
                outputStream.close();
            }
        }
        return fileContent;
    }
}
