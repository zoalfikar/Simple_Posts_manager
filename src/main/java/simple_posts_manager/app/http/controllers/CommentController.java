package simple_posts_manager.controllers;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import simple_posts_manager.models.entities.Comment;
import simple_posts_manager.models.entities.User;
import simple_posts_manager.app.http.requests.CommentRequest;
import simple_posts_manager.app.services.LikeCommentService;

import static simple_posts_manager.controllers.helper.IndexController.sendSuccessMessage;

import java.io.IOException;


@RestController	
@RequestMapping(path="/api/comments")
public class CommentController {


	private LikeCommentService likeCommentService;

	public CommentController(LikeCommentService likeCommentService) {
        this.likeCommentService = likeCommentService;
    }


	@PostMapping(path="") 
	public @ResponseBody Comment add(@Valid @RequestBody CommentRequest comment , @AuthenticationPrincipal User userDetails ){
		return likeCommentService.addComment(comment , userDetails);
	}

    @PutMapping(path="/{id}") 
	public @ResponseBody Comment update(@PathVariable("id") long id , @Valid @RequestBody CommentRequest comment , @AuthenticationPrincipal User userDetails ) {
		return likeCommentService.updateComment(id , comment ,userDetails);
	}


	@DeleteMapping(path="/{id}") 
	public @ResponseBody Object delete(@PathVariable("id") long id ,@AuthenticationPrincipal User userDetails ) {
		likeCommentService.removeComment(id ,userDetails);
		return sendSuccessMessage("comment has been removed successfully!");
	}

}