package ge.tsu.ByteSized.service;

import ge.tsu.ByteSized.model.Comment;
import ge.tsu.ByteSized.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    void save_setsCreatedAtWhenMissing() {
        Comment comment = new Comment();
        comment.setAuthorName("Reader");
        comment.setMessage("Great post!");

        when(commentRepository.save(comment)).thenAnswer(invocation -> invocation.getArgument(0));

        Comment saved = commentService.save(comment);

        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(captor.capture());
        assertThat(captor.getValue().getCreatedAt()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
    }

    @Test
    void save_preservesExistingCreatedAt() {
        LocalDateTime createdAt = LocalDateTime.of(2026, 6, 12, 9, 30);
        Comment comment = new Comment();
        comment.setAuthorName("Reader");
        comment.setMessage("Nice article.");
        comment.setCreatedAt(createdAt);

        when(commentRepository.save(comment)).thenReturn(comment);

        Comment saved = commentService.save(comment);

        assertThat(saved.getCreatedAt()).isEqualTo(createdAt);
    }
}
