package com.example.aybudev.controller;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.example.aybudev.entity.Post;
import com.example.aybudev.entity.User;
import com.example.aybudev.repo.PostRepository;
import com.example.aybudev.repo.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Controller
public class HomeController {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User oAuth2User, Model model){
        if(oAuth2User==null || oAuth2User.getAttribute("login")==null || oAuth2User.getName()==null){
            return "index";
        }
        String userId = oAuth2User.getAttribute("login");

        User user = userRepository.findByUsername(userId);

        if(user==null){
            user = new User();
            user.setUsername(userId);
            user.setCareer("EMPTY");
            userRepository.save(user);
        }

        List<Post> postList = postRepository.findAll();
        Collections.reverse(postList);
        model.addAttribute("posts", postList);

        model.addAttribute("user",user);

        return "home";
    }

    @PostMapping("/careerSelect")
    public String careerSelect(@RequestBody MultiValueMap<String, String> form, @AuthenticationPrincipal OAuth2User oAuth2User) {
        if (oAuth2User == null || oAuth2User.getAttribute("login") == null) {
            return null;
        }
        User user = userRepository.findByUsername(oAuth2User.getAttribute("login"));
        String career = form.getFirst("career"); 
        user.setCareer(career);
        userRepository.save(user);
        return "redirect:/";
    }

    @PostMapping("/sharePost")
    public String sharePost(@RequestBody MultiValueMap<String, String> form, @AuthenticationPrincipal OAuth2User oAuth2User) {
        if (oAuth2User == null || oAuth2User.getAttribute("login") == null) {
            return null;
        }

        String postbody = form.getFirst("post-body");
        String aboutpost = form.getFirst("about-post");
        User user = userRepository.findByUsername(oAuth2User.getAttribute("login"));

        Post post = new Post();
        Timestamp ts=new Timestamp(System.currentTimeMillis());  
        Date date=new Date(ts.getTime());  
        post.setCreatedAt(date);
        post.setPostAbout(aboutpost);
        post.setPostBody(postbody);
        post.setUser(user);

        postRepository.save(post);
        
        return "redirect:/";
    }

    @GetMapping("/me")
    public String me(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        if (oAuth2User == null || oAuth2User.getAttribute("login") == null) {
            return null;
        }
        User user = userRepository.findByUsername(oAuth2User.getAttribute("login"));
        model.addAttribute("user",user);
        return "profile";
    }

    @GetMapping("/inbox")
    public String inbox(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        if (oAuth2User == null || oAuth2User.getAttribute("login") == null) {
            return null;
        }
        User user = userRepository.findByUsername(oAuth2User.getAttribute("login"));
        model.addAttribute("user",user);
        return "inbox";
    }

    @RequestMapping(value = "/deletePost/{id}")
    public String deletePost(@PathVariable int id) {
        postRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/support")
    public String support() {
        return "support";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}
