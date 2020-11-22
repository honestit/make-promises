package pl.honestit.projects.promises.maker.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Optional;

@Entity
@Table(name = "promises")
@SecondaryTable(name = "promises_deadlines", pkJoinColumns = @PrimaryKeyJoinColumn)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"who", "whom", "what", "when", "till"})
public class PromiseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(nullable = false)
    private String who;
    @Column(nullable = false)
    private String whom;
    @Column(nullable = false)
    private String what;
    @Column(nullable = false, table = "promises_deadlines")
    private ZonedDateTime when;
    @Column(nullable = false, table = "promises_deadlines")
    private ZonedDateTime till;
    private ZonedDateTime pass;
    private Boolean kept;

    Optional<Boolean> isKept() {
        if (pass == null) {
            return Optional.empty();
        }
        return Optional.of(pass.isBefore(till));
    }

}
