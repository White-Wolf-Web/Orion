package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class TopicDTO {
    private Long id;

    @NotBlank(message = "Le titre ne peut pas être vide")
    @Size(min = 5, max = 100, message = "Le titre doit contenir entre 5 et 100 caractères")
    private String title;

    @NotBlank(message = "Le contenu ne peut pas être vide")
    private String content;
    private Date createdAt;
    private Date updatedAt;


}
