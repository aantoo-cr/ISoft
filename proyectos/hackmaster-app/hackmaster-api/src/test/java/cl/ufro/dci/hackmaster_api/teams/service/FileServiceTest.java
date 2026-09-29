package cl.ufro.dci.hackmaster_api.teams.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileServiceTest {

    private FileService fileService;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
    }

    @Test
    @DisplayName("Debe crear la URL de la foto correctamente")
    void testCreatePhotoUrlSuccess() throws IOException {

        var photoFile = new MockMultipartFile("photo", "photo.jpg", "image/jpeg", "fake-image-content".getBytes());


        File directory = new File("src/main/resources/images");
        if (!directory.exists()) {
            directory.mkdirs();
        }


        String photoUrl = fileService.createPhotoUrl(photoFile);


        assertThat(photoUrl).contains("photo.jpg");
        File file = new File(photoUrl);
        assertThat(file.exists()).isTrue();


        file.delete();
    }


    @Test
    @DisplayName("Debe lanzar excepción si el archivo no es una imagen válida")
    void testCreatePhotoUrlInvalidFile() {

        var invalidFile = new MockMultipartFile("file", "file.txt", "text/plain", "invalid-content".getBytes());


        assertThrows(IllegalArgumentException.class, () -> fileService.createPhotoUrl(invalidFile));
    }

    @Test
    @DisplayName("Debe eliminar un archivo existente")
    void testRemovePhotoSuccess() throws IOException {

        var tempFile = Files.createTempFile("test-file", ".jpg");
        String filePath = tempFile.toString();


        fileService.removePhoto(filePath);


        assertThat(new File(filePath).exists()).isFalse();
    }
}
