// IBookManager.aidl
package com.sven.develop.aidl;

// Declare any non-default types here with import statements
import com.sven.develop.aidl.Book;
import com.sven.develop.aidl.IEventListener;

interface IBookManager {

   int addBook(in Book book,in IEventListener listener);

}
