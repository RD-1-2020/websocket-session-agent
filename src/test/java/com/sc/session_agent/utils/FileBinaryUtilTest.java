package com.sc.session_agent.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FileBinaryUtilTest {
    private final static List<String> testData = Arrays.asList("word1", "word2", "word3");

    @InjectMocks
    private FileBinaryUtil fileBinaryUtil;

    @Mock
    private File fileMock;

    @Test
    public void testWriteBinaryWhenFileAndWordsAreValidThenWritesCorrectNumberOfWordsAndWords(@TempDir File tempDir) throws IOException {
        File file = new File(tempDir, "test.txt");

        fileBinaryUtil.writeBinary(file, testData);
        List<String> result = fileBinaryUtil.readBinary(file);

        Assertions.assertEquals(testData.size(), result.size(), "The number of words written to the file should be correct");
        Assertions.assertEquals(testData, result, "The words written to the file should be correct");
    }

    @Test
    public void testWriteBinaryWhenValidationResultIsNotNullThenDoesNotWriteAnything() throws IOException {
        fileMock = null;

        fileBinaryUtil.writeBinary(fileMock, testData);

        Assertions.assertNull(fileMock, "The file should not be created");
    }

    @Test
    public void testReadBinaryWhenFileIsNullThenReturnEmptyList() throws IOException {
        File file = null;

        List<String> result = fileBinaryUtil.readBinary(file);

        Assertions.assertTrue(result.isEmpty(), "The result list should be empty");
    }

    @Test
    public void testReadBinaryWhenFileDoesNotExistThenCreateNewFileAndReturnEmptyList(@TempDir File tempDir) throws IOException {
        File file = new File(tempDir, "nonexistent.txt");

        List<String> result = fileBinaryUtil.readBinary(file);

        Assertions.assertTrue(result.isEmpty(), "The result list should be empty");
        Assertions.assertTrue(file.exists(), "The file should have been created");
    }

    @Test
    public void testReadBinaryWhenFileExistsAndContainsDataThenReturnListWithData(@TempDir File tempDir) throws IOException {
        File file = new File(tempDir, "test.txt");
        fileBinaryUtil.writeBinary(file, testData);

        List<String> result = fileBinaryUtil.readBinary(file);

        Assertions.assertEquals(testData, result, "The result list should contain the data written to the file");
    }

    @Test
    public void testWriteBinaryWhenFileAndWordsProvidedThenWritesCorrectDataToFile(@TempDir File tempDir) throws IOException {
        File file = new File(tempDir, "test.txt");

        fileBinaryUtil.writeBinary(file, testData);
        List<String> result = fileBinaryUtil.readBinary(file);

        Assertions.assertEquals(testData, result, "The result list should contain the data written to the file");
    }

    @Test
    public void testWriteBinaryWhenFileIsNullThenDoesNothing() throws IOException {
        File file = null;

        fileBinaryUtil.writeBinary(file, testData);

        Assertions.assertNull(file, "The file should not be created");
    }
}