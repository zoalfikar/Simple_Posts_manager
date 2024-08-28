package simple_posts_manager.app.http.requests;

import jakarta.validation.constraints.*;
import lombok.*;
import java.sql.Timestamp;


@NoArgsConstructor
@AllArgsConstructor

@Data
public class LikeRequest {

    @NotNull(message="post id is required")
    private Long postId;

}