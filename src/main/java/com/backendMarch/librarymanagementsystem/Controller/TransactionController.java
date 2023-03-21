package com.backendMarch.librarymanagementsystem.Controller;

import com.backendMarch.librarymanagementsystem.DTO.IssueBookRequestDto;
import com.backendMarch.librarymanagementsystem.DTO.IssueBookResponseDto;
import com.backendMarch.librarymanagementsystem.Entity.Transaction;
import com.backendMarch.librarymanagementsystem.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @PostMapping("/issueBook")
    public ResponseEntity issueBook(@RequestBody IssueBookRequestDto issueBookRequestDto){

        IssueBookResponseDto issueBookResponseDto;
        try{
            issueBookResponseDto = transactionService.issueBook(issueBookRequestDto);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(issueBookResponseDto,HttpStatus.ACCEPTED);
    }

    @GetMapping("/alltransactions")
    public String getAllTransactionWithCardNo(@RequestParam("id") int cardId){
        return transactionService.getAllTransactionWithCardNo(cardId);
    }
}
