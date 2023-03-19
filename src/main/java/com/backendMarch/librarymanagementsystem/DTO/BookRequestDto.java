package com.backendMarch.librarymanagementsystem.DTO;

import com.backendMarch.librarymanagementsystem.Enum.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.PrivateKey;
import java.util.PrimitiveIterator;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookRequestDto {
           private String title;
           private Genre genre;
           private int price;
           private int authorId;
}
