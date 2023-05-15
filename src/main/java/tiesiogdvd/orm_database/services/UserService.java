package tiesiogdvd.orm_database.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tiesiogdvd.orm_database.entities.Client;
import tiesiogdvd.orm_database.repositories.ClientRepository;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client=clientRepository.findClientByUsername(username);
        if (client==null){
            throw  new UsernameNotFoundException("Vartotojas nerastas");
        }
        return client;
    }
}
