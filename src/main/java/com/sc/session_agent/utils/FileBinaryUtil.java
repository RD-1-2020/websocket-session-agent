package com.sc.session_agent.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FileBinaryUtil {
    private final static Logger logger = LoggerFactory.getLogger(FileBinaryUtil.class);

    public List<String> readBinary(File file) throws IOException {
        List<String> validateResult = validateFile(file);
        if (validateResult != null) {
            return validateResult;
        }

        createFileIfNotExist(file);

        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            if (dis.available() == 0) {
                logger.warn("File is blank! Return empty list!");
                return Collections.emptyList();
            }

            int count = dis.readInt();
            List<String> words = new ArrayList<>(count);

            while (words.size() < count) {
                words.add(dis.readUTF());
            }

            return words;
        }
    }

    public void writeBinary(File file, List<String> words) throws IOException {
        List<String> validateResult = validateFile(file);
        if (validateResult != null) {
            return;
        }

        createFileIfNotExist(file);

        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            dos.writeInt(words.size());

            for (String word : words) {
                dos.writeUTF(word);
            }
        }
    }

    private List<String> validateFile(File file) {
        if (file == null) {
            logger.warn("File is null!");
            return Collections.emptyList();
        }

        return null;
    }

    private void createFileIfNotExist(File file) throws IOException {
        if (!file.exists()) {
            logger.info("File is not exist! Try create a new file...");
            file.createNewFile();
        }
    }
}
