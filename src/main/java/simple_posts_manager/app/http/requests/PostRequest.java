package simple_posts_manager.app.http.requests;

import jakarta.validation.constraints.*;
import lombok.*;
import java.sql.Timestamp;


@NoArgsConstructor
@AllArgsConstructor

@Data
public class PostRequest {

    @NotNull(message="content is required")
    private String content;

}