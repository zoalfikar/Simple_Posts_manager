package simple_posts_manager.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import simple_posts_manager.models.entities.Post;
import simple_posts_manager.models.entities.User;
import simple_posts_manager.app.http.requests.PostRequest;
import simple_posts_manager.app.services.PostService;
import simple_posts_manager.app.services.PostCacheService;
import simple_posts_manager.app.http.requests.PostPicRequest;
import simple_posts_manager.models.repositories.PostRepository;
import library_managment_system.app.exceptions.PostNotExistsException;

import static simple_posts_manager.controllers.helper.IndexController.sendSuccessMessage;
import static simple_posts_manager.controllers.helper.IndexController.sendFailMessage;

import java.io.IOException;
import java.util.Set;


@RestController	
@RequestMapping(path="/api/posts")
public class PostController {

	@Autowired
	private PostRepository postRepository;

	private PostService postService;

	private PostCacheService postCacheService;


	public PostController(PostService postService , PostCacheService postCacheService) {
        this.postService = postService;
        this.postCacheService = postCacheService;
    }

	@GetMapping(path="") 
	public @ResponseBody Iterable<Post> getUserPosts ( @AuthenticationPrincipal User authUser ) {
		return authUser.getPosts();
	}

	@GetMapping(path="/{id}") 
	public @ResponseBody Post getPost (@PathVariable("id") long id) {
		Post post =  postRepository.findById(id).orElseThrow(() -> new PostNotExistsException(id));
		return post;
	}

	@GetMapping(path="/cache/{id}") 
	public @ResponseBody Object getPostRromCache (@PathVariable("id") long id) {
		return postCacheService.retrive(id);
	}


	@GetMapping(path="/all/posts") 
	public @ResponseBody Iterable<Post> getAllPosts () {
		return postRepository.findAll();
	}

	@PostMapping(path="") 
	public @ResponseBody Post add(@Valid @RequestBody PostRequest post , @AuthenticationPrincipal User userDetails ){
		return  postService.save(post , userDetails);
	}

	@PostMapping(path="add/with-image" ,consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }) 
	public @ResponseBody Post addWithPic(@Valid @ModelAttribute  PostPicRequest post  , @AuthenticationPrincipal User userDetails ){
		return postService.save(post , userDetails);
	}

	@PutMapping(path="/{id}") 
	public @ResponseBody Post update(@PathVariable("id") long id , @Valid @RequestBody PostRequest post , @AuthenticationPrincipal User userDetails ) {
		return postService.update(id , post ,userDetails);
	}

	@PutMapping(path="/{id}/image" ,consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }) 
	public @ResponseBody Object updateImage(@PathVariable("id") long id ,@RequestParam MultipartFile image ,@AuthenticationPrincipal User userDetails ) {
		
		try {

			postService.updatePostImage(id , image ,userDetails);

		} catch (Exception e) {

            System.out.println("Something went wrong while updating image");
			return sendFailMessage("some thing went wrong!");

		}
		return sendSuccessMessage("post image updated successfully");

	}

	@DeleteMapping(path="/{id}") 
	public @ResponseBody Object delete(@PathVariable("id") long id ,@AuthenticationPrincipal User userDetails ) {
		postService.delete(id ,userDetails);
		return sendSuccessMessage("Post has been deleted successfully!");
	}

}