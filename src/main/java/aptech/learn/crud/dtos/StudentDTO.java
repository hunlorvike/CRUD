package aptech.learn.crud.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StudentDTO {
    @NotBlank(message = "Fullname is mandatory")
    @Size(min = 2, max = 100, message = "Fullname must be between 2 and 100 characters")
    private String fullname;

    @Size(max = 15, message = "Phone number must be up to 15 characters")
    private String phone;

    @Size(max = 255, message = "Address must be up to 255 characters")
    private String address;

    @NotNull(message = "Point is mandatory")
    private Double point;
}
