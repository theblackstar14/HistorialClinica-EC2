package com.historias.clinicas.controllers;

import com.historias.clinicas.dao.UserDao;
import com.historias.clinicas.entity.UserEntity;
import com.historias.clinicas.entity.enums.Role;
import com.historias.clinicas.general.PasswordUtil;
import com.historias.clinicas.repositories.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controla el flujo de login/logout y coloca en sesión los atributos
 * USER y ROLE.  Usa PasswordUtil para verificar las credenciales.
 */
@Controller
public class AuthController {

    private final UserDao                 userDao;
    private final AdminRepository         adminRepo;
    private final ProfessionalRepository  proRepo;
    private final PatientRepository       patientRepo;

    public AuthController(UserDao userDao,
                          AdminRepository adminRepo,
                          ProfessionalRepository proRepo,
                          PatientRepository patientRepo) {
        this.userDao    = userDao;
        this.adminRepo  = adminRepo;
        this.proRepo    = proRepo;
        this.patientRepo= patientRepo;
    }

    /* ---------------------------------------------------------------
     * FORMULARIO DE LOGIN
     * --------------------------------------------------------------- */
    @GetMapping("/login")
    public String form(@RequestParam(required = false) String error,
                       @RequestParam(required = false) String timeout,
                       Model model) {
        if (error   != null) model.addAttribute("msg", "Credenciales inválidas");
        if (timeout != null) model.addAttribute("msg", "Sesión finalizada");
        return "login";
    }

    /* ---------------------------------------------------------------
     * PROCESO DE LOGIN
     * --------------------------------------------------------------- */
    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session) {

        UserEntity u = userDao.findByUsername(username).orElse(null);

        // --- verificación de contraseña con PasswordUtil ---
        if (u == null || !PasswordUtil.verify(password, u.getHashPassword()))
            return "redirect:/login?error";

        /* -------- determinar rol -------- */
        Role role;
        Integer id = u.getUserId();
        if      (adminRepo.existsById(id)) role = Role.ADMIN;
        else if (proRepo.existsById(id))   role = Role.PROFESSIONAL;
        else                               role = Role.PATIENT;

        session.setAttribute("USER", u);
        session.setAttribute("ROLE", role);

        return switch (role) {
            case ADMIN        -> "redirect:/admin/dashboard";
            case PROFESSIONAL -> "redirect:/professional/dashboard";
            case PATIENT      -> "redirect:/patient/dashboard";
        };
    }

    /* ---------------------------------------------------------------
     * LOGOUT
     * --------------------------------------------------------------- */
    @GetMapping("/logout")
    public String logout(HttpSession s) {
        s.invalidate();
        return "redirect:/login?timeout";
    }
}
