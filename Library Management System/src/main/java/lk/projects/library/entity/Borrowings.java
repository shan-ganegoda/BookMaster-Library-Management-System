package lk.projects.library.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Borrowings {

    private Integer id;
    private String code;
    private LocalDate doborrowed;
    private LocalDate dohandedover;
    private BorrowStatus borrowStatus;
    private Books books;
    private Member member;
}
