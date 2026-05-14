package org.example.backend1;

import org.example.backend1.Model.Room;
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
    public CommandLineRunner createRooms(RoomRepository repo){
        return (args) -> {
            repo.save(new Room("1", false, 1L));
        };
    }

}
