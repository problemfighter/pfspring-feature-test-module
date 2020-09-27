package com.problemfighter.pfspring.webtestmodule.example.controller;


import com.problemfighter.pfspring.webtestmodule.example.model.entity.Comment;
import com.problemfighter.pfspring.webtestmodule.example.model.entity.Post;
import com.problemfighter.pfspring.webtestmodule.example.repository.CommentRepository;
import com.problemfighter.pfspring.webtestmodule.example.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/blog")
public class ApiV1BlogController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;


    @GetMapping("/add-post")
    public String addPost(){
        postRepository.save(new Post().setName("Post 1").setDetails("Post Details 1"));
        return "Added";
    }

    @GetMapping("/add-comment")
    public String addComment(){
        Post post = postRepository.findById(1l).get();
        Comment comment = new Comment().setText("Comment 1");
        comment.post = post;
        commentRepository.save(comment);
        return "Added";
    }


    @GetMapping("/post")
    public String post(){
        postRepository.findAll();
        return "list";
    }

    @GetMapping("/comment")
    public String comment(){
        commentRepository.findAll();
        return "list";
    }

    @GetMapping("/add-comment-post")
    public String addCommentPost(){
        Post post = new Post().setName("Test Post").setDetails(" Test Post Details");


        Comment comment = new Comment().setText("Test Post Comment");
        comment.post = post;

//        commentRepository.save(comment);

        Comment comment2 = new Comment().setText("Bulk Comment 2");
        comment2.post = post;

        post.comments.add(comment);
        post.comments.add(comment2);


        postRepository.save(post);

        return "list";
    }
}
