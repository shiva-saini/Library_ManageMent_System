package com.backendMarch.librarymanagementsystem.Service;

import com.backendMarch.librarymanagementsystem.DTO.BookRequestDto;
import com.backendMarch.librarymanagementsystem.DTO.BookResponseDto;
import com.backendMarch.librarymanagementsystem.Entity.Author;
import com.backendMarch.librarymanagementsystem.Entity.Book;
import com.backendMarch.librarymanagementsystem.Repository.AuthorRepository;
import com.backendMarch.librarymanagementsystem.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    AuthorRepository authorRepository;
    public BookResponseDto addBook(BookRequestDto bookRequestDto) throws Exception{
        Author author;
        author = authorRepository.findById(bookRequestDto.getAuthorId()).get();

        List<Book> booksWritten = author.getBooks();
        Book book = new Book();
        book.setAuthor(author);
        book.setPrice(bookRequestDto.getPrice());
        book.setIssued(false);
        book.setTitle(bookRequestDto.getTitle());
        book.setGenre(bookRequestDto.getGenre());

        booksWritten.add(book);

        authorRepository.save(author); // this will save both book and author because author is parent

        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setPrice(book.getPrice());
        bookResponseDto.setTitle(book.getTitle());
        return bookResponseDto;
    }
}
