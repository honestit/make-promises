package pl.honestit.projects.promises.maker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.honestit.projects.promises.model.promise.RejectReason;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "rejected_promises")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RejectedPromiseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String who;
    private String what;
    private String whom;
    @Column(name = "\"when\"")
    private ZonedDateTime when;
    private ZonedDateTime till;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RejectReason reason;
}
