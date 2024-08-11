package gm.inventarios.servicio;

import gm.inventarios.configuracion.SecurityConfig;
import gm.inventarios.modelo.Usuario;
import gm.inventarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordMigrationService {

    @Autowired
    private UsuarioRepository usuarioRepository;  // Tu repositorio para usuarios

    public void migratePasswords() {
        // Recupera todos los usuarios con contraseñas en texto plano
        List<Usuario> usuarios = usuarioRepository.findAll();

        for (Usuario usuario : usuarios) {
            if (!usuario.getPassword().startsWith("{aes}")) {  // Asumiendo que las contraseñas cifradas empiezan con un prefijo
                try {
                    // Cifra la contraseña con AES
                    String encryptedPassword = SecurityConfig.EncryptionUtil.encrypt(usuario.getPassword());

                    // Actualiza el usuario con la contraseña cifrada
                    usuario.setPassword(encryptedPassword);
                    usuarioRepository.save(usuario);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
