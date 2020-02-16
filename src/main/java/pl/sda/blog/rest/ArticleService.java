package pl.sda.blog.rest;

import java.util.UUID;

public interface ArticleService {

	void addComment(UUID articleId, String comment, String author);
}
