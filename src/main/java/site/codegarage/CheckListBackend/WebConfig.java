package site.codegarage.CheckListBackend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Permitir solicitudes CORS para todas las rutas y de cualquier origen
        registry.addMapping("/**")
                .allowedOrigins("*")  // Reemplaza con los orígenes permitidos
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
                .allowedHeaders("*")  // Cualquier cabecera
                .allowCredentials(false);
    }
}
