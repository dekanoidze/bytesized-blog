package ge.tsu.ByteSized.controller;

import ge.tsu.ByteSized.dto.BlogPostResponse;
import ge.tsu.ByteSized.service.BlogPostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlogPostRestControllerTest {

    @Mock
    private BlogPostService blogPostService;

    @InjectMocks
    private BlogPostRestController blogPostRestController;

    @Test
    void allPosts_returnsListFromService() {
        BlogPostResponse response = new BlogPostResponse(
                1L,
                "Test Post",
                "Sample content for controller test.",
                null,
                LocalDateTime.of(2026, 6, 12, 12, 0),
                List.of());

        when(blogPostService.findAllForApi()).thenReturn(List.of(response));

        List<BlogPostResponse> result = blogPostRestController.allPosts();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().title()).isEqualTo("Test Post");
    }
}
