package com.historias.clinicas.config;

import com.historias.clinicas.entity.*;
import com.historias.clinicas.entity.enums.*;
import com.historias.clinicas.repositories.*;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private String hash(String raw){ return BCrypt.hashpw(raw, BCrypt.gensalt()); }

    @Bean
    CommandLineRunner init(UserRepository userRepo,
                           AdminRepository adminRepo,
                           ProfessionalRepository proRepo,
                           PatientRepository patientRepo){

        return args -> {
            if(userRepo.count()==0){

                /* --- Admin --- */
                UserEntity admin = userRepo.save(
                    new UserEntity("admin", hash("admin123"),
                                   "Ada","Admin", Sex.F,null,
                                   "0","a@demo"));
                adminRepo.save(new Admin(admin,true));

                /* --- Professional --- */
                UserEntity pro = userRepo.save(
                    new UserEntity("pro1", hash("pro123"),
                                   "Pedro","Pro", Sex.M,null,
                                   "1","p@demo"));
                proRepo.save(new Professional(pro,
                               com.historias.clinicas.entity.enums.ProfType.MEDICO,
                               "CMP12345","Medicina General"));

                /* --- Patient --- */
                UserEntity pac = userRepo.save(
                    new UserEntity("pac1", hash("pac123"),
                                   "Paz","Pac", Sex.F,null,
                                   "2","c@demo"));
                patientRepo.save(new Patient(pac,
                               com.historias.clinicas.entity.enums.DocType.DNI,
                               "87654321","Av. Siempre Viva 123"));

                System.out.println("### Usuarios iniciales insertados ###");
            }
        };
    }
}
