package lk.projects.library.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Category {
    private Integer id;
    private String name;
    private String code;
}
