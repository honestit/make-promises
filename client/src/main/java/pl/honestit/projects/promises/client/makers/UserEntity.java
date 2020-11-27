package pl.honestit.projects.promises.client.makers;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "users")
@SecondaryTable(name = "users_securities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "username")
@ToString(exclude = "securityDetails")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @NaturalId
    private String username;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "password", column = @Column(table = "users_securities")),
            @AttributeOverride(name = "provider", column = @Column(table = "users_securities"))
    })
    private UserSecurityDetails securityDetails;
}
