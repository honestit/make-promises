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
public class FriendOwnerity {

    private String username;

    public static FriendOwnerity from(String username) {
        return new FriendOwnerity(username);
    }

    public static FriendOwnerity from(User user) {
        return new FriendOwnerity(user.getUsername());
    }
}
