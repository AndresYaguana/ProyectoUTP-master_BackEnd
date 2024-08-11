package gm.inventarios.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import gm.inventarios.excepcion.RecursoNoEncontradoExcepcion;
import gm.inventarios.modelo.*;
import gm.inventarios.servicio.ContenidoCursoServicio;
import gm.inventarios.servicio.CursoServicio;
import gm.inventarios.servicio.SeccionCursoServicio;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.request.async.AsyncRequestNotUsableException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("ProyectoUTP-master")
@CrossOrigin(value = "http://localhost:4200")
public class ContenidoCursoControlador {

    private static final Logger logger = LoggerFactory.getLogger(ContenidoCursoControlador.class);
    private final Path fileStorageLocation = Paths.get("C:/Proyecto1/ProyectoUTP-master/archivos/");

    private ContenidoCursoServicio contenidoCursoServicio;
    private CursoServicio cursoServicio;
    private SeccionCursoServicio seccionServicio;
   //private  Path fileStorageLocation;

    @Autowired
    public ContenidoCursoControlador(ContenidoCursoServicio contenidoCursoServicio,CursoServicio cursoServicio,SeccionCursoServicio seccionServicio/*Path fileStorageLocation*/) {
        this.contenidoCursoServicio = contenidoCursoServicio;
        this.cursoServicio = cursoServicio;
        this.seccionServicio = seccionServicio;
        //this.fileStorageLocation = fileStorageLocation;
    }

    @GetMapping("/cursos/contenido/{id}")
    public List<ContenidoCurso> obtenerContenidoCurso(@PathVariable Integer id) {
        return contenidoCursoServicio.obtenerContenidoCurso(id);
    }

