package com.angkorteam.ecommerce;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by socheatkhauv on 2/2/17.
 */
public class SQLMerge {

    public static void main(String[] args) throws IOException {
        File db = new File("src/main/webapp/WEB-INF/db");
        File file = new File("src/main/webapp/WEB-INF/db/migration");
        String[] patterns = new String[]{"mysql__platform", "mysql__ecommerce"};
        for (String pattern : patterns) {
            File patternFile = new File(db, pattern + ".sql");
            patternFile.delete();
            for (File sql : FileUtils.listFiles(file, new String[]{"sql"}, true)) {
                if (FilenameUtils.getBaseName(sql.getName()).startsWith("mysql__platform")) {
                    FileUtils.writeByteArrayToFile(patternFile, ("-- # " + FilenameUtils.getName(sql.getName()) + "\n").getBytes(), true);
                    FileUtils.writeByteArrayToFile(patternFile, "-- ################################################################\n".getBytes(), true);
                    FileUtils.writeByteArrayToFile(patternFile, FileUtils.readFileToByteArray(sql), true);
                    FileUtils.writeByteArrayToFile(patternFile, "\n\n\n".getBytes(), true);
                }
            }
        }
    }

}
