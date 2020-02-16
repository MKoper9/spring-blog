package pl.sda.blog;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Data
@Entity
public class Article {

	@Id
	@GeneratedValue
	private UUID id;
	private String title;
	private String content;
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Collection<Comment> comments;
}
