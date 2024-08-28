package simple_posts_manager.models.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import simple_posts_manager.models.entities.User;
import simple_posts_manager.models.entities.Like;
import simple_posts_manager.models.entities.Comment;


@NoArgsConstructor
@RequiredArgsConstructor

@Entity 
@Table(name = "posts")
@Data

public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "user_id" ,nullable=false)
    private Long userId;
  
    @Column(columnDefinition = "TEXT" ,nullable=false)
    private String content;

    @NonNull
    @Column(nullable=true , length = 500 , updatable=false)
    private String imagePath;
  

    @Column(name = "created_at" ,columnDefinition = "TIMESTAMP   DEFAULT NOW() NOT")
    private Timestamp createdAt;
  
  
    @Column(name = "updated_at",columnDefinition = "TIMESTAMP  DEFAULT NOW() ON UPDATE now() NOT")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id" , referencedColumnName="id" ,insertable=false, updatable=false)
    private User user ;
  
    @OneToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<Like> liks;

    @OneToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<Comment> comments;
}
