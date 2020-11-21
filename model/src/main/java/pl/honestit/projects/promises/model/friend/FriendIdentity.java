package pl.honestit.projects.promises.model.friend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.honestit.projects.promises.model.user.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendIdentity {

    private String username;

    public static FriendIdentity from(String username) {
        return new FriendIdentity(username);
    }

    public static FriendIdentity from(User user) {
        return new FriendIdentity(user.getUsername());
    }
}
