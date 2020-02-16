package pl.sda.blog.frontend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.blog.Article;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FrontendController {

	private RestTemplate restTemplate;
	private String articlesRestApiEndpoint;

	public FrontendController(RestTemplate restTemplate, @Value("${pl.sda" +
                ".blog.rest.articles}") String articlesRestApiEndpoint) {
		this.restTemplate = restTemplate;
		this.articlesRestApiEndpoint = articlesRestApiEndpoint;
	}

	@GetMapping("/frontend/articles")
	public ModelAndView showAllArticles() throws URISyntaxException {
		Map<String, List<Article>> articlesMap = new HashMap<>();

		RequestEntity requestEntity = RequestEntity
			.get(new URI(articlesRestApiEndpoint))
			.header("Authorization", "Basic dGVzdF91c2VyOnRlc3RfdXNlcl9wYXNzd29yZA==")
			.build();
		ResponseEntity<List<Article>> articles = restTemplate
			.exchange(requestEntity, new ParameterizedTypeReference<List<Article>>() {
			});
		articlesMap.put("articles", articles.getBody());

		return new ModelAndView("articles", articlesMap);
	}
}
