package com.gestion.stock;

import com.gestion.stock.config.SpringSecurityAuditorAware;
import com.gestion.stock.services.admin.UtilisateurService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.logging.Level;

@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableSwagger2
@Log
@SpringBootApplication
public class StockApiApplication extends SpringBootServletInitializer implements CommandLineRunner {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UtilisateurService utilisateurService;


    @Bean
    public AuditorAware<String> auditorAware() {
        return new SpringSecurityAuditorAware();
    }

    @Autowired
    public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void setUtilisateurService(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Override
    public void run(String... args) throws Exception {
        log.log(Level.INFO, bCryptPasswordEncoder.encode("passer"));
        utilisateurService.addDefaultAdmin();
    }

    public static void main(String[] args) {
        SpringApplication.run(StockApiApplication.class, args);
    }

}
