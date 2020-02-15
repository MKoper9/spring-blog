package pl.sda.blog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ArticlesController {
    private ArticleRepository articleRepository;
    public ArticlesController(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    @GetMapping("/articles")
    public List<Article> getArticles(){
        return articleRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/articles")
    public void postArticles(@RequestBody Article article){
        articleRepository.save(article);
    }
}
