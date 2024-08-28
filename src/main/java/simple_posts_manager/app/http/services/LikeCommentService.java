package simple_posts_manager.app.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import simple_posts_manager.app.http.requests.LikeRequest;
import simple_posts_manager.app.http.requests.CommentRequest;
import simple_posts_manager.models.entities.Post;
import simple_posts_manager.models.entities.User;
import simple_posts_manager.models.entities.Like;
import simple_posts_manager.models.entities.Comment;
import simple_posts_manager.models.repositories.CommentRepository;
import simple_posts_manager.models.repositories.PostRepository;
import simple_posts_manager.models.repositories.LikeRepository;
import library_managment_system.app.exceptions.PostNotExistsException;
import library_managment_system.app.exceptions.LikeNotFoundException;
import library_managment_system.app.exceptions.CommentNotfoundException;
import library_managment_system.app.exceptions.NotAllowedProcessException;
import library_managment_system.app.exceptions.PostAlreadyLikedException;
import simple_posts_manager.app.services.PostCacheService;


@Service
public class LikeCommentService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
	private PostCacheService postCacheService;

    public LikeCommentService(
        PostRepository postRepository,
        LikeRepository likeRepository,
        CommentRepository commentRepository,
        PostCacheService postCacheService
    ) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.postCacheService = postCacheService;
    }
    
    public Like addLike(LikeRequest input , User userDetails) {

        Long userId = userDetails.getId();
        Post post =  postRepository.findById(input.getPostId()).orElseThrow(() -> new PostNotExistsException(input.getPostId()));
        if(likeRepository.existsByUserIdAndPostId(userId ,post.getId())) 
        throw new PostAlreadyLikedException(post.getId());
        Like like = new Like();
        like.setPostId(post.getId());
        like.setUserId(userId);
        Like newlike =likeRepository.save(like);

        Post updatedPost =  postRepository.findById(newlike.getPostId()).orElseThrow(() -> new PostNotExistsException(newlike.getPostId()));
		postCacheService.push(updatedPost);
        return newlike ;
    }

    public void removeLike(Long id , User userDetails) {
        Like like =  likeRepository.findById(id).orElseThrow(() -> new LikeNotFoundException(id));
        if (userDetails.getId() != like.getUserId())  throw new NotAllowedProcessException();
        likeRepository.deleteUsingId(id);

        Post updatedPost =  postRepository.findById(like.getPostId()).orElseThrow(() -> new PostNotExistsException(like.getPostId()));
		postCacheService.push(updatedPost);
    }

    public Comment addComment(CommentRequest input , User userDetails) {
        Long userId = userDetails.getId();
        Post post =  postRepository.findById(input.getPostId()).orElseThrow(() -> new PostNotExistsException(input.getPostId()));
        Comment comment = new Comment();
        comment.setPostId(post.getId());
        comment.setUserId(userId);
        comment.setContent(input.getContent());
        Comment newComment =commentRepository.save(comment);

        Post updatedPost =  postRepository.findById(newComment.getPostId()).orElseThrow(() -> new PostNotExistsException(newComment.getPostId()));
		postCacheService.push(updatedPost);
        return newComment ;
    }

    public Comment updateComment( Long id,CommentRequest input , User userDetails) {
        Long userId = userDetails.getId();
        Post post =  postRepository.findById(input.getPostId()).orElseThrow(() -> new PostNotExistsException(input.getPostId()));
        Comment comment =  commentRepository.findById(id).orElseThrow(() -> new CommentNotfoundException(input.getPostId()));
        if (userDetails.getId() != comment.getUserId())  throw new NotAllowedProcessException();
        comment.setContent(input.getContent());
        
        Comment  newComment =commentRepository.save(comment);
        
        Post updatedPost =  postRepository.findById(newComment.getPostId()).orElseThrow(() -> new PostNotExistsException(newComment.getPostId()));
		postCacheService.push(updatedPost);
        return newComment ;
    }

    public void removeComment(Long id , User userDetails) {
        Long userId = userDetails.getId();
        Comment comment =  commentRepository.findById(id).orElseThrow(() -> new CommentNotfoundException(id));
        if (userDetails.getId() != comment.getUserId())  throw new NotAllowedProcessException();
        commentRepository.deleteUsingId(id);

        Post updatedPost =  postRepository.findById(comment.getPostId()).orElseThrow(() -> new PostNotExistsException(comment.getPostId()));
		postCacheService.push(updatedPost);
    }
  }
