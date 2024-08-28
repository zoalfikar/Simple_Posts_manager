package simple_posts_manager.app.http.requests;

import jakarta.validation.constraints.*;
import lombok.*;
import java.sql.Timestamp;


@NoArgsConstructor
@AllArgsConstructor

@Data
public class UserRequest {

    @NotNull(message="first name is required")
    private String firstName;
  
  
    @NotNull(message="last name is required")
    private String lastName;
  
  
    @NotNull(message="email  is required")
    @Email(message="invalid email")
    @Size(max=100 ,message="max length is 100 character")
    private String email;
    
    @NotNull(message="password  is required")
    @Size(min=8 , message="min length is 8 character")
    private String password;
    
}