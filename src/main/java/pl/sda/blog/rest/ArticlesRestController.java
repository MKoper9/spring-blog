package pl.sda.blog.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.sda.blog.Article;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/rest/articles")
public class ArticlesRestController {

	private ArticleRepository articleRepository;
	private ArticleService articleService;

	public ArticlesRestController(ArticleRepository articleRepository,
				      ArticleService articleService) {
		this.articleRepository = articleRepository;
		this.articleService = articleService;
	}

	@GetMapping
	public List<Article> getArticles() {
		return articleRepository.findAll();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Article createArticle(@RequestBody Article article,
				     Principal principal) {
		article.setAuthor(principal.getName());
		return articleRepository.save(article);
	}

	@PostMapping("/{articleId}/comments")
	public void addComment(@PathVariable("articleId") UUID articleId,
			       @RequestBody AddCommentDto addCommentDto,
			       Principal principal) {
		articleService.addComment(articleId, addCommentDto
			.getContent(), principal.getName());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Article> getArticleById(@PathVariable("id") UUID uuid) {

		return articleRepository.findById(uuid)
			.map(article -> ResponseEntity.ok(article))
			.orElse(ResponseEntity.notFound().build());
/*
        if (articleRepository.findById(uuid).isPresent()) {
            return ResponseEntity.ok(articleRepository.findById(uuid));
        }
        return ResponseEntity.notFound().build();*/
	}

	@PreAuthorize("hasRole('ADMIN') || hasPermission(#uuid, 'pl.sda.blog" +
		".Article', 'OWNER')")
	@PutMapping("/{id}")
	public Optional<Article> editArticle(@PathVariable("id") UUID uuid,
					     @RequestBody Article articleThatWillReplaceTheOldOne) {
		return articleRepository.findById(uuid).map(article -> {
			article.setTitle(articleThatWillReplaceTheOldOne
				.getTitle());
			return articleRepository.save(article);
		});
	}

	@PreAuthorize("hasRole('ADMIN') || hasPermission(#uuid, 'pl.sda.blog" +
		".Article', 'OWNER')")
	@DeleteMapping("/{id}")
	public void deleteArticle(@PathVariable("id") UUID uuid) {
		articleRepository.deleteById(uuid);
	}
}
