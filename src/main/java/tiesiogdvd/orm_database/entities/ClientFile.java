package tiesiogdvd.orm_database.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "clientFile")
public class ClientFile{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer file_id;

        @NotNull
        private String filePath;

        @ManyToOne(cascade = CascadeType.REMOVE)
        @JoinColumn(name = "client_id") //joins with parent id column
        private Client client;

        public ClientFile(String filePath, Client client) {
                this.filePath = filePath;
                this.client = client;
        }

        public ClientFile() {

        }

        public Integer getFile_id() {
                return file_id;
        }

        public void setFile_id(Integer file_id) {
                this.file_id = file_id;
        }

        public String getFilePath() {
                return filePath;
        }

        public void setFilePath(String filePath) {
                this.filePath = filePath;
        }

        public Client getClient() {
                return client;
        }

        public void setClient(Client client) {
                this.client = client;
        }
}
