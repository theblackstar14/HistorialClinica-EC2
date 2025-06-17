package com.historias.clinicas.controllers;

import com.historias.clinicas.entity.UserEntity;
import com.historias.clinicas.entity.enums.Role;
import com.historias.clinicas.general.PasswordUtil;
import com.historias.clinicas.repositories.AdminRepository;
import com.historias.clinicas.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller to allow any authenticated user (admin, professional or patient)
 * to edit their own profile.
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepo;
    private final AdminRepository adminRepo;

    public ProfileController(UserRepository userRepo, AdminRepository adminRepo) {
        this.userRepo = userRepo;
        this.adminRepo = adminRepo;
    }

    /**
     * Adds layout variables for the shared user_layout.
     */
    @ModelAttribute
    public void addLayoutAttributes(Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("USER");
        Role role = (Role) session.getAttribute("ROLE");
        String rolePath = (role != null) ? role.name().toLowerCase() : "";
        model.addAttribute("pageTitle", "Perfil · Historias Clínicas");
        model.addAttribute("appTitle", "Historias Clínicas");
        model.addAttribute("homePath", rolePath + "/dashboard");
        model.addAttribute("profileEditPath", "profile/edit");
    }

    /**
     * Exposes the current user in the model for form binding.
     */
    @ModelAttribute("user")
    public UserEntity currentUser(HttpSession session) {
        return (UserEntity) session.getAttribute("USER");
    }

    /**
     * Shows the edit form for the current user's profile.
     */
    @GetMapping("/edit")
    public String showEditForm(HttpSession session) {
        Role role = (Role) session.getAttribute("ROLE");
        if (role == Role.ADMIN) {
            return "admin/profile_form";
        } else if (role == Role.PROFESSIONAL) {
            return "professional/profile_form";
        } else {
            return "patient/profile_form";
        }
    }

    /**
     * Processes updates to the user's profile.
     */
    @PostMapping("/edit")
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
                user.setHashPassword(PasswordUtil.hash(password));
            }
            userRepo.save(user);
            session.setAttribute("USER", user);
        }
        // redirect back to the dashboard
        Role role = (Role) session.getAttribute("ROLE");
        if (role == Role.ADMIN) {
            return "redirect:/admin/dashboard";
        } else if (role == Role.PROFESSIONAL) {
            return "redirect:/professional/dashboard";
        } else {
            return "redirect:/patient/dashboard";
        }
    }
}
