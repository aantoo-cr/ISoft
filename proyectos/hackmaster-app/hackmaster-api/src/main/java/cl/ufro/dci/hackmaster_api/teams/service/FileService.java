package cl.ufro.dci.hackmaster_api.teams.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Servicio para manejar operaciones relacionadas con archivos, como guardar imágenes y eliminar imágenes anteriores.
 */

@Service
public class FileService {

    /**
     * Directorio donde se almacenan las imágenes subidas por el usuario.
     * Esta ruta es relativa al directorio raíz del proyecto.
     */

    private static final String IMAGE_DIRECTORY = "src/main/resources/static/images";

    /**
     * Guarda la imagen proporcionada en un directorio local y devuelve la URL del archivo guardado.
     *
     * @param photoFile el archivo {@link MultipartFile} que se desea guardar.
     * @return la URL del archivo guardado.
     * @throws IllegalArgumentException si el archivo está vacío o tiene un tipo no soportado.
     * @throws RuntimeException si ocurre un error al guardar el archivo.
     */

    public String createPhotoUrl(MultipartFile photoFile) {
        validateFile(photoFile);

        var originalFileName = photoFile.getOriginalFilename();
        var destinationFile = Path.of(IMAGE_DIRECTORY, originalFileName);
        var tempFile = convertMultipartFileToFile(photoFile);

        copyFileContent(tempFile, destinationFile);
        deleteTempFile(tempFile);

        return "images/"+originalFileName;
    }

    /**
     * Elimina un archivo de foto existente dado su URL.
     *
     * @param photoUrl la URL del archivo que se desea eliminar.
     * @throws RuntimeException si el archivo existe pero no se puede eliminar.
     */

    public void removePhoto(String photoUrl) {
        var oldPhotoFile = new File(photoUrl);
        if (oldPhotoFile.exists()) {
            boolean deleted = oldPhotoFile.delete();
            if (!deleted) {
                throw new RuntimeException("Unable to delete old photo: " + oldPhotoFile.getAbsolutePath());
            }
        }
    }

    /**
     * Copia el contenido de un archivo a otro.
     *
     * @param origin el archivo de origen como {@link Path}.
     * @param destination el archivo de destino como {@link Path}.
     * @throws RuntimeException si ocurre un error al copiar el archivo.
     */

    private void copyFileContent(Path origin, Path destination) {
        try {
            Files.copy(origin, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while trying to save the file from "
                    + origin.toAbsolutePath() + " to " + destination.toAbsolutePath() + ": " + e.getMessage(), e);
        }
    }

    /**
     * Convierte un archivo {@link MultipartFile} en un archivo temporal {@link Path}.
     *
     * @param file el archivo {@link MultipartFile} que se desea convertir.
     * @return el archivo temporal como {@link Path}.
     * @throws RuntimeException si ocurre un error durante la conversión.
     */

    private Path convertMultipartFileToFile(MultipartFile file) {
        try {
            Path tempFile = Files.createTempFile("tempFile", file.getOriginalFilename());
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert MultipartFile to File", e);
        }
    }

    /**
     * Valida que el archivo proporcionado no esté vacío y sea una imagen PNG o JPG.
     *
     * @param file el archivo {@link MultipartFile} que se desea validar.
     * @throws IllegalArgumentException si el archivo está vacío o tiene un tipo de contenido no soportado.
     */

    private void validateFile (MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("The file doesn't exists");
        }

        String contentType = file.getContentType();
        if (contentType == null ||
                (!contentType.equalsIgnoreCase(MediaType.IMAGE_JPEG_VALUE) &&
                        !contentType.equalsIgnoreCase(MediaType.IMAGE_PNG_VALUE))) {
            throw new IllegalArgumentException("The file must be a PNG or JPG image");
        }
    }

    /**
     * Elimina un archivo temporal si existe.
     *
     * @param tempFile el archivo temporal como {@link Path}.
     * @throws RuntimeException si ocurre un error durante la eliminación.
     */

    private void deleteTempFile(Path tempFile) {
        try {
            Files.deleteIfExists(tempFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete temporary file: " + tempFile.toAbsolutePath(), e);
        }
    }
}
