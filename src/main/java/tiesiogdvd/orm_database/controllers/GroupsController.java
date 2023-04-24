package tiesiogdvd.orm_database.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tiesiogdvd.orm_database.entities.Group;
import tiesiogdvd.orm_database.repositories.GroupRepository;

import java.util.List;

@Controller
public class GroupsController {

    @Autowired
    public GroupRepository groupRepository;

    @GetMapping("/")
    public String groups(Model model){
        List<Group> groups = groupRepository.findAll();
        model.addAttribute("groups", groups);
        return "groups_list";
    }

    @GetMapping("/new")
    public String addGroup(Model model){
        model.addAttribute("group",new Group());

        return "groups_new";
    }

    @PostMapping("/new")
    public String storeGroup(
            @RequestParam("name") String name,
            @RequestParam("year") Integer year
    ){
        System.out.println("haha");
        Group group = new Group(name,year);
        groupRepository.save(group);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String updateGroup(
            Model model,
            @PathVariable("id") Integer id
            ){
        Group group=groupRepository.getReferenceById(id);
        model.addAttribute("group", group);
        return "groups_update";
    }

    @PostMapping("/update/{id}")
    public String saveGroup(
            @PathVariable("id") Integer id,
            @RequestParam("name") String name,
            @RequestParam("year") Integer year
    ){
        Group g=groupRepository.getReferenceById(id);
        g.setName(name);
        g.setYear(year);
        groupRepository.save(g);

        return "redirect:/";
    }


    @GetMapping("/delete/{id}")
    public  String deleteGroup(
            @PathVariable("id") Integer id
    ){
        groupRepository.deleteById(id);
        return "redirect:/";
    }
}
