package tiesiogdvd.orm_database.controllers;

import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tiesiogdvd.orm_database.entities.Client;
import tiesiogdvd.orm_database.entities.Group;
import tiesiogdvd.orm_database.entities.Registration;
import tiesiogdvd.orm_database.entities.Workout;
import tiesiogdvd.orm_database.repositories.ClientRepository;
import tiesiogdvd.orm_database.repositories.RegistrationRepository;
import tiesiogdvd.orm_database.repositories.WorkoutRepository;

import java.util.Date;
import java.util.List;

@Controller
public class RegistrationsController {

    @Autowired
    public WorkoutRepository workoutRepository;
    @Autowired
    public RegistrationRepository registrationRepository;
    @Autowired
    public ClientRepository clientRepository;

    @GetMapping("/registrations")
    public String registrationsAll(
            Model model
    ){
        List<Registration> registrationList = registrationRepository.findAll();
        model.addAttribute("registrations", registrationList);
        return "registration_list";
    }


    @GetMapping("/registrations/{id}")
    public String registrationsFromWork(
            Model model,
            @PathVariable("id") Integer id
    ){
        Workout workout = workoutRepository.getReferenceById(id);
        List<Registration> registrationList = workout.getRegistrations();
        model.addAttribute("registrations", registrationList);
        return "registration_list";
    }


    @GetMapping("/registrations/{id}/new")
    public String addRegistrationToWorkout(
            Model model,
            @PathVariable("id") Integer id
    ){
        List<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        model.addAttribute("workout", id);
        return "registration_new";
    }

    @GetMapping("/registrations/new")
    public String addRegistration(
            Model model
    ){
        List<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        List<Workout> workoutList = workoutRepository.findAll();
        model.addAttribute("workoutList", workoutList);
        return "registration_new";
    }


    @PostMapping("/registrations/{workout_id}/new")
    public String storeRegistrationToWorkout(
            @RequestParam("client_id") Integer clientId,
            @PathVariable("workout_id") Integer workoutId
    ){
        Client client = clientRepository.getReferenceById(clientId);
        Workout workout = workoutRepository.getReferenceById(workoutId);
        Registration registration = new Registration(new Date(), client, workout);
        registrationRepository.save(registration);
        return "redirect:/registrations/"+workoutId;
    }


    @PostMapping("/registrations/new")
    public String storeRegistration(
            @RequestParam("client_id") Integer clientId,
            @RequestParam("workout_id") Integer workoutId
    ){
        Client client = clientRepository.getReferenceById(clientId);
        Workout workout = workoutRepository.getReferenceById(workoutId);
        Registration registration = new Registration(new Date(), client, workout);
        registrationRepository.save(registration);
        return "redirect:/registrations";
    }


    @GetMapping("/registrations/{workout_id}/delete/{registration_id}")
    public  String deleteGroupClient(
            @PathVariable("workout_id") Integer id,
            @PathVariable("registration_id") Integer registrationId
    ){
        registrationRepository.deleteById(registrationId);
        return "redirect:/registrations/"+id;
    }

    @GetMapping("/registrations/delete/{registration_id}")
    public  String deleteClient(
            @PathVariable("registration_id") Integer registrationId
    ){
        registrationRepository.deleteById(registrationId);
        return "redirect:/registrations";
    }


}
