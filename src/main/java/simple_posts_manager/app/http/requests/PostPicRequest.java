package simple_posts_manager.app.http.requests;

import jakarta.validation.constraints.*;
import lombok.*;
import java.sql.Timestamp;
import org.springframework.web.multipart.MultipartFile;


@NoArgsConstructor
@AllArgsConstructor

@Data
public class PostPicRequest {

    @NotNull(message="content is required")
    private String content;

    @NotNull(message="image is required")
    private MultipartFile image;


}