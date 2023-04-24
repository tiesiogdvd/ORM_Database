package tiesiogdvd.orm_database.services;

import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    public void printInfo(){
        System.out.println("Servisas");
    }

    public String store(MultipartFile file, String fileName){
        try {
            Path target = Paths.get("uploads").resolve(fileName).toAbsolutePath();
            Files.copy(file.getInputStream(),target, StandardCopyOption.REPLACE_EXISTING);
            return target.toString();
        } catch (IOException e) {
            System.out.println("Issaugojimo klaida" + e);
            e.printStackTrace();
        }
        return null;
    }

    public Resource loadFile(String filePath){
        return new PathResource(filePath);
    }

    public void delete(String filePath){
        File file = new File(filePath);
        file.delete();
    }
}