    /*@ExceptionHandler(AsyncRequestNotUsableException.class)
    public ResponseEntity<String> handleAsyncRequestNotUsableException(AsyncRequestNotUsableException e) {
        logger.error("Client disconnected", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Client disconnected");
    }*/
    /*@ExceptionHandler(AsyncRequestNotUsableException.class)
    public ResponseEntity<String> handleAsyncRequestNotUsableException(AsyncRequestNotUsableException e) {
        logger.error("Client disconnected", e);
        return ResponseEntity.status(HttpStatus.OK).body("Client connection was interrupted.");
    }
*/
    @GetMapping("/download/{fileName}")
    public ResponseEntity<StreamingResponseBody> downloadFile(@PathVariable String fileName) {
        Path filePath = fileStorageLocation.resolve(fileName).normalize();

        if (!Files.exists(filePath)) {
            logger.error("File not found: {}", fileName);
            return ResponseEntity.notFound().build();
        }

        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
        } catch (IOException e) {
            logger.error("Error while accessing the file: {}", fileName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        StreamingResponseBody stream = outputStream -> {
            try (OutputStream out = outputStream) {
                Files.copy(filePath, out);
                out.flush();
            } catch (IOException e) {
                logger.warn("Client disconnected or failed to write: {}", fileName, e);
            }
        };

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // Set the content type accordingly
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .body(stream);
    }

    /*@PostMapping("/contenido")
    public ResponseEntity<ContenidoCurso> agregarContenido(@RequestBody ContenidoCurso contenidoCurso, @RequestBody MultipartFile file) {

        if (contenidoCurso.getCurso() != null && contenidoCurso.getCurso().getIdCurso() != null) {
            Curso curso = cursoServicio.buscarCursoPorId(contenidoCurso.getCurso().getIdCurso());
            if (curso != null) {
                contenidoCurso.setCurso(curso);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        }

        if (contenidoCurso.getSeccion() != null && contenidoCurso.getSeccion().getIdSeccion() != null) {
            SeccionCurso seccion = seccionServicio.buscarSeccionPorId(contenidoCurso.getSeccion().getIdSeccion());
            if (seccion != null) {
                contenidoCurso.setSeccion(seccion);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        }

        // Mostrar datos de ContenidoCurso en consola
        System.out.println("ContenidoCurso recibido:");
        System.out.println("Curso ID: " + (contenidoCurso.getCurso() != null ? contenidoCurso.getCurso().getIdCurso() : "N/A"));
        System.out.println("Sección ID: " + (contenidoCurso.getSeccion() != null ? contenidoCurso.getSeccion().getIdSeccion() : "N/A"));
        System.out.println("Título: " + contenidoCurso.getTitulo());
        System.out.println("Descripción: " + contenidoCurso.getDescripcion());
        // Agrega aquí otros campos de ContenidoCurso que necesites mostrar

        if (file != null && !file.isEmpty()) {
            // Mostrar información del archivo en consola
            System.out.println("Archivo recibido:");
            System.out.println("Nombre del archivo: " + file.getOriginalFilename());
            System.out.println("Tipo de contenido: " + file.getContentType());
            System.out.println("Tamaño: " + file.getSize() + " bytes");

            String fileName = file.getOriginalFilename();
            String filePath = "C:\\Proyecto1\\ProyectoUTP-master\\archivos\\" + fileName;
            try {
                file.transferTo(new File(filePath));
                contenidoCurso.setUrlArchivo(fileName);
            } catch (IOException e) {
                logger.error("Error saving file", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            System.out.println("No se recibió ningún archivo.");
        }

        ContenidoCurso contenidoGuardado = this.contenidoCursoServicio.guardarContenido(contenidoCurso);
        return ResponseEntity.ok(contenidoGuardado);
    }*/

    @PostMapping("/contenido")
    public ResponseEntity<ContenidoCurso> agregarContenido(
            @RequestParam("file") MultipartFile file,
            @RequestParam("contenidoCurso") String contenidoCursoJson) {

        // Deserialize the contenidoCurso from the JSON received
        ObjectMapper objectMapper = new ObjectMapper();
        ContenidoCurso contenidoCurso;
        try {
            contenidoCurso = objectMapper.readValue(contenidoCursoJson, ContenidoCurso.class);
        } catch (IOException e) {
            logger.error("Error deserializing ContenidoCurso", e);
            return ResponseEntity.badRequest().body(null);
        }

        // Obtain the course and section, and verify their existence
        if (contenidoCurso.getCurso() != null && contenidoCurso.getCurso().getIdCurso() != null) {
            Curso curso = cursoServicio.buscarCursoPorId(contenidoCurso.getCurso().getIdCurso());
            if (curso != null) {
                contenidoCurso.setCurso(curso);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        }

        if (contenidoCurso.getSeccion() != null && contenidoCurso.getSeccion().getIdSeccion() != null) {
            SeccionCurso seccion = seccionServicio.buscarSeccionPorId(contenidoCurso.getSeccion().getIdSeccion());
            if (seccion != null) {
                contenidoCurso.setSeccion(seccion);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        }

        // Display ContenidoCurso data in console
        System.out.println("ContenidoCurso received:");
        System.out.println("Course ID: " + (contenidoCurso.getCurso() != null ? contenidoCurso.getCurso().getIdCurso() : "N/A"));
        System.out.println("Section ID: " + (contenidoCurso.getSeccion() != null ? contenidoCurso.getSeccion().getIdSeccion() : "N/A"));
        System.out.println("Title: " + contenidoCurso.getTitulo());
        System.out.println("Description: " + contenidoCurso.getDescripcion());

        if (file != null && !file.isEmpty()) {
            String cursoId = contenidoCurso.getCurso() != null ? String.valueOf(contenidoCurso.getCurso().getIdCurso()) : "default";
            File directory = new File("C:/Proyecto1/ProyectoUTP-master/archivos/");
            if (!directory.exists()) {
                directory.mkdirs(); // Ensure the directory exists
            }
            String fileName = file.getOriginalFilename();
            File destinationFile = new File(directory, fileName);

            try {
                file.transferTo(destinationFile);
                contenidoCurso.setUrlArchivo(fileName); // Store only the filename
            } catch (IOException e) {
                logger.error("Error saving file", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            System.out.println("No file received.");
        }

        ContenidoCurso contenidoGuardado = this.contenidoCursoServicio.guardarContenido(contenidoCurso);
        return ResponseEntity.ok(contenidoGuardado);
    }


    @GetMapping("/seccion/{idSeccion}/contenido")
    public List<ContenidoCurso> obtenerContenidoSeccion(@PathVariable Integer idSeccion) {
        return contenidoCursoServicio.obtenerContenidosSeccion(idSeccion);
    }

    @GetMapping("/contenido")
    public List<ContenidoCurso> obtenerContenido() {
        List<ContenidoCurso> contenido = this.contenidoCursoServicio.ListarContenido();
        logger.info("Contenidos obtenidos:");
        contenido.forEach(contenidoCurso -> logger.info(contenidoCurso.toString()));
        return contenido;
    }

    @GetMapping("/contenido/{id}")
    public ResponseEntity<ContenidoCurso> obtenerContenidoPorId(@PathVariable int id) {
        ContenidoCurso contenido = this.contenidoCursoServicio.buscarContenidoPorId(id);
        if (contenido != null) {
            return ResponseEntity.ok(contenido);
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        }
    }

    @PutMapping("/contenido/{id}")
    public ResponseEntity<ContenidoCurso> actualizarContenido(
            @PathVariable int id,
            @RequestBody ContenidoCurso contenidoRecibido) {
        ContenidoCurso contenido = this.contenidoCursoServicio.buscarContenidoPorId(id);
        if (contenido == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        contenido.setTitulo(contenidoRecibido.getTitulo());
        contenido.setDescripcion(contenidoRecibido.getDescripcion());
        contenido.setUrlArchivo(contenidoRecibido.getUrlArchivo());
        contenido.setHabilitado(contenidoRecibido.getHabilitado());
        contenido.setModificadoPor(contenidoRecibido.getModificadoPor());
        contenido.setFechaModificacion(contenidoRecibido.getFechaModificacion());
        this.contenidoCursoServicio.guardarContenido(contenido);
        return ResponseEntity.ok(contenido);
    }

    @DeleteMapping("/contenido/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarSeccion(@PathVariable int id) {
        ContenidoCurso contenido = contenidoCursoServicio.buscarContenidoPorId(id);
        if (contenido == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        this.contenidoCursoServicio.eliminarContenidoPorId(contenido.getIdContenido());
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("Contenido Eliminado Exitosamente", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        logger.info("Solicitud de archivo para vista previa: {}", filename); // Agrega esta línea para imprimir el nombre del archivo solicitado

        try {
            Path filePath = fileStorageLocation.resolve(URLDecoder.decode(filename, StandardCharsets.UTF_8)).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String mediaType = getMediaTypeForFileName(filename);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(mediaType)) // Set the appropriate media type
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .body(resource);
            } else {
                throw new RecursoNoEncontradoExcepcion("Archivo no encontrado: " + filename);
            }
        } catch (IOException e) {
            throw new RecursoNoEncontradoExcepcion("Error al acceder al archivo: " + filename);
        }
    }


    private String getMediaTypeForFileName(String filename) {
        String mediaType = URLConnection.guessContentTypeFromName(filename);
        if (mediaType == null) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // Default to binary if type can't be determined
        }
        return mediaType;
    }

}
