package com.codewithratchez.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer categoryId;
    @NotEmpty
    @NotBlank
    @Size(min = 4, message = "Min of 4 characters in category title")
    private String categoryTitle;
    @NotEmpty
    @NotBlank
    @Size(min = 10, message = "Min of 10 characters in category description")
    private String categoryDescription;
}
