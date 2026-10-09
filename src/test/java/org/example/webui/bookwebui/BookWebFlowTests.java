package org.example.webui.bookwebui;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class BookWebFlowTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void rendersPagesAndShowsRegisteredBook() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/list"))
                .andExpect(content().string(containsString("등록된 책이 없습니다.")));

        mockMvc.perform(get("/books/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/form"))
                .andExpect(content().string(containsString("name=\"title\"")));

        mockMvc.perform(post("/books")
                        .param("title", "테스트 도서")
                        .param("author", "테스트 저자")
                        .param("price", "15000")
                        .param("category", "프로그래밍")
                        .param("isbn", "9781234567890"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("테스트 도서")))
                .andExpect(content().string(containsString("테스트 저자")))
                .andExpect(content().string(containsString("15000")))
                .andExpect(content().string(containsString("프로그래밍")))
                .andExpect(content().string(containsString("9781234567890")));

        mockMvc.perform(get("/css/style.css"))
                .andExpect(status().isOk());
    }
}
