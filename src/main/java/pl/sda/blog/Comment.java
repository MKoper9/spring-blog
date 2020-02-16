package pl.sda.blog;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class Comment {

	public Comment(String content, Instant timestamp, String author) {
		this.content = content;
		this.timestamp = timestamp;
		this.author = author;
	}

	@Id
	@GeneratedValue
	private UUID id;
	private String content;
	private Instant timestamp;
	private String author;
}
