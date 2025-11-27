package br.com.alura.screenSound.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MusicaDTO(
        Long id,
        String title,
        ArtistaDTO artist
) {}
