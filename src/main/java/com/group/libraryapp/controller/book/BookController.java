package com.group.libraryapp.controller.book;

import com.group.libraryapp.dto.book.BookCreateRequest;
import com.group.libraryapp.dto.book.BookLoanRequest;
import com.group.libraryapp.dto.book.BookResponse;
import com.group.libraryapp.dto.book.BookReturnRequest;
import com.group.libraryapp.service.book.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public List<BookResponse> getBooks(){
        return bookService.getBook();
    }

    @PostMapping("/book")
    public void saveBook(@RequestBody BookCreateRequest request){
        bookService.saveBook(request);
    }

    @PostMapping("/book/loan")
    public void loanBook(@RequestBody BookLoanRequest request){
        bookService.loanBook(request);
    }

    @PutMapping("/book/return")
    public void returnBook(@RequestBody BookReturnRequest request){
        bookService.returnBook(request);
    }
}