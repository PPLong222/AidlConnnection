package com.example.mylastaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.bean.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务端 Service 类  需要在Manifest中注册
 */
public class MyService extends Service {
    public MyService() {
    }
    private final static String tag = MyService.class.getSimpleName();
    //存储对象数组
    private List<Book> books = new ArrayList<>();
    //服务端 返回Binder的关键类
    private IMyAidlInterface.Stub aidlInterface = new IMyAidlInterface.Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            return books;
        }

        @Override
        public int getBookCount() throws RemoteException {
            return books.size();
        }

        @Override
        public Book addBookIn(Book book) throws RemoteException {
            Log.d(tag+"add-in",book.toString());
            books.add(book);
            return book;
        }


    };
    // 返回创建的Binder
    @Override
    public IBinder onBind(Intent intent) {

        return aidlInterface;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(tag,"created");
        //初始化 books
        Book book = new Book();
        book.setName("第一本书");
        book.setPrice(90);
        books.add(book);
    }
}
