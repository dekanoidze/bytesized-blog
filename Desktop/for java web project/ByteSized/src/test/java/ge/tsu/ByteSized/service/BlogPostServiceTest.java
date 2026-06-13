package ge.tsu.ByteSized.service;

import ge.tsu.ByteSized.dto.BlogPostResponse;
import ge.tsu.ByteSized.dto.CreateBlogPostRequest;
import ge.tsu.ByteSized.model.BlogPost;
import ge.tsu.ByteSized.repository.BlogPostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlogPostServiceTest {

    @Mock
    private BlogPostRepository blogPostRepository;

    @InjectMocks
    private BlogPostService blogPostService;

    @Test
    void createPost_trimsFieldsAndSavesPost() {
        CreateBlogPostRequest request = new CreateBlogPostRequest();
        request.setTitle("  Hello World  ");
        request.setContent("  This is enough content for validation.  ");

        BlogPost savedPost = new BlogPost();
        savedPost.setId(1L);
        savedPost.setTitle("Hello World");
        savedPost.setContent("This is enough content for validation.");
        savedPost.setCreatedAt(LocalDateTime.now());

        when(blogPostRepository.save(any(BlogPost.class))).thenReturn(savedPost);

        BlogPost result = blogPostService.createPost(request);

        ArgumentCaptor<BlogPost> captor = ArgumentCaptor.forClass(BlogPost.class);
        verify(blogPostRepository).save(captor.capture());

        assertThat(captor.getValue().getTitle()).isEqualTo("Hello World");
        assertThat(captor.getValue().getContent()).isEqualTo("This is enough content for validation.");
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void findPaginated_returnsPageFromRepository() {
        BlogPost post = new BlogPost();
        post.setId(1L);
        post.setTitle("Sample");
        post.setContent("Sample content long enough for the blog.");
        post.setCreatedAt(LocalDateTime.now());

        Page<BlogPost> page = new PageImpl<>(List.of(post));
        when(blogPostRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<BlogPost> result = blogPostService.findPaginated(0, 5);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().getTitle()).isEqualTo("Sample");
    }

    @Test
    void findAllForApi_mapsEntitiesToResponses() {
        BlogPost post = new BlogPost();
        post.setId(10L);
        post.setTitle("API Post");
        post.setContent("Content for API mapping test.");
        post.setCreatedAt(LocalDateTime.of(2026, 6, 12, 10, 0));

        when(blogPostRepository.findAll(any(Sort.class))).thenReturn(List.of(post));

        List<BlogPostResponse> responses = blogPostService.findAllForApi();

        assertThat(responses).hasSize(1);
        assertThat(responses.getFirst().id()).isEqualTo(10L);
        assertThat(responses.getFirst().title()).isEqualTo("API Post");
    }

    @Test
    void deleteById_removesExistingPost() {
        when(blogPostRepository.existsById(5L)).thenReturn(true);

        blogPostService.deleteById(5L);

        verify(blogPostRepository).deleteById(5L);
    }

    @Test
    void deleteById_throwsWhenPostMissing() {
        when(blogPostRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> blogPostService.deleteById(99L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("99");
    }
}
