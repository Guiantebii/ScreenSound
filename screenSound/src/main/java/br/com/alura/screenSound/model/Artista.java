package br.com.alura.screenSound.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artistas")
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @ManyToMany
    @JoinTable(
            name = "artista_musica",
            joinColumns = @JoinColumn(name="artista_id"),
            inverseJoinColumns = @JoinColumn(name = "musica_id")
    )
    private List<Musica> musicas = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
