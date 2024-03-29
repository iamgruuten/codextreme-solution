package com.example.appdevin.myapplication;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.plattysoft.leonids.ParticleSystem;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class request_activity extends AppCompatActivity {

//Request for help
    private static final int REQUEST_CAMERA = 3;
    private static final int SELECT_FILE = 2;
    public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;

    FrameLayout profile;
    Bitmap imageBitmap;
    ImageView back;
    ImageView image;
    Uri imageUri;
    Button submit;
    ParticleSystem ps;

    String sPostalcode;
    String sDescription;

    EditText description;
    EditText postalcode;

    DatabaseReference Dref= FirebaseDatabase.getInstance().getReference("Quests");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_activity);

        profile = findViewById(R.id.frame);
        back = findViewById(R.id.back);

        image = findViewById(R.id.image_recycle);
        submit = findViewById(R.id.submit);

        description = findViewById(R.id.description);
        postalcode = findViewById(R.id.postal);

        // Launch 2 particle systems one for each image
        ps = new ParticleSystem(this ,100, R.drawable.star_white_border, 800);
        ps.setScaleRange(0.9f, 2.3f);
        ps.setSpeedRange(0.01f, 0.15f);
        ps.setAcceleration(0.00001f, 120);
        ps.setRotationSpeedRange(90, 180);
        ps.setFadeOut(900, new AccelerateInterpolator());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sPostalcode = postalcode.getText().toString();
                sDescription = description.getText().toString();

                StorageReference storage = FirebaseStorage.getInstance().getReference();

                final String Key=Dref.push().getKey();


                StorageReference PutFile=storage.child("Quest_Photo").child(Key);

                PutFile.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("Uploaded","true");

                        request_connector request_connector = new request_connector(Login.User.name,sPostalcode,Login.User.contact,Key,sDescription);

                        Dref.child(Dref.push().getKey()).setValue(request_connector);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Uploaded",e.getLocalizedMessage());
                    }
                });


                achievements();
                ps.oneShot(v, 230);






                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       onBackPressed();

                    }
                }, 1500);


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageloader();
            }
        });
    }

    //Method to decide the option
    private void imageloader() {

        //DISPLAY DIALOG TO CHOOSE CAMERA OR GALLERY

        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(request_activity.this);
        builder.setTitle("Profile Picture");

        //SET ITEMS AND THERE LISTENERS
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    Toast.makeText(request_activity.this, "Camera SELECTED", Toast.LENGTH_LONG).show();
                    try {
                        if (ContextCompat.checkSelfPermission(request_activity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(request_activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                            ActivityCompat.requestPermissions(request_activity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                        }
                        else{
                            Toast.makeText(request_activity.this, "Camera Intent running", Toast.LENGTH_SHORT).show();
                            cameraIntent();
                        }
                    } catch (Exception e) {
                        finish();
                    }
                } else if (items[item].equals("Choose from Library")) {
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 110) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                cameraIntent();
            }
        }}

    //CHOOSE CAMERA
    private String pictureImagePath = "";
    private void cameraIntent() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
        File file = new File(pictureImagePath);
        Uri outputFileUri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".my.package.name.provider", file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
    }

    //CHOOSE IMAGE FROM GALLERY
    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //SAVE URI FROM GALLERY
        if(requestCode == SELECT_FILE && resultCode == RESULT_OK)
        {
            imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }else if (requestCode == CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK ){
            //Convert Bitmap to URI
            File imgFile = new File(pictureImagePath);
            imageBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            File files = createImageFile();
            if (files != null) {
                FileOutputStream fout;
                try {
                    fout = new FileOutputStream(files);
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
                    fout.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                imageUri = Uri.fromFile(files);

                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
            }

        }

        //image crop library code
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                image.setImageURI(imageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Error Cropping image", Toast.LENGTH_SHORT).show();
            }
        }

    }
    //Creating a temporary photo dir to automatically convert bitmap to URI

    public File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File mFileTemp = null;
        String root = this.getDir("my_sub_dir", Context.MODE_PRIVATE).getAbsolutePath();
        File myDir = new File(root + "/Img");
        if(!myDir.exists()){
            myDir.mkdirs();
        }
        try {
            mFileTemp=File.createTempFile(imageFileName,".jpg",myDir.getAbsoluteFile());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return mFileTemp;
    }

    private void achievements(){

        // custom dialog
        Dialog dialog;
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.pop_thanks);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();




    }
}
