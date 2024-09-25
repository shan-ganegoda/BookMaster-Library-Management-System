package lk.projects.library.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Fine {
    private Integer id;
    private Borrowings borrowings;
    private Double fine;
    private FineStatus fineStatus;
    private Integer latedays;
}
