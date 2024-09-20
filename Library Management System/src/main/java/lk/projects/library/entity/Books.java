package lk.projects.library.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.Year;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Books {

    private Integer id;
    private String title;
    private String author;
    private String code;
    private String publisher;
    private Integer yopublication;
    private String isbn;
    private Integer pages;
    private LocalDate doadded;
    private Category category;
    private Language language;
    private User user;
}
