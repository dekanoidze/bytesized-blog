package ge.tsu.ByteSized.controller;

import ge.tsu.ByteSized.config.BlogProperties;
import ge.tsu.ByteSized.dto.CreateBlogPostRequest;
import ge.tsu.ByteSized.model.BlogPost;
import ge.tsu.ByteSized.service.BlogPostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class WebController {

    private final BlogPostService blogPostService;
    private final BlogProperties blogProperties;

    public WebController(BlogPostService blogPostService, BlogProperties blogProperties) {
        this.blogPostService = blogPostService;
        this.blogProperties = blogProperties;
    }

    @GetMapping({"/", ""})
    public String home(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        int currentPage = Math.max(page, 0);
        Page<BlogPost> posts = blogPostService.findPaginated(currentPage, 5);

        model.addAttribute("platformName", blogProperties.getPlatformName());
        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", currentPage);
        return "home";
    }

    @GetMapping("/posts/new")
    public String newPostForm(Model model) {
        if (!model.containsAttribute("createBlogPostRequest")) {
            model.addAttribute("createBlogPostRequest", new CreateBlogPostRequest());
        }
        model.addAttribute("platformName", blogProperties.getPlatformName());
        model.addAttribute("maxImageUploadSize", blogProperties.getMaxImageUploadSize());
        return "create-post";
    }

    @PostMapping("/posts")
    public String createPost(
            @Valid @ModelAttribute("createBlogPostRequest") CreateBlogPostRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("platformName", blogProperties.getPlatformName());
            model.addAttribute("maxImageUploadSize", blogProperties.getMaxImageUploadSize());
            return "create-post";
        }

        blogPostService.createPost(request);
        redirectAttributes.addFlashAttribute("successMessage", "Blog post published successfully.");
        return "redirect:/";
    }
}
