package com.group.libraryapp.domain.user.loanhistory;

import com.group.libraryapp.domain.book.Book;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoanHistoryRepository extends JpaRepository<UserLoanHistory, Long> {

    // select * from user_loan_history where book_name=? and is_return=?;
    boolean existsByBookNameAndIsReturn(String name, boolean isReturn);

    Optional<UserLoanHistory> findByUserIdAndBookName(long userId, String bookName);
}
