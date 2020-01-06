package dev.edmt.investoraplikasi;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class investor_verifikasi extends AppCompatActivity {

    ImageView imageView;
    String nama, email;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    public static final String STORAGE_PATH ="investor_verifikasi/";
    public static final  String DATA_PATH = "verifikasi_investor";

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    private ProgressDialog mProgress;

    private Uri filePath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.investor_verifikasi);
        imageView = (ImageView)findViewById(R.id.image);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("INVESTOR").child(current_uid);
        mUserDatabase.keepSynced(true);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String simpan = dataSnapshot.child("name").getValue().toString();
                nama = simpan;
                String simpan2 = dataSnapshot.child("email").getValue().toString();
                email = simpan2;
            }
            @Override

            public void onCancelled(DatabaseError databaseError) {

            }
        });

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(DATA_PATH);
    }


    public void uploadData(View view) {
        if (filePath != null){

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference reference = storageReference.child(STORAGE_PATH + System.currentTimeMillis()+"." + getActualImage(filePath));
            reference .putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String NAME = nama.toString();
                    String EMAIL = email.toString();

                    m_verifikasi mverifikasi = new m_verifikasi(NAME, EMAIL, taskSnapshot.getDownloadUrl().toString());
                    String id = databaseReference.push().getKey();

                    databaseReference.child(id).setValue(mverifikasi);
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Data Uploaded", Toast.LENGTH_LONG).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double totalProgress = (100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploat % "+ (int)totalProgress);
                        }
                    });


        }else {
            Toast.makeText(getApplicationContext(),"Data Uploaded", Toast.LENGTH_LONG).show();
        }

    }
/*
    public void viewAlldata(View view) {
        Intent intent = new Intent(investor_verifikasi.this, v_verifikasi.class);
        startActivity(intent);
    }*/

    public void BrowseImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*.jpg");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an image"), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getActualImage(Uri uri){

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
