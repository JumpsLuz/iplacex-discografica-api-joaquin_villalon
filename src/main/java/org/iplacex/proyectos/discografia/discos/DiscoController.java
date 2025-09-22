package org.iplacex.proyectos.discografia.discos;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepository;
    
    @Autowired
    private IArtistaRepository artistaRepository;

    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disco) {
        if (!artistaRepository.existsById(disco.idArtista)) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }
        Disco temp = discoRepository.save(disco);
        return ResponseEntity.status(HttpStatus.CREATED).body(temp);
    }

    @GetMapping(value = "/discos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        return new ResponseEntity<>(discoRepository.findAll(), null, HttpStatus.OK);
    }

    @GetMapping(value = "/disco/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable("id") String id) {
        Optional<Disco> disco = discoRepository.findById(id);
        if (!disco.isPresent()) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(disco.get(), null, HttpStatus.OK);
    }

    @GetMapping(value = "/artista/{id}/discos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable("id") String id) {
        List<Disco> discos = discoRepository.findDiscosByIdArtista(id);
        return new ResponseEntity<>(discos, null, HttpStatus.OK);
    }
}