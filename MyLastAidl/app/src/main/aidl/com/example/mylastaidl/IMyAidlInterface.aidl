// IMyAidlInterface.aidl
package com.example.mylastaidl;

// Declare any non-default types here with import statements
import com.example.bean.Book;

    interface IMyAidlInterface {
  List<Book> getBooks();

     int getBookCount();
     Book addBookIn(in Book book);

}
