package pl.honestit.projects.promises.client.makers;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSecurityDetails implements Serializable {

    private String password;
    private String provider;
    private Boolean activateRequired;
}
