package pl.sda.blog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RestSecurityTest {
    private static final String ARTICLES = "/rest/articles";
    @Autowired
    private MockMvc mockMvc;

    // @formatter:off
    @DisplayName(
            "when GET on /rest/articles without credentials, " +
            "then Unauthorized status is returned"
    )
    // @Formatter:on
    @Test
    void testUnauthorized() throws Exception {
        // when
        mockMvc.perform(get(ARTICLES))

                // then
                .andExpect(status().isUnauthorized());
    }

    // @formatter:off
    @DisplayName(
            "when GET on /rest/articles with test user credentials, " +
            "then OK status is returned"
    )
    // @formatter:on
    @Test
    void testAuthorized() throws Exception {
        // when
        mockMvc.perform(
                get(ARTICLES)
                        .with(httpBasic("test_user", "test_user_password"))
        )

                // then
                .andExpect(status().isOk());
    }
}
