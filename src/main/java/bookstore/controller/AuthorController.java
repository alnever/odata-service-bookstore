package bookstore.controller;

import bookstore.exceptions.ResourceNotFoundException;
import bookstore.model.Author;
import bookstore.repository.AuthorRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    @GetMapping("/author")
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @GetMapping("/author/{AuthorId}")
    public ResponseEntity<Author> getAuthor(@PathVariable(name = "AuthorId") long AuthorId) throws ResourceNotFoundException {
        Author author = authorRepository.findById(AuthorId)
                .orElseThrow(() -> new ResourceNotFoundException("The author not found ID = " + AuthorId));
        return ResponseEntity.ok().body(author);
    }

    @PostMapping("/author")
    public Author createAuthor(@Valid @RequestBody Author author){
        return authorRepository.save(author);
    }

    @PutMapping("/author/{AuthorId}")
    public ResponseEntity<Author> updateAuthor(@PathVariable(name = "AuthorId") long AuthorId, @Valid @RequestBody Author newAuthor) throws ResourceNotFoundException {
        Author author = authorRepository.findById(AuthorId)
                .orElseThrow(() -> new ResourceNotFoundException("The author not found ID = " + AuthorId));
        author.setName(newAuthor.getName());
        final Author updatedAuthor = authorRepository.save(author);
        return ResponseEntity.ok().body(updatedAuthor);
    }

    @DeleteMapping("/author/{AuthorId}")
    public Map<String, Boolean> deleteAuthor(@PathVariable(name = "AuthorId") long AuthorId) throws ResourceNotFoundException {
        Author author = authorRepository.findById(AuthorId)
                .orElseThrow(() -> new ResourceNotFoundException("The author not found ID = " + AuthorId));
        authorRepository.delete(author);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
