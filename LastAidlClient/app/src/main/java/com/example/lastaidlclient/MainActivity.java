package com.example.lastaidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bean.Book;
import com.example.mylastaidl.IMyAidlInterface;

import java.util.List;

/**
 * 请注意build.gradle中的sourceSets设置,否则无法读取aidl包下的java类
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final String tag = "MainActivity";
    private IMyAidlInterface bookInterface = null;
    private boolean isConnect = false;
    private List<Book> books;
    private Button buttonAllBooks;
    private Button buttonBind;
    private Button buttonBookCounts;
    private Button buttonAddBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();

    }


    private void initUi() {
        buttonAddBook = findViewById(R.id.button_bookaddIn);
        buttonBookCounts = findViewById(R.id.button_bookCount);
        buttonAllBooks = findViewById(R.id.button_allBook);
        buttonBind = findViewById(R.id.button_bind);

        buttonAllBooks.setOnClickListener(this);
        buttonBookCounts.setOnClickListener(this);
        buttonAddBook.setOnClickListener(this);
        buttonBind.setOnClickListener(this);
    }

    private boolean connectService() {
        Intent intent = new Intent();
        intent.setAction("com.example.mylastaidl.MyService");
        //arg 0: 服务器app包名  arg 1: 服务器对应接口的action
        intent.setComponent(new ComponentName("com.example.mylastaidl","com.example.mylastaidl.MyService"));
        // 进行绑定
        return bindService(intent,connection,Context.BIND_AUTO_CREATE);
    }

    //创建ServiceConnection在建立连接后进行接口绑定
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(tag,"client service connected");

            bookInterface = IMyAidlInterface.Stub.asInterface(service);
            if(bookInterface != null){
                try {
                    books = bookInterface.getBooks();
                    Log.d(tag,"books "+books.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 取消绑定时的回调
            Log.d(tag,"service disconnected");
        }
    };

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_bookaddIn:
                if(isConnect) {
                    Book book = new Book();
                    book.setPrice(5);
                    book.setName("second");
                    try {
                        Log.d(tag, "add a book: " + bookInterface.addBookIn(book).toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(this, "please connect first", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.button_bookCount:
                if(isConnect){
                try {
                    Log.d(tag,"books count: "+bookInterface.getBookCount());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                }else{
                    Toast.makeText(this, "please connect first", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_allBook:
                if(isConnect) {
                    try {
                        Log.d(tag, "books list: " + bookInterface.getBooks().toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(this, "please connect first", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_bind:
                if(!isConnect){
                    isConnect = connectService();
                    if(isConnect){
                        Toast.makeText(this, "success connect", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "connect failed", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "already connect ", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
