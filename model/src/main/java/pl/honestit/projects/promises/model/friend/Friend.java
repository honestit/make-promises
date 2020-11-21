package pl.honestit.projects.promises.model.friend;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"ownerity", "identity", "name"})
public class Friend {

    private String name;
    private Set<FriendAlias> aliases;
    private FriendOwnerity ownerity;
    private FriendIdentity identity;
}
