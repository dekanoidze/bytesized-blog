package ge.tsu.ByteSized.service;

import ge.tsu.ByteSized.dto.BlogPostResponse;
import ge.tsu.ByteSized.dto.CommentResponse;
import ge.tsu.ByteSized.dto.CreateBlogPostRequest;
import ge.tsu.ByteSized.model.BlogPost;
import ge.tsu.ByteSized.repository.BlogPostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BlogPostService {

    private static final Logger log = LoggerFactory.getLogger(BlogPostService.class);
    private static final Path UPLOAD_DIR = Path.of("uploads");

    private final BlogPostRepository blogPostRepository;

    public BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Transactional(readOnly = true)
    public Page<BlogPost> findPaginated(int page, int size) {
        return blogPostRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
    }

    @Transactional
    public BlogPost createPost(CreateBlogPostRequest request) {
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(request.getTitle().trim());
        blogPost.setContent(request.getContent().trim());
        blogPost.setCreatedAt(LocalDateTime.now());
        blogPost.setCoverImagePath(storeImage(request.getCoverImage()));

        BlogPost saved = blogPostRepository.save(blogPost);
        log.info("Saved blog post with id={}", saved.getId());
        return saved;
    }

    @Transactional(readOnly = true)
    public List<BlogPostResponse> findAllForApi() {
        return blogPostRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(post -> new BlogPostResponse(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getCoverImagePath(),
                        post.getCreatedAt(),
                        post.getComments().stream()
                                .map(comment -> new CommentResponse(
                                        comment.getId(),
                                        comment.getAuthorName(),
                                        comment.getMessage(),
                                        comment.getCreatedAt()))
                                .toList()))
                .toList();
    }

    private String storeImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            Files.createDirectories(UPLOAD_DIR);
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String fileName = UUID.randomUUID() + (extension == null ? "" : "." + extension);
            Path destination = UPLOAD_DIR.resolve(fileName);

            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            log.debug("Stored cover image at {}", destination.toAbsolutePath());
            return "/uploads/" + fileName;
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to store uploaded file.", ex);
        }
    }
}
