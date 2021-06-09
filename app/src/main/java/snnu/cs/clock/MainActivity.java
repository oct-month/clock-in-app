package snnu.cs.clock;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import snnu.cs.clock.utils.LocalStorage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final int ZXING_CAMERA_PERMISSION = 1;
    private Class<?> mClss;

    private ImageView scanImg;
    private LocalStorage storage;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        scanImg = findViewById(R.id.img_scan);
        scanImg.setOnClickListener(this);
        storage = new LocalStorage(this);
        if (storage.get(Config.SCHOOL_KEY).trim().length() <= 0)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.img_scan:
                launchFullFragmentActivity(v);
                break;
        }
    }

    public void launchSimpleActivity(View v) {
        launchActivity(SimpleScannerActivity.class);
    }

    public void launchSimpleFragmentActivity(View v) {
        launchActivity(SimpleScannerFragmentActivity.class);
    }

    public void launchFullActivity(View v) {
        launchActivity(FullScannerActivity.class);
    }

    public void launchFullFragmentActivity(View v) {
        launchActivity(FullScannerFragmentActivity.class);
    }

    public void launchFullScreenScannerFragmentActivity(View v) {
        launchActivity(FullScreenScannerFragmentActivity.class);
    }

    public void launchCustomViewFinderScannerActivity(View v) {
        launchActivity(CustomViewFinderScannerActivity.class);
    }

    public void launchScalingScannerActivity(View v) {
        launchActivity(ScalingScannerActivity.class);
    }

    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mClss != null) {
                        Intent intent = new Intent(this, mClss);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
}