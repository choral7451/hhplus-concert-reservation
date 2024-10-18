package hhplus.hhplusconcertreservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
@EnableJpaRepositories(repositoryImplementationPostfix = "CustomPostFix")
public class HhplusConcertReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(HhplusConcertReservationApplication.class, args);
	}

}
