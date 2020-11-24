package pl.honestit.projects.promises.client.giving.first;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FirstPromiseCommand {

    @NotBlank
    private String who;
    @NotBlank
    private String what;
    @NotBlank
    private String whom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private LocalDate tillDate;
    @DateTimeFormat(pattern = "HH:mm:ss")
    @FutureOrPresent
    private LocalTime tillTime;

}
