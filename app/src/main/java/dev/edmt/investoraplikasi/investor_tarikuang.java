package dev.edmt.investoraplikasi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class investor_tarikuang extends AppCompatActivity {
    private TextView saldo, proses;
    private EditText tarik;
    private Button bttarik, btbataltarik;

    private DatabaseReference mUserDatabase, mUserDatabase2;
    private FirebaseUser mCurrentUser;
    private ProgressDialog mProgress;
    private String save, save1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.investor_tarikuang);

            saldo = (TextView) findViewById(R.id.tv_saldo);
            tarik = (EditText) findViewById(R.id.et_tarik);
            proses = (TextView) findViewById(R.id.tv_proses);
            bttarik = (Button) findViewById(R.id.bt_tarik);
            btbataltarik = (Button) findViewById(R.id.bt_bataltarik);

            mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
            final String current_uid = mCurrentUser.getUid();
            mUserDatabase2 = FirebaseDatabase.getInstance().getReference().child("INVESTOR-req").child(current_uid);
            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("INVESTOR").child(current_uid);
            mUserDatabase.keepSynced(true);
            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String simpan = dataSnapshot.child("saldo").getValue().toString();
                    saldo.setText(simpan);
                    String simpan2 = dataSnapshot.child("email").getValue().toString();
                    save = simpan2;
                    String simpan3 = dataSnapshot.child("totaltarik").getValue().toString();
                    proses.setText(simpan3);
                }

                @Override

                public void onCancelled(DatabaseError databaseError) {

                }
            });


            bttarik.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ssaldo = saldo.getText().toString();
                    String starik = tarik.getText().toString();
                    String sproses = proses.getText().toString();

                    int nssaldo = Integer.parseInt(ssaldo.toString());
                    int nstarik = Integer.parseInt(starik.toString());
                    int nsproses = Integer.parseInt(sproses.toString());
                    int A = nstarik + nsproses;

                    if (A > (nssaldo)) {
                        Toast.makeText(investor_tarikuang.this, "SALDO ANDA KURANG", Toast.LENGTH_LONG).show();

                    } else if (A <= (nssaldo)) {
                        Toast.makeText(investor_tarikuang.this, "PERMINTAAN ANDA DALAM PROSES VERIVIKASI", Toast.LENGTH_LONG).show();
                        int hasil = nstarik + nsproses;
                        String update = Integer.toString(hasil);

                        mProgress = new ProgressDialog(investor_tarikuang.this);
                        mProgress.setTitle("Saving Changes");
                        mProgress.setMessage("Please wait while we save the changes");
                        mProgress.show();
                        mUserDatabase2.child("totaltarik").setValue(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    mProgress.dismiss();

                                } else {

                                    Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                                }
                            }
                        });

                        mUserDatabase.child("totaltarik").setValue(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    mProgress.dismiss();

                                } else {

                                    Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                                }
                            }
                        });

                        String ssave = save.toString();
                        mUserDatabase2.child("email").setValue(ssave).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    mProgress.dismiss();

                                } else {

                                    Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                                }
                            }
                        });


                    } else {
                        Toast.makeText(investor_tarikuang.this, "SILAHKAN TULIS SALDO YANG AKAN DI TARIK", Toast.LENGTH_LONG).show();

                    }
                }
            });


            btbataltarik.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mUserDatabase2.setValue(null);

                    mUserDatabase.child("totaltarik").setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

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