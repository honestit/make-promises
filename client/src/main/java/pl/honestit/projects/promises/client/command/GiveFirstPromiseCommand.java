package pl.honestit.projects.promises.client.command;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class GiveFirstPromiseCommand {

    private String who;
    private String what;
    private String whom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tillDate;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime tillTime;

}
