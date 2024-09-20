package lk.projects.library.entity;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {
    private Integer id;
    private String username;
    private String password;
    private String fullname;
    private Role role;
    private UserStatus userstatus;
}
