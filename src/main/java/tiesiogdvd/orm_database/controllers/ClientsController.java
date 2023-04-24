package tiesiogdvd.orm_database.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tiesiogdvd.orm_database.entities.Client;
import tiesiogdvd.orm_database.entities.ClientFile;
import tiesiogdvd.orm_database.entities.Group;
import tiesiogdvd.orm_database.repositories.ClientRepository;
import tiesiogdvd.orm_database.repositories.FileRepository;
import tiesiogdvd.orm_database.repositories.GroupRepository;
import tiesiogdvd.orm_database.services.FileStorageService;

import java.util.List;

@Controller
public class ClientsController {


    @Autowired
    public FileRepository fileRepository;
    @Autowired
    public ClientRepository clientRepository;
    @Autowired
    public GroupRepository groupRepository;

    @Autowired
    public FileStorageService fileStorageService;


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
        model.addAttribute("client", new Client());
        model.addAttribute("group", id);
        return "client_new";
    }

    @GetMapping("/clients/new")
    public String addClient(
            Model model
    ){
        List<Group> groups = groupRepository.findAll();
        model.addAttribute("client", new Client());
        model.addAttribute("groups", groups);
        return "client_new";
    }

    @PostMapping("/clients/{id}/new")
    public String storeClient(
            Model model,
            @PathVariable("id") Integer id,
            @Valid
            @ModelAttribute
            Client client,
            BindingResult result
    ){
        if(result.hasErrors()){
            List<Group> groups = groupRepository.findAll();
            model.addAttribute("groups", groups);
            return "client_new";
        }

        clientRepository.save(client);
        model.addAttribute("id", id);
        return "redirect:/clients/"+id;
    }


    @PostMapping("/clients/new")
    public String storeClient(
            Model model,
            @Valid
            @ModelAttribute
            Client client,
            BindingResult result
    ){
        if(result.hasErrors()){
            List<Group> groups = groupRepository.findAll();
            model.addAttribute("groups", groups);
            return "client_new";
        }
        clientRepository.save(client);
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

    @PostMapping("/clients/addfile/{client_id}")
    public String storeClientFile(
            @PathVariable("client_id") Integer clientId,
            @RequestParam("file") MultipartFile file

    ){
        Client client = clientRepository.getReferenceById(clientId);
        String filename = clientId + file.getOriginalFilename();
        String filepath = fileStorageService.store(file,filename);
        if(filepath!=null){
            ClientFile clientFile = new ClientFile(filepath, client);
            fileRepository.save(clientFile);
        }
        return "redirect:/clients/update/"+clientId;
    }

    @PostMapping("/clients/{id}/addfile/{client_id}")
    public String storeGroupClientFile(
            @PathVariable("id") Integer groupId,
            @PathVariable("client_id") Integer clientId,
            @RequestParam("file") MultipartFile file

    ){
        Client client = clientRepository.getReferenceById(clientId);
        String filename = clientId + file.getOriginalFilename();
        String filepath = fileStorageService.store(file,filename);
        if(filepath!=null){
            ClientFile clientFile = new ClientFile(filepath, client);
            fileRepository.save(clientFile);
        }
        return "redirect:/clients/" + groupId + "/update/"+clientId;
    }

    @GetMapping("/clients/file/{file_id}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(
            @PathVariable Integer file_id
            ){
        ClientFile clientFile = fileRepository.getReferenceById(file_id);
        Resource resource = fileStorageService.loadFile(clientFile.getFilePath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"")
                .body(resource);
    }


    @GetMapping("/clients/deletefile/{file_id}")
    @ResponseBody
    public ResponseEntity<Resource> removeFile(
            @PathVariable Integer file_id
    ){
        ClientFile clientFile = fileRepository.getReferenceById(file_id);
        fileStorageService.delete(clientFile.getFilePath());
        fileRepository.delete(clientFile);
        return ResponseEntity.ok().build();
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
