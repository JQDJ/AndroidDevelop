// IEventListener.aidl
package com.sven.develop.aidl;

// Declare any non-default types here with import statements
import com.sven.develop.aidl.Book;

interface IEventListener {

  void onBookAdded(in Book book);

}
