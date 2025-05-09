package com.example.fixtures

import com.example.entities.AuthorEntity
import com.example.entities.BookEntity
import com.example.repositories.BookRepository
import com.example.services.SaveBook

trait BookFixture {

    abstract BookRepository getBookRepository()

    BookEntity saveBook(String title, AuthorEntity author, int pages = 1) {
        BookEntity bookEntity = new BookEntity(title, pages, author)
        bookRepository.save(bookEntity)

        bookEntity
    }

    SaveBook createSaveBook(Long authorId, String title = 'book title', int pages = 100) {
        new SaveBook(title, pages, authorId)
    }
}
