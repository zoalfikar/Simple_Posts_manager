package simple_posts_manager.app.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.cache.annotation.*;
import org.springframework.cache.interceptor.SimpleKey;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import simple_posts_manager.app.http.requests.PostRequest;
import simple_posts_manager.app.http.requests.PostPicRequest;
import simple_posts_manager.models.entities.Post;
import simple_posts_manager.models.entities.User;
import simple_posts_manager.models.repositories.PostRepository;
import library_managment_system.app.exceptions.PostNotExistsException;
import library_managment_system.app.exceptions.NotAllowedProcessException;
import library_managment_system.app.exceptions.ImageSavingException;
import simple_posts_manager.app.services.PostCacheService;


@Service
public class PostService {

    private final  String uploadDirectory = "src/main/resources/static/images/posts";
    private final PostRepository postRepository;
	private PostCacheService postCacheService;

    public PostService(
        PostRepository postRepository,
        PostCacheService postCacheService
        
    ) {
        this.postRepository = postRepository;
        this.postCacheService = postCacheService;
    }
    
    public Post save(PostRequest input , User userDetails) {

        Long userId = userDetails.getId();
        Post post = new Post();
        post.setContent(input.getContent());
        post.setUserId(userId);
        post = postRepository.save(post);

		postCacheService.push(post);

        return post;
    }

    public Post save(PostPicRequest input , User userDetails)  {
        Long userId = userDetails.getId();
        String imagePath=null;
        try {
            imagePath = savePostImage(input.getImage());
        } catch (Exception e) {
            System.out.println("Something went wrong while saving image");
            throw new ImageSavingException();
        }
        Post post = new Post();
        post.setContent(input.getContent());
        post.setImagePath(imagePath);
        post.setUserId(userId);
        post = postRepository.save(post);

		postCacheService.push(post);

        return post;
    }

	@CachePut (cacheNames  = "post", key = "#id")
    public Post update(Long id , PostRequest input  , User userDetails) {
        Post post =  postRepository.findById(id).orElseThrow(() -> new PostNotExistsException(id));
        if (userDetails.getId() != post.getUserId())  throw new NotAllowedProcessException();
        post.setContent(input.getContent());
        return postRepository.save(post);
    }
    
	@CacheEvict(cacheNames  = "post",key = "#id", beforeInvocation = false)
    public void delete(Long id , User userDetails) {
        Post post =  postRepository.findById(id).orElseThrow(() -> new PostNotExistsException(id));
        if (userDetails.getId() != post.getUserId())  throw new NotAllowedProcessException();
        if(post.getImagePath() !=null)
        try {
             deletePostImage(post);

        } catch (Exception e) {
            System.out.println("Something went wrong while deleting image");
            throw new ImageSavingException();
        }
        postRepository.deleteUsingId(id);
    }


    private String savePostImage(MultipartFile imageFile) throws IOException {

            String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            Path uploadPath = Path.of(uploadDirectory);
            Path filePath = uploadPath.resolve(uniqueFileName);
    
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
    
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
    }

    
    public void updatePostImage(Long id , MultipartFile image , User userDetails) throws IOException {
        Post post =  postRepository.findById(id).orElseThrow(() -> new PostNotExistsException(id));
        if (userDetails.getId() != post.getUserId())  throw new NotAllowedProcessException();

        if (post.getImagePath() == null)  throw new ImageSavingException();

        Path imagePath = Paths.get(post.getImagePath());
        Files.copy(image.getInputStream(), imagePath , StandardCopyOption.REPLACE_EXISTING);
    }

    private void deletePostImage(Post post) throws IOException {
        Path imagePath = Paths.get(post.getImagePath());
        Files.deleteIfExists(imagePath);
    }
}
