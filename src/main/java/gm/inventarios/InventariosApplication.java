package gm.inventarios;

import gm.inventarios.servicio.PasswordMigrationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventariosApplication {

	public static void main(String[] args) {

		SpringApplication.run(InventariosApplication.class, args);
	}
	/*@Autowired
	private PasswordMigrationService passwordMigrationService;

	@PostConstruct
	public void migratePasswords() {
		passwordMigrationService.migratePasswords();
	}*/
}
