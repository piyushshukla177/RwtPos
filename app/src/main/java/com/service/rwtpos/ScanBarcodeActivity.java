package com.service.rwtpos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class ScanBarcodeActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    BarcodeReader barcodeReader;
    CreateBillActivity createbillactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode);
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
    }

    @Override
    public void onScanned(Barcode barcode) {
        barcodeReader.playBeep();
        createbillactivity.getProductBybarcode(String.valueOf(barcode));
        finish();
        Toast.makeText(getApplicationContext(), "On Scanned", Toast.LENGTH_SHORT).show();
//        String code = barcode.displayValue;
//        if (code.length() > 10) {
//            Intent intent = new Intent(ScanBarcodeActivity.this, ManuallyBarcodeActivity.class);
//            intent.putExtra("code", code);
//            startActivity(intent);
//            finish();
//        } else {
//            Toast.makeText(ScanBarcodeActivity.this, "Invalid Barcode, scan again", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {
        String code = list.get(2).displayValue;
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String s) {
        Toast.makeText(getApplicationContext(), "Error occurred while scanning " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraPermissionDenied() {
        finish();
        Toast.makeText(getApplicationContext(), "On Camera Permission Denied", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(getApplicationContext(), "resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}