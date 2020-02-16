package pl.sda.blog.rest;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

@Component
public class ArticlePermissionEvaluator implements PermissionEvaluator {

	private ArticleRepository articleRepository;

	public ArticlePermissionEvaluator(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	@Override
	public boolean hasPermission(Authentication authentication,
				     Object targetDomainObject,
				     Object permission) {
		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication,
				     Serializable targetId, String targetType,
				     Object permission) {
		if (!permission.equals("OWNER")) {
			return false;
		}

		UUID id = (UUID) targetId;
		return articleRepository.findById(id).map(article -> {
			String authorOfTheArticle = article.getAuthor();
			String user = authentication.getName();
			return user.equals(authorOfTheArticle);
		}).orElse(false);
	}
}
