package pl.sda.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.blog.rest.ArticleRepository;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(roles = "ADMIN")
@SpringBootTest
@AutoConfigureMockMvc
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ArticlesControllerTest {
    public static final String ARTICLES = "/rest/articles";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void cleanDb() {
        articleRepository.deleteAll();
    }

    // @formatter:off
    @DisplayName(
            "when GET on /rest/articles, " +
            "then 200 status is returned"
    )
    // @formatter:on
    @Test
    void test() throws Exception {
        mockMvc
                .perform(get("/rest/articles"))
                .andExpect(status().isOk());
    }

    // @formatter:off
    @DisplayName(
            "given article with title 'My First Article', " +
            "when POST this article on /rest/articles, " +
            "then the same article can get retrieved by calling GET on /rest/articles"
    )
    // @formatter:on
    @Test
    void test1() throws Exception {
        // @formatter:off
        // given
        String articleToSend = "{ \"title\": \"My First Article\" }";

        // when
        mockMvc
                .perform(
                        post(ARTICLES)
                        .content(articleToSend)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());

        // then
        mockMvc.perform(get(ARTICLES))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("My First Article")));
        // @formatter:on
    }

    // @formatter:off
    @DisplayName(
            "given two created articles with titles 'My First article' and 'My Second Article', " +
            "when GET /rest/articles/id, where id is id of the first article, " +
            "then only the first article is returned"
    )
    // @formatter:on
    @Test
    void test2() throws Exception {
        // @formatter:off
        // given
        String articleToSend1 = "{ \"title\": \"My First Article\" }";
        String articleToSend2 = "{ \"title\": \"My Second Article\" }";
        UUID idOfTheFirstArticle = createArticle(articleToSend1);
        createArticle(articleToSend2);

        // when
        mockMvc.perform(get("/rest/articles/{id}", idOfTheFirstArticle))

        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("My First Article")));
        // @formatter:on
    }

    // @formatter:off
    @DisplayName(
            "given two created articles with titles 'My First article' and 'My Second Article', " +
            "when PUT new article with title 'Updated Article' on /rest/articles/id, where id is id of the first article, " +
            "then the title of the first article is now 'Updated Article'"
    )
    // @formatter:on
    @Test
    void test3() throws Exception {
        // @formatter:off
        // given
        String articleToSend1 = "{ \"title\": \"My First Article\" }";
        String articleToSend2 = "{ \"title\": \"My Second Article\" }";
        UUID idOfTheFirstArticle = createArticle(articleToSend1);
        createArticle(articleToSend2);

        // when
        String newArticleThatReplacesTheFirstOne = "{ \"title\": \"Updated Article\" }";
        mockMvc.perform(
                put("/rest/articles/{id}", idOfTheFirstArticle)
                .content(newArticleThatReplacesTheFirstOne)
                .contentType(MediaType.APPLICATION_JSON)
        )

        // then
                .andExpect(status().isOk());
        mockMvc.perform(get("/rest/articles/{id}", idOfTheFirstArticle))
                .andExpect(jsonPath("$.title", is("Updated Article")));
        // @formatter:on
    }

    // @formatter:off
    @DisplayName(
            "given two created articles with titles 'My First article' and 'My Second Article', " +
            "when DELETE article with /rest/articles/id, where id is id of the first article, " +
            "then the first article will be removed " +
            "and thus GET on /article/id, where id is id of the first article returns 404"
    )
    // @formatter:on
    @Test
    void test4() throws Exception {
        // @formatter:off
        // given
        String articleToSend1 = "{ \"title\": \"My First Article\" }";
        String articleToSend2 = "{ \"title\": \"My Second Article\" }";
        UUID idOfTheFirstArticle = createArticle(articleToSend1);
        createArticle(articleToSend2);

        // when
        mockMvc.perform(
                delete("/rest/articles/{id}", idOfTheFirstArticle)
        )

                // then
                .andExpect(status().isOk());
        mockMvc.perform(get("/rest/articles/{id}", idOfTheFirstArticle))
                .andExpect(status().is(404));
        // @formatter:on
    }

    private UUID createArticle(String article) throws Exception {
        String resultAsJson = mockMvc
                .perform(
                        post(ARTICLES)
                                .content(article)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Article createdArticle = objectMapper.readValue(resultAsJson, Article.class);

        return createdArticle.getId();
    }
}
