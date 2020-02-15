package pl.sda.blog;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class Article {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String content;
}
