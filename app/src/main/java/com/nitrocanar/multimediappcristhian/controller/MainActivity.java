package com.nitrocanar.multimediappcristhian.controller;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;
import com.nitrocanar.multimediappcristhian.R;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivPhoto;
    private Button btnRec;
    private Button btnSave;
    private Button btnTake;
    private Button btnTakeVideo;
    private Button btnGallery;
    private EditText edTitle;

    //Audio
    private MediaRecorder recorder;
    private boolean clickRecord = false;
    private String ficheroUri;

    private String[] PERMISSION_REC = {Manifest.permission.RECORD_AUDIO};



    //Magical
    private MagicalPermissions permissions;
    private MagicalCamera magicalCamera;
    public static int RESIZE_PHOTO = 80;
    public static String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        references();

        if (checkSelfPermission(PERMISSION_REC[0]) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,PERMISSION_REC,111);


        permissions = new MagicalPermissions(this,PERMISSIONS);

        magicalCamera = new MagicalCamera(this,RESIZE_PHOTO,permissions);

    }

    private void references() {
        ivPhoto = findViewById(R.id.ivPhotoCristhian);
        edTitle = findViewById(R.id.tvTitleImage);


        btnTake = findViewById(R.id.btnTakePhoto);
        btnTake.setOnClickListener(this);
        btnRec = findViewById(R.id.btnRec);
        btnRec.setOnClickListener(this);
        btnSave = findViewById(R.id.btnSaves);
        btnSave.setOnClickListener(this);
        btnTakeVideo = findViewById(R.id.btnVideo);
        btnTakeVideo.setOnClickListener(this);
        btnGallery = findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        magicalCamera.resultPhoto(requestCode,resultCode,data,MagicalCamera.ORIENTATION_ROTATE_90);

        ivPhoto.setImageBitmap(magicalCamera.getPhoto());

        magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(),UUID.randomUUID().toString(),MagicalCamera.JPEG,true);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnTakePhoto:
                    if (edTitle.getText().toString().isEmpty()){
                        edTitle.setError("Escribe un nombre");
                        edTitle.requestFocus();
                    }else {
                        magicalCamera.takePhoto();
                    }
                break;

            case R.id.btnRec:
                if (edTitle.getText().toString().isEmpty()){
                    edTitle.setError("Escribe un nombre");
                    edTitle.requestFocus();
                }else {
                    if (clickRecord == true && recorder != null){
                        recorder.stop();
                        btnRec.setBackgroundResource(R.drawable.micro_rec);
                        recorder = null;
                    }else {
                        recorderAudio();

                    }
                }


                break;

            case R.id.btnSaves:
                if (edTitle.getText().toString().isEmpty()){
                    edTitle.setError("Escribe un nombre");
                    edTitle.requestFocus();
                }else {
                    savePhoto();
                    Toast.makeText(this, "Guardado con éxito", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btnGallery:
                if (edTitle.getText().toString().isEmpty()){
                    edTitle.setError("Escribe un nombre");
                    edTitle.requestFocus();
                }else {
                    magicalCamera.selectedPicture("Seleccionar foto");
                }

                break;
            case R.id.btnVideo:
                if (edTitle.getText().toString().isEmpty()){
                    edTitle.setError("Escribe un nombre");
                    edTitle.requestFocus();
                }else {
                    takeVideo();
                }

                break;
        }
    }

    private void savePhoto() {



    }

    private void takeVideo() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,30);
        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Environment.getExternalStorageDirectory().
                getAbsolutePath()+"/"+ UUID.randomUUID() +".mp4" );

        startActivity(takeVideoIntent);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void recorderAudio() {
        if (edTitle.getText().toString().isEmpty()){
            edTitle.setError("Escribe un nombre");
            edTitle.requestFocus();
        }else {
            ficheroUri = Environment.getExternalStorageDirectory().getPath()
                    +"/audios"+edTitle.getText().toString()+".3gp";

            recorder = new MediaRecorder();
            recorder.setOutputFile(ficheroUri);
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);

            try {
                recorder.prepare();
            }catch (Exception e){
                e.printStackTrace();
            }
            recorder.start();
            btnRec.setBackgroundResource(R.drawable.micro_stop);

            clickRecord=true;
        }

    }
}

