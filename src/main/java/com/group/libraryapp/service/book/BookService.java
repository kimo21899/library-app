package com.group.libraryapp.service.book;


import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.BookCreateRequest;
import com.group.libraryapp.dto.book.BookLoanRequest;
import com.group.libraryapp.dto.book.BookResponse;
import com.group.libraryapp.dto.book.BookReturnRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;

    private final UserRepository userRepository;

//    public BookService(BookRepository bookRepository, UserLoanHistoryRepository userLoanHistoryRepository, UserRepository userRepository) {
//        this.bookRepository = bookRepository;
//        this.userLoanHistoryRepository = userLoanHistoryRepository;
//        this.userRepository = userRepository;
//    }

    @Transactional
    public void saveBook(BookCreateRequest request){
       Book book = bookRepository.save(new Book(request.getName()));
       System.out.printf("id: %s, name: %s%n", book.getId(), book.getName());
    }

    @Transactional
    public void loanBook(BookLoanRequest request){
        // 1. 책 정보를 조회
        Book book = bookRepository.findByName(request.getBookName())
                .orElseThrow(IllegalArgumentException::new);
        // 2. 대출중인지 검사, 대출중이면 예외를 발생
        if(userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)){
           throw new IllegalArgumentException("이미 대출되어 있는 책입니다.");
        }
        // 3. 유저정보 조회
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);
        // 4. 유저객체 내부에서 대출기록을 저장.
        user.loanBook(request.getBookName());

        // 4. 유저정보+책정보 > 대출기록 저장(UserLoanHistory)
        // userLoanHistoryRepository.save(new UserLoanHistory(user, book.getName()));

    }

    @Transactional
    public void returnBook(BookReturnRequest request) {
        // 1. 유저정보 조회
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);
        System.out.println("Hello");
        // 2. 대출기록 삭제 (user 객체 내부에서 모두 처리)
        user.returnBook(request.getBookName());

//        // 2. 대출기록 조회
//        UserLoanHistory history = userLoanHistoryRepository.findByUserIdAndBookName(user.getId(), request.getBookName())
//                .orElseThrow(IllegalArgumentException::new);
//        // 3. 책반납
//        history.doReturn();
          // 4. 반납처리된 객체를 db에 적용 (트렌젝션 영속성으로 인해 자동 저장)
// userLoanHistoryRepository.save(history);
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getBook() {
        return  bookRepository.findAll().stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }


}
