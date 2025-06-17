package com.historias.clinicas.controllers;

import com.historias.clinicas.entity.Medication;
import com.historias.clinicas.entity.Prescription;
import com.historias.clinicas.entity.Episode;
import com.historias.clinicas.repositories.MedicationRepository;
import com.historias.clinicas.repositories.PrescriptionRepository;
import com.historias.clinicas.repositories.EpisodeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/professional/medications")
public class ProfessionalMedicationController {

    private final MedicationRepository medRepo;
    private final PrescriptionRepository prescRepo;
    private final EpisodeRepository epiRepo;

    public ProfessionalMedicationController(MedicationRepository medRepo,
                                            PrescriptionRepository prescRepo,
                                            EpisodeRepository epiRepo) {
        this.medRepo   = medRepo;
        this.prescRepo = prescRepo;
        this.epiRepo   = epiRepo;
    }

    /** Listar todas las medicaciones disponibles. */
    @GetMapping
    public String listMedications(Model model) {
        model.addAttribute("medications", medRepo.findAll());
        return "medications/list";
    }

    /** Formulario nuevo medicamento (integrado en sidebar). */
    @GetMapping("/new")
    public String newMedicationForm(Model model) {
        model.addAttribute("medication", new Medication());
        return "medications/form";
    }

    /** Alta de medicamento. */
    @PostMapping
    public String createMedication(@ModelAttribute Medication medication) {
        medRepo.save(medication);
        return "redirect:/professional/medications";
    }

    /** Formulario edición medicamento. */
    @GetMapping("/{id}/edit")
    public String editMedicationForm(@PathVariable Integer id, Model model) {
        Medication medication = medRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Medicamento no encontrado: " + id));
        model.addAttribute("medication", medication);
        return "medications/form";
    }

    /** Procesar edición de medicamento. */
    @PostMapping("/{id}/edit")
    public String updateMedication(@PathVariable Integer id,
                                   @ModelAttribute Medication med) {
        Medication existing = medRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Medicamento no encontrado: " + id));
        existing.setName(med.getName());
        existing.setForm(med.getForm());
        existing.setConcentration(med.getConcentration());
        medRepo.save(existing);
        return "redirect:/professional/medications";
    }
    
    @PostMapping("/{id}/delete")
    public String deleteMedication(@PathVariable Integer id) {
        medRepo.deleteById(id);
        return "redirect:/professional/medications";
    }

    /* — Prescripción existente, no toca — */
    @GetMapping("/prescribe")
    public String prescribeForm(Model model, HttpSession session) {
        Integer profId = getCurrentProfId(session);
        List<Episode> openEpisodes =
            epiRepo.findByProfessionalUserUserIdAndDateOutIsNull(profId);

        model.addAttribute("episodes",    openEpisodes);
        model.addAttribute("medications", medRepo.findAll());
        model.addAttribute("routes",      Prescription.Route.values());
        return "medications/prescribe";
    }
    @PostMapping("/prescribe")
    public String prescribe(
            @RequestParam("episodeId")    Integer episodeId,
            @RequestParam("medicationId") Integer medicationId,
            @RequestParam String dose,
            @RequestParam Prescription.Route route,
            @RequestParam(required = false) String frequency) {

        Episode     episode    = epiRepo.findById(episodeId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Episodio no encontrado: " + episodeId));
        Medication  medication = medRepo.findById(medicationId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Medicamento no encontrado: " + medicationId));

        prescRepo.save(new Prescription() {{
            setEpisode(episode);
            setMedication(medication);
            setDose(dose);
            setRoute(route);
            setFrequency(frequency);
            setStartDt(LocalDateTime.now());
        }});
        return "redirect:/professional/medications";
    }

    private Integer getCurrentProfId(HttpSession session) {
        return ((com.historias.clinicas.entity.UserEntity)
               session.getAttribute("USER")).getUserId();
    }
}
