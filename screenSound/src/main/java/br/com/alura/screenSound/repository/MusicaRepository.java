package br.com.alura.screenSound.repository;

import br.com.alura.screenSound.model.Artista;
import br.com.alura.screenSound.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface MusicaRepository extends JpaRepository<Musica, Long> {

    List<Musica> findByArtistas(Artista artista);
}
