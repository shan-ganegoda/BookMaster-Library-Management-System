package lk.projects.library.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChartData {

    private String month;
    private long count;
}
