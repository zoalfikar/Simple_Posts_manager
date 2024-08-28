package simple_posts_manager.models.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import simple_posts_manager.models.entities.User;
import simple_posts_manager.models.entities.Post;


@NoArgsConstructor
@RequiredArgsConstructor

@Entity 
@Table(name = "likes")
@Data

public class Like implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "user_id" ,nullable=false)
    private Long userId;
  
    @NonNull
    @Column(name = "post_id" ,nullable=false)
    private Long postId;
  

    @Column(name = "created_at" ,columnDefinition = "TIMESTAMP   DEFAULT NOW() NOT")
    private Timestamp createdAt;
  
  
    @Column(name = "updated_at",columnDefinition = "TIMESTAMP  DEFAULT NOW() ON UPDATE now() NOT")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id" , referencedColumnName="id" ,insertable=false, updatable=false)
    private User user ;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="post_id" , referencedColumnName="id" ,insertable=false, updatable=false)
    private Post post ;
  
}
