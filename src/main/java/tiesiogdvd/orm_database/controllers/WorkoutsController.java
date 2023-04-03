package tiesiogdvd.orm_database.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tiesiogdvd.orm_database.entities.Client;
import tiesiogdvd.orm_database.entities.Group;
import tiesiogdvd.orm_database.entities.Workout;
import tiesiogdvd.orm_database.repositories.ClientRepository;
import tiesiogdvd.orm_database.repositories.RegistrationRepository;
import tiesiogdvd.orm_database.repositories.WorkoutRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
public class WorkoutsController {
    @Autowired
    public WorkoutRepository workoutRepository;
    @Autowired
    public RegistrationRepository registrationRepository;
    @Autowired
    public ClientRepository clientRepository;

    @GetMapping("/workouts")
    public String workouts(Model model){
        List<Workout> workoutList = workoutRepository.findAll();
        model.addAttribute("workouts",workoutList);
        return "workout_list";
    }

    @GetMapping("/workouts/new")
    public String addWorkout(Model model){
        List<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "workout_new";
    }

    @PostMapping("/workouts/new")
    public String storeWorkout(
            Model model,
            @RequestParam("name") String name,
            @RequestParam("places") Integer places,
            @RequestParam("location") String location,
            @RequestParam("time") String timeString
    ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(timeString, formatter);

        // Convert LocalDateTime to Date
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date time = Date.from(instant);

        Workout workout = new Workout(name,time,places,location);
        workoutRepository.save(workout);
        return "redirect:/workouts";
    }


    @GetMapping("/workouts/update/{id}")
    public String updateWorkout(
            Model model,
            @PathVariable("id") Integer id
    ){
        Workout workout = workoutRepository.getReferenceById(id);
        model.addAttribute("workout", workout);
        return "workout_update";
    }

    @PostMapping("/workouts/update/{id}")
    public String saveUpdatedWorkout(
            @PathVariable("id") Integer id,
            @RequestParam("name") String name,
            @RequestParam("time") String timeString,
            @RequestParam("places") Integer places,
            @RequestParam("location") String location
    ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(timeString, formatter);

        // Convert LocalDateTime to Date
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date time = Date.from(instant);


        Workout workout = workoutRepository.getReferenceById(id);
        workout.setName(name);
        workout.setDate(time);
        workout.setPlaces(places);
        workout.setLocation(location);
        workoutRepository.save(workout);
        return "redirect:/workouts";
    }


    @GetMapping("/workouts/delete/{id}")
    public  String deleteWorkout(
            @PathVariable("id") Integer id
    ){
        workoutRepository.deleteById(id);
        return "redirect:/workouts";
    }
}
