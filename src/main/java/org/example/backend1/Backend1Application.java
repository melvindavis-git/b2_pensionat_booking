package org.example.backend1;

import org.example.backend1.Model.Customer;
import org.example.backend1.Model.Room;
import org.example.backend1.Repository.CustomerRepository;
import org.example.backend1.Repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Backend1Application {

    public static void main(String[] args) {
        SpringApplication.run(Backend1Application.class, args);
    }

    @Bean
    public CommandLineRunner createRooms(RoomRepository roomRepo, CustomerRepository customerRepo) {
        return (args) -> {
            roomRepo.save(new Room("1", false));
            roomRepo.save(new Room("2", false));
            roomRepo.save(new Room("3", false));
            roomRepo.save(new Room("4", false));
            roomRepo.save(new Room("5", false));
            roomRepo.save(new Room("6", false));
            roomRepo.save(new Room("7", false));
            roomRepo.save(new Room("8", false));
            roomRepo.save(new Room("9", false));
            roomRepo.save(new Room("10", false));
            customerRepo.save(new Customer("Melvin", "melvin@gmail.com", "070123456789"));
            customerRepo.save(new Customer("Tungvall", "tungvall@gmail.com", "070123456789"));
            customerRepo.save(new Customer("Tim", "tim@gmail.com", "070123456789"));
        };
    }

    //TODO: DTO-klasser (service lagret, från entity till DTO)
    //TODO: låt controllers hantera thymeleaf-mallar
    //TODO: ta bort kund (bara om bokningar ej finns)
    //TODO: ett rum kan bokas av kund (+en eller fler nätter, datum)
    //TODO: avboka ett rum och ändra bokning
    //TODO: ett rum kan vara enkel- eller dubbelrum
    //TODO: dubbelrum kan ha fler sängar (1-2)
    //TODO: söka på datum/datumintervall och antal personer - få fram alla lediga rum
    //TODO: ha vettiga felmeddelanden, använd validerings-annoteringar i modell-klasser
    //TODO: enhetstester


}
