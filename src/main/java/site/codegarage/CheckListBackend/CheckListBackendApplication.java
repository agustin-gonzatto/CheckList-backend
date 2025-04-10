package site.codegarage.CheckListBackend;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.TimeZone;

@SpringBootApplication
public class CheckListBackendApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("DB_PASS", dotenv.get("DB_PASS"));
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USER", dotenv.get("DB_USER"));
		SpringApplication.run(CheckListBackendApplication.class, args);
		printStartupMessage();
	}

	private static void printStartupMessage() {
		// Obtener información del sistema
		Runtime runtime = Runtime.getRuntime();
		long maxMemory = runtime.maxMemory() / (1024 * 1024);
		long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
		String version = CheckListBackendApplication.class.getPackage().getImplementationVersion();

		// Arte ASCII de "CheckList" en celeste
		String checklistArt = """
				
				                                                            sSSs   .S    S.     sSSs    sSSs   .S    S.   S.       .S    sSSs  sdSS_SSSSSSbs \s
				                                                           d%%SP  .SS    SS.   d%%SP   d%%SP  .SS    SS.  SS.     .SS   d%%SP  YSSS~S%SSSSSP \s
				                                                          d%S'    S%S    S%S  d%S'    d%S'    S%S    S&S  S%S     S%S  d%S'         S%S      \s
				                                                          S%S     S%S    S%S  S%S     S%S     S%S    d*S  S%S     S%S  S%|          S%S      \s
				                                                          S&S     S%S SSSS%S  S&S     S&S     S&S   .S*S  S&S     S&S  S&S          S&S      \s
				                                                          S&S     S&S  SSS&S  S&S_Ss  S&S     S&S_sdSSS   S&S     S&S  Y&Ss         S&S      \s
				                                                          S&S     S&S    S&S  S&S~SP  S&S     S&S~YSSY%b  S&S     S&S  `S&&S        S&S      \s
				                                                          S&S     S&S    S&S  S&S     S&S     S&S    `S%  S&S     S&S    `S*S       S&S      \s
				                                                          S*b     S*S    S*S  S*b     S*b     S*S     S%  S*b     S*S     l*S       S*S      \s
				                                                          S*S.    S*S    S*S  S*S.    S*S.    S*S     S&  S*S.    S*S    .S*P       S*S      \s
				                                                           SSSbs  S*S    S*S   SSSbs   SSSbs  S*S     S&   SSSbs  S*S  sSS*S        S*S      \s
				                                                            YSSP  SSS    S*S    YSSP    YSSP  S*S     SS    YSSP  S*S  YSS'         S*S      \s
				                                                                         SP                   SP                  SP                SP       \s
				                                                                         Y                    Y                   Y                 Y        \s
				                                                                                                                                             \s
				
        """;

		// Mensaje de inicio
		System.out.println("\n\u001B[36m" + checklistArt + "\u001B[0m");
		System.out.println("\u001B[36m╔════════════════════════════════════════════════╗");
		System.out.println("\u001B[36m║               CHECKLIST BACKEND v" + (version != null ? version : "1.0.0") + "              ║");
		System.out.println("\u001B[36m╚════════════════════════════════════════════════╝\u001B[0m");

		// Información técnica
		System.out.println("\u001B[34m» Hora de inicio: \u001B[0m" + LocalDateTime.now());
		System.out.println("\u001B[34m» Perfil activo: \u001B[0m" +
				(System.getenv("SPRING_PROFILES_ACTIVE") != null ?
						System.getenv("SPRING_PROFILES_ACTIVE") : "default"));
		System.out.println("\u001B[34m» Memoria usada: \u001B[0m" + usedMemory + "MB / " + maxMemory + "MB");
		System.out.println("\u001B[34m» Procesadores disponibles: \u001B[0m" + runtime.availableProcessors());

		// Endpoints útiles
		System.out.println("\n\u001B[35m» Endpoints disponibles:");
		System.out.println("\u001B[35m  ➤ Swagger UI: \u001B[0mhttp://localhost:8080/swagger-ui.html");
		System.out.println("\u001B[35m  ➤ Health Check: \u001B[0mhttp://localhost:8080/actuator/health");
		System.out.println("\u001B[35m  ➤ API Docs: \u001B[0mhttp://localhost:8080/v3/api-docs");

		// Mensaje final
		System.out.println("\n\u001B[36m» ¡Sistema CheckList listo para usar! \uD83D\uDCC3\u001B[0m\n");
	}


}
