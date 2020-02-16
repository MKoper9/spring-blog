package pl.sda.blog.rest;

import org.springframework.stereotype.Service;
import pl.sda.blog.Comment;

import java.time.Instant;
import java.util.UUID;

@Service
public class RepositoryArticleService implements ArticleService {

	private ArticleRepository articleRepository;

	public RepositoryArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	@Override
	public void addComment(UUID articleId, String comment, String author) {
		articleRepository.findById(articleId).ifPresent(article -> {
			Comment commentEntity = createCommentEntity(comment,
				author);
			article.getComments().add(commentEntity);
			articleRepository.save(article);
		});
	}

	private Comment createCommentEntity(String content, String author) {
		return new Comment(content, Instant.now(), author);
	}
}
