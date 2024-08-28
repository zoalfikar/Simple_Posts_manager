package simple_posts_manager.controllers;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import simple_posts_manager.models.entities.Like;
import simple_posts_manager.models.entities.User;
import simple_posts_manager.app.http.requests.LikeRequest;
import simple_posts_manager.app.services.LikeCommentService;

import static simple_posts_manager.controllers.helper.IndexController.sendSuccessMessage;

import java.io.IOException;


@RestController	
@RequestMapping(path="/api/likes")
public class LikeController {


	private LikeCommentService likeCommentService;

	public LikeController(LikeCommentService likeCommentService) {
        this.likeCommentService = likeCommentService;
    }


	@PostMapping(path="") 
	public @ResponseBody Like add(@Valid @RequestBody LikeRequest like , @AuthenticationPrincipal User userDetails ){
		return likeCommentService.addLike(like , userDetails);
	}


	@DeleteMapping(path="/{id}") 
	public @ResponseBody Object delete(@PathVariable("id") long id ,@AuthenticationPrincipal User userDetails ) {
		likeCommentService.removeLike(id ,userDetails);
		return sendSuccessMessage("like has been removed successfully!");
	}

}