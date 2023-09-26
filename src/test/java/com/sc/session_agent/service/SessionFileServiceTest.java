package com.sc.session_agent.service;

import com.sc.session_agent.utils.FileBinaryUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SessionFileServiceTest {
    private static final String VALID_API_KEY = "validApiKey";
    private static final String TEST_FILE_PATH = "/dummyPath";
    private static final String NONEXISTENT_PATH = "nonexistentPath";

    @Mock
    private FileBinaryUtil fileBinaryUtil;

    @InjectMocks
    private SessionFileService sessionFileService;
    
    @TempDir
    private File sharedTempDir;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(sessionFileService, "authFilePath", TEST_FILE_PATH);
    }
    
    @AfterEach
    public void cleanUp() throws IOException {
        FileUtils.cleanDirectory(sharedTempDir);
    }

    @Test
    public void testRewriteApiKeyToFileWhenApiKeyIsProvidedThenReturnsSameKey() throws IOException {
        // Arrange
        getFileFromShared();

        // Act
        String result = sessionFileService.rewriteApiKeyToFile(VALID_API_KEY);

        // Assert
        assertThat(result).isEqualTo(VALID_API_KEY);
    }

    @Test
    public void testRewriteApiKeyToFileWhenIOExceptionOccursThenReturnsNull() throws IOException {
        // Arrange
        doThrow(IOException.class).when(fileBinaryUtil).writeBinary(any(), any());

        // Act
        String result = sessionFileService.rewriteApiKeyToFile(VALID_API_KEY);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    public void testGetApiKeyFromSessionFileWhenFileDoesNotExistThenReturnNull() throws IOException {
        // Arrange
        // No arrangement needed as the method doesn't have any dependencies

        // Act
        String result = sessionFileService.getApiKeyFromSessionFile();

        // Assert
        assertThat(result).isNull();
    }

    @Test
    public void testGetApiKeyFromSessionFileWhenFileExistsAndContainsApiKeyThenReturnApiKey() throws IOException {
        // Arrange
        File file = getFileFromShared();
        file.createNewFile();
        when(fileBinaryUtil.readBinary(any())).thenReturn(Collections.singletonList(VALID_API_KEY));

        // Act
        String result = sessionFileService.getApiKeyFromSessionFile();

        // Assert
        assertThat(result).isEqualTo(VALID_API_KEY);
    }

    private File getFileFromShared() {
        ReflectionTestUtils.setField(sessionFileService, "authFilePath", sharedTempDir.getPath() + TEST_FILE_PATH);
        File file = new File(sharedTempDir, TEST_FILE_PATH);
        return file;
    }

    @Test
    public void testGetApiKeyFromSessionFileWhenIOExceptionIsThrownThenReturnNull() throws IOException {
        // Arrange
        File file = getFileFromShared();
        file.createNewFile();
        when(fileBinaryUtil.readBinary(any())).thenThrow(IOException.class);

        // Act
        String result = sessionFileService.getApiKeyFromSessionFile();

        // Assert
        assertThat(result).isNull();
    }

    @Test
    public void testGetApiKeyFromSessionFileWhenFileExistsAndContainsApiKey() throws IOException {
        // Arrange
        File file = getFileFromShared();
        file.createNewFile();
        when(fileBinaryUtil.readBinary(any())).thenReturn(Collections.singletonList(VALID_API_KEY));

        // Act
        String result = sessionFileService.getApiKeyFromSessionFile();

        // Assert
        assertThat(result).isEqualTo(VALID_API_KEY);
    }

    @Test
    public void testGetApiKeyFromSessionFileWhenFileExistsButDoesNotContainApiKey() throws IOException {
        // Arrange
        when(fileBinaryUtil.readBinary(any())).thenReturn(Collections.emptyList());

        // Act
        String result = sessionFileService.getApiKeyFromSessionFile();

        // Assert
        assertThat(result).isNull();
    }

    @Test
    public void testGetApiKeyFromSessionFileWhenFileDoesNotExist() {
        // Arrange
        ReflectionTestUtils.setField(sessionFileService, "authFilePath", NONEXISTENT_PATH);

        // Act
        String result = sessionFileService.getApiKeyFromSessionFile();

        // Assert
        assertThat(result).isNull();
    }

    @Test
    public void testGetApiKeyFromSessionFileWhenIOExceptionIsThrown() throws IOException {
        // Arrange
        when(fileBinaryUtil.readBinary(any())).thenThrow(IOException.class);

        // Act
        String result = sessionFileService.getApiKeyFromSessionFile();

        // Assert
        assertThat(result).isNull();
    }
}