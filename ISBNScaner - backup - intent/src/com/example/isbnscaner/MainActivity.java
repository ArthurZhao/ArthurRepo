package com.example.isbnscaner;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startScaner();
    }
    
    protected void startScaner() {
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public void onClick(View arg0) {
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        
        if(scanningResult != null) {
            String scanContent = scanningResult.getContents();
            
            // TODO: this line is dummy code
            scanContent = "9781554889617";
            
            BookFetcher bookFetcher = new BookFetcher();
            bookFetcher.getBookInfoByISBN(scanContent);
            
            TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
            TextView tvDescription = (TextView)findViewById(R.id.tvDescription);
            tvTitle.setText("Title: " + bookFetcher.getBookTitle());
            tvDescription.setText("Description: " + bookFetcher.getBookDescription());
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
