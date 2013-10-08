package com.example.isbnscaner;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.json.JSONTokener;

public class BookFetcher {
    private static int BUFFER_SIZE = 1024 * 64;
    private static Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
    
    private class BookInfo {
        String bookTitle;
        String bookDescription;
    };
    private BookInfo bookInfo  = new BookInfo();
    
    private void readBuffer(InputStream in) {
        String data = new String();
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
            int bytesRead = 0;
            int totalBytesRead = 0;
            while ((bytesRead = in.read(buffer)) >= 0) {
                String stringRead = new String(buffer, 0, bytesRead);
                data = data.concat(stringRead);
                totalBytesRead = totalBytesRead + bytesRead;
            }
        } catch(Exception e) {
            return;
        }
        
        try {
            LOGGER.info(data);
            JSONObject object = (JSONObject) new JSONTokener(data).nextValue();
            JSONObject obj1 = object.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo");
            bookInfo.bookTitle = obj1.getString("title");
            bookInfo.bookDescription = obj1.getString("description");
        } catch(Exception e) {
            LOGGER.warning(e.getMessage());
            LOGGER.warning(e.toString());
            e.printStackTrace();
        }
    }
    
    public void getBookInfoByISBN(String ISBNNumber) {
        String bookURL = "https://www.googleapis.com/books/v1/volumes?q=isbn:"
                        + ISBNNumber + "&key=AIzaSyDJr8YuyiSfW3CN9ORm2HrW_kek3qEwhf4";

        LOGGER.info("BOOK URL: " + bookURL);
        try {
            URL url = new URL(bookURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                readBuffer(in);
            } catch(Exception e) {
                LOGGER.warning(e.getMessage());
                LOGGER.warning(e.toString());
                e.printStackTrace();
            } finally {
                LOGGER.info("disconnect.");
                urlConnection.disconnect();
            }  
        } catch(Exception e) {
            LOGGER.warning(e.getMessage());
            LOGGER.warning(e.toString());
            e.printStackTrace();
        }
    }
    
    public String getBookTitle() {
        return bookInfo.bookTitle;
    }
    
    public String getBookDescription() {
        return bookInfo.bookDescription;
    }
}
