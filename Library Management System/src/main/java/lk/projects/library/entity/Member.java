package lk.projects.library.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Member {

    private Integer id;
    private String fullname;
    private String code;
    private LocalDate dob;
    private String nic;
    private String address;
    private LocalDate doregistered;
    private Gender gender;
    private MemberStatus memberstatus;
    private User user;
}
