package bookstore.controller;

import bookstore.exceptions.ResourceNotFoundException;
import bookstore.model.Book;
import bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/book")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/book/{BookId}")
    public ResponseEntity<Book> getBook(@PathVariable(name = "BookId") long BookId) throws ResourceNotFoundException {
        Book book = bookRepository.findById(BookId)
                .orElseThrow(() -> new ResourceNotFoundException("The book not found ID = " + BookId));
        return ResponseEntity.ok().body(book);
    }

    @PostMapping("/book")
    public Book createBook(@Valid @RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("/book/{BookId}")
    public ResponseEntity<Book> updateBook(@PathVariable(name = "BookId") long BookId, @Valid @RequestBody Book newBook) throws ResourceNotFoundException {
        Book book = bookRepository.findById(BookId)
                .orElseThrow(() -> new ResourceNotFoundException("The book not found ID = " + BookId));
        book.setTitle(newBook.getTitle());
        book.setGenre(newBook.getGenre());
        book.setAuthors(newBook.getAuthors());
        Book updatedBook = bookRepository.save(book);
        return ResponseEntity.ok().body(updatedBook);
    }

    @DeleteMapping("/book/{BookId}")
    public Map<String, Boolean> deleteBook(@PathVariable(name = "BookId") long BookId) throws ResourceNotFoundException {
        Book book = bookRepository.findById(BookId)
                .orElseThrow(() -> new ResourceNotFoundException("The book not found ID = " + BookId));
        bookRepository.delete(book);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
