package bookstore.controller;

import bookstore.exceptions.ResourceNotFoundException;
import bookstore.model.Genre;
import bookstore.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GenreController {

    @Autowired
    GenreRepository genreRepository;

    @GetMapping("/genre")
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @GetMapping("/genre/{GenreId}")
    public ResponseEntity<Genre> getGenre(@PathVariable(name = "GenreId") long GenreId) throws ResourceNotFoundException {
        Genre genre = genreRepository.findById(GenreId)
                .orElseThrow(() -> new ResourceNotFoundException("The genre not found with ID = " + GenreId));
        return ResponseEntity.ok().body(genre);
    }

    @PostMapping("/genre")
    public Genre createGenre(@Valid @RequestBody Genre genre) {
        return genreRepository.save(genre);
    }

    @PutMapping("/genre/{GenreId}")
    public ResponseEntity<Genre> updateGenre(@PathVariable(name = "GenreId") long GenreId, @Valid @RequestBody Genre genreDetails) throws ResourceNotFoundException {
        Genre genre = genreRepository.findById(GenreId)
                .orElseThrow(() -> new ResourceNotFoundException("The genre not found with ID = " + GenreId));
        genre.setName(genreDetails.getName());
        final Genre updatedGenre = genreRepository.save(genre);
        return ResponseEntity.ok().body(updatedGenre);
    }

    @DeleteMapping("/genre/{GenreId}")
    public Map<String, Boolean> deleteGenre(@PathVariable(name = "GenreId") long GenreId) throws ResourceNotFoundException {
        Genre genre = genreRepository.findById(GenreId)
                .orElseThrow(() -> new ResourceNotFoundException("The genre not found with ID = " + GenreId));
        genreRepository.delete(genre);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;

    }


}
