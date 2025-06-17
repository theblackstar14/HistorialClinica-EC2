package com.historias.clinicas.controllers;

import com.historias.clinicas.entity.Patient;
import com.historias.clinicas.entity.UserEntity;
import com.historias.clinicas.entity.enums.DocType;
import com.historias.clinicas.general.PasswordUtil;
import com.historias.clinicas.repositories.PatientRepository;
import com.historias.clinicas.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/professional/patients")
public class ProfessionalPatientController {

    private final PatientRepository patientRepo;
    private final UserRepository    userRepo;

    public ProfessionalPatientController(PatientRepository patientRepo,
                                         UserRepository userRepo) {
        this.patientRepo = patientRepo;
        this.userRepo    = userRepo;
    }

    /** Listado de pacientes */
    @GetMapping
    public String list(Model model) {
        List<Patient> patients = patientRepo.findAll();
        model.addAttribute("patients", patients);
        return "professional/list";
    }

    /** Formulario nuevo paciente */
    @GetMapping("/new")
    public String form(Model model) {
        Patient patient = new Patient();
        patient.setUser(new UserEntity());
        model.addAttribute("patient", patient);
        model.addAttribute("docTypes", DocType.values());
        return "professional/form";
    }

    /** Alta de paciente */
    @PostMapping
    public String create(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam DocType docType,
            @RequestParam String docNumber,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(required = false) String address,
            Model model) {

        if (userRepo.findByUsername(username).isPresent()) {
            model.addAttribute("error", "El usuario ya existe");
            Patient patient = new Patient();
            patient.setUser(new UserEntity());
            model.addAttribute("patient", patient);
            model.addAttribute("docTypes", DocType.values());
            return "professional/form";
        }

        UserEntity u = new UserEntity();
        u.setUsername(username);
        u.setHashPassword(PasswordUtil.hash(password));
        u.setFirstName(firstName);
        u.setLastName(lastName);
        userRepo.save(u);

        Patient p = new Patient(u, docType, docNumber, address);
        patientRepo.save(p);

        // Redirigir al dashboard, no a la lista antigua
        return "redirect:/professional/dashboard";
    }

    /** Formulario edición paciente */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        Patient p = patientRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado: " + id));
        model.addAttribute("patient", p);
        model.addAttribute("docTypes", DocType.values());
        return "professional/form";
    }

    /** Actualizar paciente */
    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Integer id,
            @RequestParam DocType docType,
            @RequestParam String docNumber,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(required = false) String address) {

        Patient p = patientRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado: " + id));
        UserEntity u = p.getUser();

        u.setFirstName(firstName);
        u.setLastName(lastName);
        userRepo.save(u);

        p.setDocType(docType);
        p.setDocNumber(docNumber);
        p.setAddress(address);
        patientRepo.save(p);

        return "redirect:/professional/patients";
    }

    /** Baja de paciente */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        patientRepo.deleteById(id);
        return "redirect:/professional/patients";
    }
}
