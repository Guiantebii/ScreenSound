package br.com.alura.screenSound.Principal;

import br.com.alura.screenSound.model.Artista;
import br.com.alura.screenSound.model.DadosDeezerDTO;
import br.com.alura.screenSound.model.Musica;
import br.com.alura.screenSound.repository.ArtistaRepository;
import br.com.alura.screenSound.repository.MusicaRepository;
import br.com.alura.screenSound.service.ConsumoApi;
import br.com.alura.screenSound.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();
    private final String ENDERECO = "https://api.deezer.com/search?q=";
    private MusicaRepository musicaRepository;
    private ArtistaRepository artistaRepository;


    public Principal(MusicaRepository musicaRepository, ArtistaRepository artistaRepository) {
        this.musicaRepository = musicaRepository;
        this.artistaRepository = artistaRepository;
    }

    public void exibeMenu() {
        int opcao = -1;
        while (opcao != 0) {
            var menu = """
                1 - Buscar Artistas
                2 - Listar Musicas
                3 - Listar Musica por artista
                
                0 - Sair
                """;
            System.out.println(menu);

            String linha = leitura.nextLine(); // lê toda a linha
            try {
                opcao = Integer.parseInt(linha); // converte para inteiro
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Digite um número.");
                continue;
            }

            switch (opcao) {
                case 1:
                    buscarMusicas();
                    break;
                case 2:
                    listarTodasMusicas();
                    break;
                    case 3:
                        listarMusicaPorArtista();
                        break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void listarMusicaPorArtista() {
        System.out.println("Digite o nome do artista: ");
        String nomeArtista = leitura.nextLine();

        Optional<Artista> artistaOptional = artistaRepository.findByNomeContainingIgnoreCase(nomeArtista);

        if (artistaOptional.isEmpty()) {
            System.out.println("Artista não encontrado no banco.");
            return;
        }

        Artista artista = artistaOptional.get();

        List<Musica> musicasArtista = musicaRepository.findByArtistas(artista);

        if (musicasArtista.isEmpty()) {
            System.out.println("Nenhuma música encontrada para esse artista.");
            return;
        }

        musicasArtista.forEach(musica -> {
            String artistas = musica.getArtistas().stream()
                    .map(Artista::getNome)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("Sem artista");

            System.out.println("Música: " + musica.getTitulo() + " | Artista(s): " + artistas);
        });
    }

    private void listarTodasMusicas() {
        List<Musica> musicas = musicaRepository.findAll();
        musicas.forEach(musica -> {
            String artistas = musica.getArtistas().stream()
                    .map(Artista::getNome)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("Sem artista");
            System.out.println("Música: " + musica.getTitulo() + " | Artista(s): " + artistas);
        });

    }

    public void buscarMusicas() {
        System.out.println("Digite o nome do artista: ");
        String nome = leitura.nextLine();
        String url = ENDERECO + nome.replace(" ", "+");
        //Chamando a API
        String json = consumoApi.obterDados(url);
        //Convertendo os dados para DTO
        DadosDeezerDTO  dados = converteDados.obterDados(json, DadosDeezerDTO.class);

        if(dados.data().isEmpty()){
            System.out.println("Nenhuma música encontrada para esse artista");
            return;
        }

        Artista artista = new Artista();
        artista.setNome(dados.data().get(0).artist().name());
        artista.setMusicas(new ArrayList<>());

        dados.data().stream()
                .map(musicaDTO -> {
                    Musica musica = new Musica();
                    musica.setTitulo(musicaDTO.title());
                    musica.setArtistas(List.of(artista));
                    artista.getMusicas().add(musica);
                    return musica;

                })
                .forEach(musicaRepository::save);

        artistaRepository.save(artista);

        System.out.println("Salvou o artista: " + artista.getNome());
        artista.getMusicas().forEach(m -> System.out.println("Salvou músicas: "+ m.getTitulo()));

    }
}
