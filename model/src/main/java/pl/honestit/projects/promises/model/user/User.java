package pl.honestit.projects.promises.model.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"username"})
public class User {

    private String username;
    private String nickname;
}
