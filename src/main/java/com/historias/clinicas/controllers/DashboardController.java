package com.historias.clinicas.controllers;

import com.historias.clinicas.entity.UserEntity;
import com.historias.clinicas.entity.enums.Role;
import com.historias.clinicas.repositories.AdminRepository;
import com.historias.clinicas.repositories.ProfessionalRepository;
import com.historias.clinicas.repositories.PatientRepository;
import com.historias.clinicas.repositories.EpisodeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final AdminRepository adminRepo;
    private final ProfessionalRepository profRepo;
    private final PatientRepository patientRepo;
    private final EpisodeRepository epiRepo;

    public DashboardController(AdminRepository adminRepo,
                               ProfessionalRepository profRepo,
                               PatientRepository patientRepo,
                               EpisodeRepository epiRepo) {
        this.adminRepo   = adminRepo;
        this.profRepo    = profRepo;
        this.patientRepo = patientRepo;
        this.epiRepo     = epiRepo;
    }

    @GetMapping({"/admin/dashboard","/professional/dashboard","/patient/dashboard"})
    public String dash(HttpSession sess, Model model){
        UserEntity u = (UserEntity) sess.getAttribute("USER");
        Role r       = (Role)       sess.getAttribute("ROLE");
        if(u==null || r==null) return "redirect:/login";

        boolean isPrimary = adminRepo.existsByUserIdAndPrimaryAdminTrue(u.getUserId());
        String roleStr = (r==Role.ADMIN && isPrimary) ? "ADMIN_PRIMARY" : r.name();

        model.addAttribute("user", u);
        model.addAttribute("role", roleStr);

        // Métricas solo para ADMIN
        if(r == Role.ADMIN){
            model.addAttribute("prosCount",     profRepo.count());
            model.addAttribute("patientsCount", patientRepo.count());
            model.addAttribute("openEpisodes",  epiRepo.countByDateOutIsNull());
        }

        // Métricas para PROFESIONAL
        if(r == Role.PROFESSIONAL){
            model.addAttribute("patientsCount", patientRepo.count());
            long activeEpisodes = epiRepo.findByProfessionalUserUserIdAndDateOutIsNull(u.getUserId()).size();
            model.addAttribute("activeEpisodes", activeEpisodes);
        }

        return switch(r){
            case ADMIN        -> "admin/dashboard";
            case PROFESSIONAL -> "professional/dashboard";
            case PATIENT      -> "patient/dashboard";
        };
    }
}
