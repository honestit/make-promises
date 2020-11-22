package pl.honestit.projects.promises.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.honestit.projects.promises.context.PromiseMaker;
import pl.honestit.projects.promises.model.friend.Friend;
import pl.honestit.projects.promises.model.promise.Deadline;
import pl.honestit.projects.promises.model.promise.Promise;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.TimeZone;

@SpringBootApplication
public class MakePromisesClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(MakePromisesClientApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(PromiseMaker promiseMaker, ObjectMapper objectMapper) {
		return args -> {
			Promise promise = new Promise();
			promise.setWho("Albrecht");
			promise.setWhom(Friend.builder()
					.name("Melchior")
					.build());
			promise.setWhat("Give a lot of help!");

			promise.setWhen(ZonedDateTime.now());
			promise.setTill(Deadline.builder()
					.zoneId(TimeZone.getDefault().toZoneId())
					.tillDate(LocalDate.now().plusDays(3))
					.tillTime(LocalTime.now())
					.build());

			String json = objectMapper.writeValueAsString(promise);
			System.out.println(json);

			promiseMaker.make(promise);
		};
	}

}
