package com.sc.session_agent.utils;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileBinaryUtil {
    public List<String> readBinary(File file) throws IOException {
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            int count = dis.readInt();
            List<String> words = new ArrayList<>(count);
            while (words.size() < count)
                words.add(dis.readUTF());
            return words;
        }
    }

    public void writeBinary(File file, List<String> words) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            dos.writeInt(words.size());

            for (String word : words) {
                dos.writeUTF(word);
            }
        }
    }
}
