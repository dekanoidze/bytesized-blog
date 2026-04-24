package ge.tsu.ByteSized.dto;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String authorName,
        String message,
        LocalDateTime createdAt
) {
}
