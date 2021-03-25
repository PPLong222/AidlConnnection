// IMyAidlInterface.aidl
package com.example.mylastaidl;

// 引入自定类时一定要在这里导入
import com.example.bean.Book;
interface IMyAidlInterface {
    // 声明接口方法
    List<Book> getBooks();
    int getBookCount();
    Book addBookIn(in Book book);

}
