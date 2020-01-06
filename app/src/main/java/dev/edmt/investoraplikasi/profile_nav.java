package dev.edmt.investoraplikasi;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile_nav extends AppCompatActivity {
    private EditText alamat,email,jenisbang,name,nik,nohp,norek;
    private Button ubah;
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_nav);
        alamat = (EditText)findViewById(R.id.p_alamat);
        email = (EditText)findViewById(R.id.p_email);
        jenisbang= (EditText)findViewById(R.id.p_jenisbank);
        name= (EditText)findViewById(R.id.p_nama);
        nik= (EditText)findViewById(R.id.p_nik);
        nohp= (EditText)findViewById(R.id.p_nohp);
        norek= (EditText)findViewById(R.id.p_norek);
        ubah= (Button) findViewById(R.id.p_ubah);


        email.setEnabled(false);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("INVESTOR").child(current_uid);
        mUserDatabase.keepSynced(true);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String galamat = dataSnapshot.child("alamat").getValue().toString();
                alamat.setText(galamat);
                String gemail = dataSnapshot.child("email").getValue().toString();
                email.setText(gemail);
                String gjenisbang = dataSnapshot.child("jenisbang").getValue().toString();
                jenisbang.setText(gjenisbang);
                String gname = dataSnapshot.child("name").getValue().toString();
                name.setText(gname);
                String gnik = dataSnapshot.child("nik").getValue().toString();
                nik.setText(gnik);
                String gnohp = dataSnapshot.child("nohp").getValue().toString();
                nohp.setText(gnohp);
                String gnorek = dataSnapshot.child("norek").getValue().toString();
                norek.setText(gnorek);
            }
            @Override

            public void onCancelled(DatabaseError databaseError) {

            }
        });



        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String salamat = alamat.getText().toString();
                String semail = email.getText().toString();
                String sjenisbang = jenisbang.getText().toString();
                String sname = name.getText().toString();
                String snik = nik.getText().toString();
                String snohp = nohp.getText().toString();
                String snorek = norek.getText().toString();

                mProgress = new ProgressDialog(profile_nav.this);
                mProgress.setTitle("Saving Changes");
                mProgress.setMessage("Please wait while we save the changes");
                mProgress.show();
                mUserDatabase.child("alamat").setValue(salamat).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            mProgress.dismiss();

                        } else {

                            Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                        }
                    }
                });


                mUserDatabase.child("email").setValue(semail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            mProgress.dismiss();

                        } else {

                            Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                        }
                    }
                });



                mUserDatabase.child("jenisbang").setValue(sjenisbang).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            mProgress.dismiss();

                        } else {

                            Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                        }
                    }
                });



                mUserDatabase.child("name").setValue(sname).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            mProgress.dismiss();

                        } else {

                            Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                        }
                    }
                });



                mUserDatabase.child("nik").setValue(snik).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            mProgress.dismiss();

                        } else {

                            Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                        }
                    }
                });




                mUserDatabase.child("nohp").setValue(snohp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            mProgress.dismiss();

                        } else {

                            Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                        }
                    }
                });
                mUserDatabase.child("norek").setValue(snorek).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            mProgress.dismiss();

                        } else {

                            Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }
        });
    }

}
