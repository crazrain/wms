package demojpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demojpa.domain.Book;
import demojpa.repository.BookRepository;

@EnableAutoConfiguration
@RestController
@RequestMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {
	@Autowired
	protected BookRepository bookRepositoy;

	@RequestMapping
	public Iterable<Book> books() {
		return bookRepositoy.findAll();
	}

	@RequestMapping(value = "/{isbn}")
	public Book book(@PathVariable("isbn") String isbn) {
		return bookRepositoy.findOne(isbn);
	}

	@RequestMapping(value = "{/isbn}", method = RequestMethod.DELETE)
	public String deleteBook(@PathVariable("isbn") String isbn) {
		try {
			bookRepositoy.delete(isbn);
			return String.format("Book [%s] successfully deleted", isbn);
		} catch (Exception e) {
			return String.format("A problem occurred when deleting Book [%s]", isbn);
		}
	}

	@RequestMapping("/author/{author}")
	public Iterable<Book> booksByAuthor(@PathVariable("author") String author) {
		return bookRepositoy.findBooksByAuthor(author);
	}
	
	@RequestMapping("/title/{title}")
	public Iterable<Book> booksByTitle(@PathVariable("title") String title) {
		return bookRepositoy.findBooksByTitle(title);
	}
	
	@RequestMapping("/add/{isbn}/{author}/{title}/{description}")
	public Book addBook(@PathVariable("isbn") String isbn, @PathVariable("author") String author, @PathVariable("title") String title, @PathVariable("description") String description) {
		Book book = new Book();
		book.setIsbn(isbn);
		book.setTitle(title);
		book.setAuthor(author);
		book.setDescription(description);
		return bookRepositoy.save(book);
	}

	@RequestMapping("/set/{isbn}/{author}/{title}/{description}")
	public String setBook(@PathVariable("isbn") String isbn, @PathVariable("author") String author, @PathVariable("title") String title, @PathVariable("description") String description) {
		try {
			Book book = bookRepositoy.findOne(isbn);
			book.setTitle(title);
			book.setAuthor(author);
			book.setDescription(description);
			bookRepositoy.save(book);
			return String.format("Book [%s] successfully updated", isbn);
		} catch (Exception e) {
			return String.format("A problem occurred when updating Book [%s]", isbn);
		}
	}
}
