package com.service.background_services;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.rwtpos.BillDetailsAcitvity;
import com.service.util.PrefsHelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BackgroundNotificationService extends IntentService {

    public BackgroundNotificationService() {
        super("Service");
    }

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    String bill_id = "";
    boolean downloadComplete = false;

    @Override
    protected void onHandleIntent(Intent intent) {
        bill_id = intent.getStringExtra("bill_id");

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("id", "an", NotificationManager.IMPORTANCE_LOW);

            notificationChannel.setDescription("no sound");
            notificationChannel.setSound(null, null);
            notificationChannel.enableLights(false);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        notificationBuilder = new NotificationCompat.Builder(this, "id")
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setContentTitle("Download")
                .setContentText("Downloading Invoice")
                .setDefaults(0)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());

        downloadInvoiceByRetrofit(bill_id);
    }

    public void downloadInvoiceByRetrofit(String bill_id) {
        ApiHelper apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        Call<ResponseBody> loginCall = apiHelper.downloadInvoice(PrefsHelper.getString(this, "username"), PrefsHelper.getString(this, "password"), "2");
        loginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    try {
                        downloadImage(body, bill_id);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call,
                                  @NonNull Throwable t) {
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

//    private void downloadImage(ResponseBody body, String bill_id) throws IOException {
//        if (body != null) {
//            String state = "";
//            state = Environment.getExternalStorageState();
//            if (Environment.MEDIA_MOUNTED.equals(state)) {
//                File direct = new File(Environment.getExternalStorageDirectory()
//                        + "/RwtBills");
//                if (!direct.exists()) {
//                    direct.mkdirs();
//                }
//                File myFile = new File(direct, bill_id + ".pdf");
//                FileOutputStream fstream = new FileOutputStream(myFile);
//                fstream.write(body.bytes());
//                fstream.close();
//                downloadComplete = true;
//                onDownloadComplete(downloadComplete);
////                PreviewInvoiceSheet preview_sheet = new PreviewInvoiceSheet();
////                preview_sheet.show(getSupportFragmentManager(), "exampleBottomSheet");
////                Toast.makeText(context, "Invoice Saved", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(this, "External Storage Not Found", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    private void updateNotification(int currentProgress) {
        notificationBuilder.setProgress(100, currentProgress, false);
        notificationBuilder.setContentText("Downloaded: " + currentProgress + "%");
        notificationManager.notify(0, notificationBuilder.build());
    }


    private void sendProgressUpdate(boolean downloadComplete) {

        Intent intent = new Intent(BillDetailsAcitvity.PROGRESS_UPDATE);
        intent.putExtra("downloadComplete", downloadComplete);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void onDownloadComplete(boolean downloadComplete) {
        sendProgressUpdate(downloadComplete);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText("Download Complete");
        notificationManager.notify(0, notificationBuilder.build());
        NotificationManagerCompat.from(this).cancel(null, 0);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }

    /*private void downloadImage(ResponseBody body) throws IOException {

        int count;
        byte data[] = new byte[1024 * 4];
        long fileSize = body.contentLength();
        InputStream inputStream = new BufferedInputStream(body.byteStream(), 1024 * 8);
        File outputFile = new File(Environment.getExternalStorageDirectory()
                + "/RwtBills");
        OutputStream outputStream = new FileOutputStream(outputFile);
        long total = 0;
        boolean downloadComplete = false;
        //int totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));

        while ((count = inputStream.read(data)) != -1) {

            total += count;
            int progress = (int) ((double) (total * 100) / (double) fileSize);


            updateNotification(progress);
            outputStream.write(data, 0, count);
            downloadComplete = true;
        }
        onDownloadComplete(downloadComplete);
        outputStream.flush();
        outputStream.close();
        inputStream.close();

    }*/
    private void downloadImage(ResponseBody body, String bill_id) throws IOException {
        if (body != null) {
            long total = 0;
            int count;
            byte data[] = new byte[1024 * 4];
            long fileSize = body.contentLength();
            InputStream inputStream = new BufferedInputStream(body.byteStream(), 1024 * 8);

            String state = "";
            state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                File direct = new File(Environment.getExternalStorageDirectory()
                        + "/RwtBills");
                if (!direct.exists()) {
                    direct.mkdirs();
                }
                File myFile = new File(direct, bill_id + ".pdf");
                FileOutputStream fstream = new FileOutputStream(myFile);

                while ((count = inputStream.read(data)) != -1) {

                    total += count;
                    int progress = (int) ((double) (total * 100) / (double) fileSize);

                    updateNotification(progress);
                    fstream.write(data, 0, count);
                    downloadComplete = true;
                }
                fstream.close();
                downloadComplete = true;
                onDownloadComplete(downloadComplete);
//                PreviewInvoiceSheet preview_sheet = new PreviewInvoiceSheet();
//                preview_sheet.show(getSupportFragmentManager(), "exampleBottomSheet");
//                Toast.makeText(context, "Invoice Saved", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "External Storage Not Found", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
