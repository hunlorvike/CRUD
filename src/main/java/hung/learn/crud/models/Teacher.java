package hung.learn.crud.models;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
public class Teacher extends BaseModel {
    private String fullname;
    private String username;
    private String password;
}
