package demojpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import demojpa.domain.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
	public Iterable<Book> findBooksByAuthor(@Param("author") String author);
	
	public Iterable<Book> findBooksByTitle(@Param("title") String title);
}
