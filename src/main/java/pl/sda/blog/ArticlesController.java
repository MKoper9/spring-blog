package pl.sda.blog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ArticlesController {
    private ArticleRepository articleRepository;

    public ArticlesController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/articles")
    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/articles")
    public Article postArticles(@RequestBody Article article) {
        return articleRepository.save(article);
    }

    @GetMapping("/articles/{id}")
    public Optional<Article> getArticleById(@PathVariable("id") UUID uuid) {
        return articleRepository.findById(uuid);
    }
}
