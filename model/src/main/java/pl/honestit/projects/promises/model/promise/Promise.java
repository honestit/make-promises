package pl.honestit.projects.promises.model.promise;

import lombok.*;
import pl.honestit.projects.promises.model.friend.Friend;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"who", "what", "who", "when", "till"})
public class Promise {

    private String who;
    private String what;
    private Friend whom;
    private ZonedDateTime when;
    private Deadline till;
}