package gm.inventarios.controlador;

import gm.inventarios.modelo.Mensaje;
import gm.inventarios.servicio.MensajeServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ProyectoUTP-master")
@CrossOrigin(value = "http://localhost:4200")
public class MensajeControlador {

    private final MensajeServicio mensajeService;

    @Autowired
    public MensajeControlador(MensajeServicio mensajeService) {
        this.mensajeService = mensajeService;
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Mensaje> obtenerMensajesPorUsuario(@PathVariable Integer idUsuario) {
        List<Mensaje> mensajes = mensajeService.obtenerMensajesPorUsuario(idUsuario);
        System.out.println("Mensajes obtenidos: " + mensajes); // O usa un logger para esto
        return mensajes;
    }


    @PostMapping
    public ResponseEntity<?> enviarMensaje(@RequestBody Mensaje mensaje) {
        try {
            if (mensaje.getEmisor() == null || mensaje.getEmisor().getIdUsuario() == 0) {
                return ResponseEntity.badRequest().body("El emisor del mensaje no puede ser nulo o tener ID 0");
            }
            if (mensaje.getReceptor() == null || mensaje.getReceptor().getIdUsuario() == 0) {
                return ResponseEntity.badRequest().body("El receptor del mensaje no puede ser nulo o tener ID 0");
            }
            Mensaje mensajeGuardado = mensajeService.enviarMensaje(mensaje);
            return ResponseEntity.ok(mensajeGuardado);
        } catch (Exception e) {
            // Log the exception details for debugging
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar mensaje");
        }
    }

    @GetMapping("/mensajes")
    public List<Mensaje> obtenerTodosLosMensajes() {
        return mensajeService.obtenerTodosLosMensajes();
    }

}