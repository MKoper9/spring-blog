package pl.sda.blog.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sda.blog.Article;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/rest/articles")
public class ArticlesRestController {
    private ArticleRepository articleRepository;

    public ArticlesRestController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Article postArticles(@RequestBody Article article) {
        return articleRepository.save(article);
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

    @PutMapping("/{id}")
    public Optional<Article> putArticle(@PathVariable("id") UUID uuid, @RequestBody Article articleThatWillReplaceTheOldOne) {
        return articleRepository.findById(uuid).map(article ->
                {
                    article.setTitle(articleThatWillReplaceTheOldOne.getTitle());
                    return articleRepository.save(article);
                }
        );
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable("id") UUID uuid) {
        articleRepository.deleteById(uuid);
    }
}
