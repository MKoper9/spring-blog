package pl.sda.blog;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticlesControllerTest {
    public static final String ARTICLES = "/articles";
    @Autowired
    private MockMvc mockMvc;

    // @formatter:off
    @DisplayName(
            "when GET on /articles, " +
            "then 200 status is returned"
    )
    // @formatter:on
    @Test
    void test() throws Exception {
        mockMvc
                .perform(get("/articles"))
                .andExpect(status().isOk());
    }

    // @formatter:off
    @DisplayName(
            "given article with title 'My First Article', " +
            "when POST this article on /articles, " +
            "then the same article can get retrieved by calling GET on /articles"
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
}
