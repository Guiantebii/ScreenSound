package br.com.alura.screenSound.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ArtistaDTO(
        Long id,
        String name
) {}
