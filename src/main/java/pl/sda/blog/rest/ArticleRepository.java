package pl.sda.blog.rest;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.blog.Article;

import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

}
