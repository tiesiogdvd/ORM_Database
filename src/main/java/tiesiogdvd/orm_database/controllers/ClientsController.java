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
import tiesiogdvd.orm_database.repositories.ClientRepository;
import tiesiogdvd.orm_database.repositories.GroupRepository;

import java.util.List;

@Controller
public class ClientsController {
    @Autowired
    public ClientRepository clientRepository;
    @Autowired
    public GroupRepository groupRepository;


    @GetMapping("/clients/{id}")
    public String clientsFromGroup(
            Model model,
            @PathVariable("id") Integer id
            ){
        Group group = groupRepository.getReferenceById(id);
        model.addAttribute("group", group);
        return "client_list";
    }

    @GetMapping("/clients")
    public String clientsAll(
            Model model
    ){
        List<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "client_list_all";
    }

    @GetMapping("/clients/{id}/new")
    public String addClientToGroup(
            Model model,
            @PathVariable("id") Integer id
    ){
        model.addAttribute("group", id);
        return "client_new";
    }

    @GetMapping("/clients/new")
    public String addClient(
            Model model
    ){
        List<Group> groups = groupRepository.findAll();
        model.addAttribute("groups", groups);
        return "client_new";
    }

    @PostMapping("/clients/{id}/new")
    public String storeClient(
            @PathVariable("id") Integer id,
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone
    ){
        Group group = groupRepository.getReferenceById(id);
        Client client = new Client(name, surname, email, phone, group);
        clientRepository.save(client);
        return "redirect:/clients/"+id;
    }


    @PostMapping("/clients/new")
    public String storeClient(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam(value = "group_id", required = false) Integer groupId
    ){

        if(groupId!=null){
            Group group = groupRepository.getReferenceById(groupId);
            Client client = new Client(name, surname, email, phone, group);
            clientRepository.save(client);
        }else{
            Client client = new Client(name, surname, email, phone);
            clientRepository.save(client);
        }


        return "redirect:/clients";
    }

    @GetMapping("/clients/{id}/update/{client_id}")
    public String updateClientFromGroup(
            Model model,
            @PathVariable("id") Integer id,
            @PathVariable("client_id") Integer clientId
    ){
        Client client = clientRepository.getReferenceById(clientId);
        Group group=groupRepository.getReferenceById(id);
        model.addAttribute("group", group);
        model.addAttribute("client", client);
        return "client_update";
    }

    @GetMapping("/clients/update/{client_id}")
    public String updateClient(
            Model model,
            @PathVariable("client_id") Integer clientId
    ){
        List<Group> groups = groupRepository.findAll();
        model.addAttribute("groups", groups);
        Client client = clientRepository.getReferenceById(clientId);
        model.addAttribute("client", client);
        return "client_update";
    }

    @PostMapping("/clients/{id}/update/{client_id}")
    public String saveClientFromGroup(
            @PathVariable("id") Integer id,
            @PathVariable("client_id") Integer clientId,
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone
    ){
        Client client = clientRepository.getReferenceById(clientId);
        client.setName(name);
        client.setSurname(surname);
        client.setEmail(email);
        client.setPhone(phone);
        clientRepository.save(client);
        return "redirect:/clients/"+id;
    }

    @PostMapping("/clients/update/{client_id}")
    public String saveClient(
            @PathVariable("client_id") Integer clientId,
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("group_id") Integer groupId
    ){
        Group group = groupRepository.getReferenceById(groupId);
        Client client = clientRepository.getReferenceById(clientId);
        client.setName(name);
        client.setSurname(surname);
        client.setEmail(email);
        client.setPhone(phone);
        client.setGroup(group);
        clientRepository.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/clients/{id}/delete/{client_id}")
    public  String deleteGroupClient(
            @PathVariable("id") Integer id,
            @PathVariable("client_id") Integer clientId
    ){
        clientRepository.deleteById(clientId);
        return "redirect:/clients/"+id;
    }

    @GetMapping("/clients/delete/{client_id}")
    public  String deleteClient(
            @PathVariable("client_id") Integer clientId
    ){
        clientRepository.deleteById(clientId);
        return "redirect:/clients";
    }

}
