package com.historias.clinicas.controllers;

import com.historias.clinicas.entity.UserEntity;
import com.historias.clinicas.entity.enums.Role;
import com.historias.clinicas.entity.enums.ProfType;
import com.historias.clinicas.entity.enums.Sex;
import com.historias.clinicas.repositories.AdminRepository;
import com.historias.clinicas.repositories.ProfessionalRepository;
import com.historias.clinicas.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Control panel for ADMIN users, managing professionals, administrators, and self-profile.
 */
@Controller
@RequestMapping("/admin")
public class AdminPanelController {

    private final UserRepository userRepo;
    private final ProfessionalRepository profRepo;
    private final AdminRepository adminRepo;

    public AdminPanelController(UserRepository userRepo,
                                ProfessionalRepository profRepo,
                                AdminRepository adminRepo) {
        this.userRepo = userRepo;
        this.profRepo = profRepo;
        this.adminRepo = adminRepo;
    }

    /**
     * Adds the current user's role string to all admin views for menu rendering.
     */
    @ModelAttribute
    public void populateRole(Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("USER");
        Role role = (Role) session.getAttribute("ROLE");
        boolean isPrimary = user != null
                && adminRepo.existsByUserIdAndPrimaryAdminTrue(user.getUserId());
        String roleStr = (role == Role.ADMIN && isPrimary)
                ? "ADMIN_PRIMARY" : (role != null ? role.name() : "");
        model.addAttribute("role", roleStr);
    }

    /* === PROFESSIONALS (any admin) === */

    @GetMapping("/professionals")
    public String listProfessionals(Model model) {
        model.addAttribute("pros", profRepo.findAll());
        return "admin/professionals";
    }

    @GetMapping("/professionals/new")
    public String newProfessionalForm(Model model) {
        model.addAttribute("profTypes", ProfType.values());
        model.addAttribute("sexValues", Sex.values());
        return "admin/professional_form";
    }

    @PostMapping("/professionals")
    public String createProfessional(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam Sex sex,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam ProfType profType,
            @RequestParam String licenseNumber,
            @RequestParam(required = false) String specialty,
            Model model) {

        if (userRepo.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Usuario ya existe");
            model.addAttribute("sexValues", Sex.values());
            model.addAttribute("profTypes", ProfType.values());
            return "admin/professional_form";
        }

        UserEntity u = new UserEntity(
                username,
                com.historias.clinicas.general.PasswordUtil.hash(password),
                firstName, lastName, sex, null, phone, email);
        userRepo.save(u);
        profRepo.save(new com.historias.clinicas.entity.Professional(
                u, profType, licenseNumber, specialty));

        return "redirect:/admin/professionals";
    }

    @PostMapping("/professionals/{id}/delete")
    public String deleteProfessional(@PathVariable Integer id) {
        profRepo.deleteById(id);
        return "redirect:/admin/professionals";
    }

    /* === ADMINISTRATORS (primary admin only) === */

    @GetMapping("/admins")
    public String listAdmins(HttpSession session, Model model) {
        if (!isPrimary(session)) {
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("admins", adminRepo.findAll());
        return "admin/admins";
    }

    @GetMapping("/admins/new")
    public String newAdminForm(HttpSession session, Model model) {
        if (!isPrimary(session)) {
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("sexValues", Sex.values());
        return "admin/admin_form";
    }

    @PostMapping("/admins")
    public String createAdmin(
            HttpSession session,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam Sex sex,
            Model model) {

        if (!isPrimary(session)) {
            return "redirect:/admin/dashboard";
        }
        if (userRepo.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Usuario ya existe");
            model.addAttribute("sexValues", Sex.values());
            return "admin/admin_form";
        }

        UserEntity u = new UserEntity(
                username,
                com.historias.clinicas.general.PasswordUtil.hash(password),
                firstName, lastName, sex, null, null, null);
        userRepo.save(u);
        adminRepo.save(new com.historias.clinicas.entity.Admin(u, false));

        return "redirect:/admin/admins";
    }

    @PostMapping("/admins/{id}/delete")
    public String deleteAdmin(HttpSession session, @PathVariable Integer id) {
        if (!isPrimary(session)) {
            return "redirect:/admin/dashboard";
        }
        UserEntity current = (UserEntity) session.getAttribute("USER");
        if (id.equals(current.getUserId())) {
            return "redirect:/admin/admins?error=self";
        }
        adminRepo.deleteById(id);
        return "redirect:/admin/admins";
    }

    /* === SELF PROFILE EDIT === */

    /**
     * Show edit form for current user's profile.
     */
    @GetMapping("/profile/edit")
    public String editProfileForm(HttpSession session, Model model) {
        UserEntity current = (UserEntity) session.getAttribute("USER");
        Optional<UserEntity> opt = userRepo.findById(current.getUserId());
        UserEntity user = opt.orElse(current);
        model.addAttribute("user", user);
        return "admin/profile_form";
    }

    /**
     * Process profile update (name, contact, password).
     */
    @PostMapping("/profile/edit")
    public String updateProfile(HttpSession session,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam(required = false) String phone,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) String password) {
        UserEntity current = (UserEntity) session.getAttribute("USER");
        Optional<UserEntity> opt = userRepo.findById(current.getUserId());
        if (opt.isPresent()) {
            UserEntity user = opt.get();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhone(phone);
            user.setEmail(email);
            if (password != null && !password.isBlank()) {
                user.setHashPassword(com.historias.clinicas.general.PasswordUtil.hash(password));
            }
            userRepo.save(user);
            session.setAttribute("USER", user);
        }
        return "redirect:/admin/dashboard";
    }

    private boolean isPrimary(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("USER");
        return user != null && adminRepo.existsByUserIdAndPrimaryAdminTrue(user.getUserId());
    }
}
