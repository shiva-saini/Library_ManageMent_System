package com.backendMarch.librarymanagementsystem.Service;

import com.backendMarch.librarymanagementsystem.DTO.IssueBookRequestDto;
import com.backendMarch.librarymanagementsystem.DTO.IssueBookResponseDto;
import com.backendMarch.librarymanagementsystem.Entity.Book;
import com.backendMarch.librarymanagementsystem.Entity.LibraryCard;
import com.backendMarch.librarymanagementsystem.Entity.Transaction;
import com.backendMarch.librarymanagementsystem.Enum.CardStatus;
import com.backendMarch.librarymanagementsystem.Enum.TransactionStatus;
import com.backendMarch.librarymanagementsystem.Repository.BookRepository;
import com.backendMarch.librarymanagementsystem.Repository.CardRepository;
import com.backendMarch.librarymanagementsystem.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private JavaMailSender emailSender;
    public IssueBookResponseDto issueBook(IssueBookRequestDto issueBookRequestDto){
        LibraryCard card;
        Transaction transaction = new Transaction();
        transaction.setIssuedOperation(true);
        transaction.setTransactionNumber(String.valueOf(UUID.randomUUID()));
        try{
            card = cardRepository.findById(issueBookRequestDto.getCardId()).get();
        }catch (Exception e){
            transaction.setMessage("Invalid card id");
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new RuntimeException("Invalid card id");
        }

        Book book;
        try{
            book = bookRepository.findById(issueBookRequestDto.getBookId()).get();
        }catch (Exception e){
            transaction.setMessage("Invalid book id");
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new RuntimeException("Invalid book id");
        }

        transaction.setBook(book);
        transaction.setCard(card);

        if(card.getStatus() != CardStatus.ACTIVATED){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Card is not activated");
            transactionRepository.save(transaction);
            throw new RuntimeException("Card is not activated");
        }

        if(book.isIssued() == true){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Book is already issued");
            transactionRepository.save(transaction);
            throw new RuntimeException("Book is already issued");
        }


        // now i can issue the book

        book.setIssued(true);
        book.setCard(card);
        book.getTransactions().add(transaction);
        card.getTransactions().add(transaction);
        card.getBooks().add(book);
        transaction.setMessage("Transaction was successfully");
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        cardRepository.save(card);

        IssueBookResponseDto issueBookResponseDto  = new IssueBookResponseDto();
        issueBookResponseDto.setBookName(book.getTitle());
        issueBookResponseDto.setTransactionId(transaction.getId());
        issueBookResponseDto.setTransactionStatus(TransactionStatus.SUCCESS);

         String text = "Hi! " + card.getStudent().getName() +  " you have issued book successfully";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("shivasainisiyana2211@gmail.com");
        message.setTo(card.getStudent().getEmail());
        message.setSubject("Regarding Transaction");
        message.setText(text);
        emailSender.send(message);
        return issueBookResponseDto;

    }

    public  String getAllTransactionWithCardNo(int cardId){
        List<Transaction> trs =  transactionRepository.getAllSuccessfullTxnWithCardNo(cardId);
        String ans = "";
        for(Transaction t :trs){
            ans += t.getTransactionNumber() + "\n";
        }
        return ans;
    }
}
