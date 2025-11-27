package br.com.alura.screenSound;

import br.com.alura.screenSound.Principal.Principal;
import br.com.alura.screenSound.repository.ArtistaRepository;
import br.com.alura.screenSound.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenSoundApplication implements CommandLineRunner {

	@Autowired
	private MusicaRepository musicaRepository;
	@Autowired
	private ArtistaRepository artistaRepository;


	public static void main(String[] args) {
		SpringApplication.run(ScreenSoundApplication.class, args);

	}
	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(musicaRepository,artistaRepository);

		principal.exibeMenu();
	}
}
