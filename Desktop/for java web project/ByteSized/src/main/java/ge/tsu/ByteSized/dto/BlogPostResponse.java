package ge.tsu.ByteSized.dto;

import java.time.LocalDateTime;
import java.util.List;

public record BlogPostResponse(
        Long id,
        String title,
        String content,
        String coverImageUrl,
        LocalDateTime createdAt,
        List<CommentResponse> comments
) {
}
