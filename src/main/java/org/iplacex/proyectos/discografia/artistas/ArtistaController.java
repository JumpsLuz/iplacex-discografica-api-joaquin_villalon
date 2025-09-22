package org.iplacex.proyectos.discografia.artistas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {
    
    @Autowired
    private IArtistaRepository artistaRepository;
    
    @PostMapping(value = "/artista", 
                consumes = MediaType.APPLICATION_JSON_VALUE, 
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        Artista savedArtista = artistaRepository.save(artista);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArtista);
    }
    
    @GetMapping(value = "/artistas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artista>> HandleGetArtistasRequest() {
        List<Artista> artistas = artistaRepository.findAll();
        return ResponseEntity.ok(artistas);
    }
    
    @GetMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetArtistaRequest(@PathVariable String id) {
        Optional<Artista> artista = artistaRepository.findById(id);
        if (artista.isPresent()) {
            return ResponseEntity.ok(artista.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artista no encontrado");
    }
    
    @PutMapping(value = "/artista/{id}", 
               consumes = MediaType.APPLICATION_JSON_VALUE, 
               produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleUpdateArtistaRequest(@PathVariable String id, @RequestBody Artista artista) {
        if (!artistaRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artista no encontrado");
        }
        artista._id = id;
        Artista updatedArtista = artistaRepository.save(artista);
        return ResponseEntity.ok(updatedArtista);
    }
    
    @DeleteMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleDeleteArtistaRequest(@PathVariable String id) {
        if (!artistaRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artista no encontrado");
        }
        artistaRepository.deleteById(id);
        return ResponseEntity.ok("Artista eliminado");
    }
}
