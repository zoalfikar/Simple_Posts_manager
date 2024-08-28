package simple_posts_manager.app.requests;
import jakarta.validation.constraints.*;
import lombok.*;
import java.sql.Timestamp;


@NoArgsConstructor
@AllArgsConstructor

@Data
public class LoginUserRequest {

    @NotNull(message="email  is required")
    private String email;
    
    @NotNull(message="password  is required")
    private String password;
    
}