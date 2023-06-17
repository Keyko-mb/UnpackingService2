package ru.shafikova.UnpackingService.Services;

import org.junit.jupiter.api.Test;
import ru.shafikova.UnpackingService.Models.OutputFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UnpackingServiceTest {
    private final UnpackingService unpackingService = new UnpackingService();

    @Test
    void shouldUnpackCorrectly() throws IOException {
        File testFile = new File("src\\test\\resources\\The_Lady_with_the_Dog-compressed.bin");

        OutputFile outputData = unpackingService.unpack(Files.readAllBytes(testFile.toPath()));

        File unpackedFile = outputData.getFile();

        File fileBeforeCompression = new File("src\\test\\resources\\The Lady with the Dog.txt");
        byte[] bytesBeforeCompression = Files.readAllBytes(fileBeforeCompression.toPath());
        byte[] bytesAfterCompression = Files.readAllBytes(unpackedFile.toPath());

        boolean filesEquals = Arrays.equals(bytesAfterCompression, bytesBeforeCompression);
        assertThat(filesEquals).isTrue();
    }
}