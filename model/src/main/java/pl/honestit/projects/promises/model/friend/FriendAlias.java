package pl.honestit.projects.promises.model.friend;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"friendName", "alias"})
public class FriendAlias {

    private String friendName;
    private String alias;
    private String description;
}
