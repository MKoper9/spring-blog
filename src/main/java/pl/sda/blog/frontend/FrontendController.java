package pl.sda.blog.frontend;

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

    public FrontendController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/frontend/articles")
    public ModelAndView showAllArticles() throws URISyntaxException {
        Map<String, List<Article>> articlesMap = new HashMap<>();

        RequestEntity requestEntity = RequestEntity.get(new URI("http://localhost:8080/articles")).build();
        ResponseEntity<List<Article>> articles = restTemplate.exchange(requestEntity,
                new ParameterizedTypeReference<List<Article>>() {
        });
        articlesMap.put("articles", articles.getBody());

        return new ModelAndView("articles", articlesMap);
    }
}
