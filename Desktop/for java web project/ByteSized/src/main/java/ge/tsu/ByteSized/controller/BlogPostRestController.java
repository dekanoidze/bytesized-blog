package ge.tsu.ByteSized.controller;

import ge.tsu.ByteSized.dto.BlogPostResponse;
import ge.tsu.ByteSized.service.BlogPostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BlogPostRestController {

    private final BlogPostService blogPostService;

    public BlogPostRestController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping
    public List<BlogPostResponse> allPosts() {
        return blogPostService.findAllForApi();
    }
}
