package hung.learn.crud.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
public abstract class BaseModel {
    protected int id;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
}
