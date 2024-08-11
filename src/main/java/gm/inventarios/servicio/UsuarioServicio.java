package gm.inventarios.servicio;

import gm.inventarios.configuracion.SecurityConfig;
import gm.inventarios.excepcion.RecursoNoEncontradoExcepcion;
import gm.inventarios.modelo.Usuario;
import gm.inventarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Key;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio implements IUsuarioServicio {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> ListarUsuario() {
        return this.usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarUsuarioPorId(Integer idUsuario) {
        Usuario usuario = this.usuarioRepository.findById(idUsuario).orElse(null);
        return usuario;
    }

    /*@Override
    public Usuario guardarUsuario(Usuario usuario) {
        return this.usuarioRepository.save(usuario);
    }*/
    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            try {
                usuario.setPassword(SecurityConfig.EncryptionUtil.encrypt(usuario.getPassword()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarUsuarioPorId(Integer idUsuario) {
        this.usuarioRepository.deleteById(idUsuario);
    }

    /*public Usuario autenticarUsuario(String email, String password) {
        String trimmedEmail = email.trim().toLowerCase();
        System.out.println("Authenticating user with email: " + trimmedEmail);
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(trimmedEmail);
        if (usuario != null && passwordEncoder.matches(password, usuario.getPassword())) {
            return usuario;
        } else {
            return null;
        }
    }*/

    @Override
    public Usuario autenticarUsuario(String email, String password) {
        String trimmedEmail = email.trim().toLowerCase();
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(trimmedEmail);
        if (usuario != null) {
            try {
                String decryptedPassword = SecurityConfig.EncryptionUtil.decrypt(usuario.getPassword());
                System.out.println("Contraseña desencriptada: " + decryptedPassword);  // Solo para desarrollo
                if (password.equals(decryptedPassword)) {
                    return usuario;
                } else {
                    System.out.println("Contraseña incorrecta para el usuario: " + trimmedEmail);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Usuario no encontrado: " + trimmedEmail);
        }
        return null;
    }

    @Override
    public List<String> obtenerPermisosUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoExcepcion("Usuario no encontrado con ID: " + idUsuario));

        if (usuario.getTipousuario() != null) {
            return usuario.getTipousuario().getPermisos();
        } else {
            throw new RecursoNoEncontradoExcepcion("Tipo de usuario no encontrado para el usuario con ID: " + idUsuario);
        }
    }
}

