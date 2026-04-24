package ge.tsu.ByteSized.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class CreateBlogPostRequest {

    @NotBlank(message = "Title is required.")
    @Size(min = 5, max = 150, message = "Title must be between 5 and 150 characters.")
    private String title;

    @NotBlank(message = "Content is required.")
    @Size(min = 20, max = 5000, message = "Content must be between 20 and 5000 characters.")
    private String content;

    private MultipartFile coverImage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MultipartFile getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(MultipartFile coverImage) {
        this.coverImage = coverImage;
    }
}
