package com.example.isbnscaner;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HandleClick hc = new HandleClick();
        findViewById(R.id.butQR).setOnClickListener(hc);
        findViewById(R.id.butProd).setOnClickListener(hc);
        findViewById(R.id.butOther).setOnClickListener(hc);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == 0) {
            TextView tvStatus = (TextView)findViewById(R.id.tvStatus);
            TextView tvResult = (TextView)findViewById(R.id.tvResult);
            if(resultCode == RESULT_OK) {
                tvStatus.setText(intent.getStringExtra("SCAN_RESULT_FORMAT"));
                tvResult.setText(intent.getStringExtra("SCAN_RESULT"));
            } else if(resultCode == RESULT_CANCELED) {
                tvStatus.setText(intent.getStringExtra("Press a button to start a scan"));
                tvResult.setText(intent.getStringExtra("Scan canceled"));
            }
        }
    }

    private class HandleClick implements OnClickListener {
        public void onClick(View arg0) {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
/*            switch(arg0.getId()) {
            case R.id.butQR:
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                break;
            case R.id.butProd:
                intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
                break;
            case R.id.butOther:
                intent.putExtra("SCAN_FORMATS", "CODE_39,CODE_93,CODE_128,DATA_MATRIX,ITF");
                break;
            }*/
            startActivityForResult(intent, 0);
        }
    }

}
