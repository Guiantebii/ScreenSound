package br.com.alura.screenSound.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosDeezerDTO(
        List<MusicaDTO> data
) {}
