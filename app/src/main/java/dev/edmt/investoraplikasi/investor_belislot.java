package dev.edmt.investoraplikasi;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class investor_belislot extends AppCompatActivity {
private String email;
private TextView saldo,slotyangdibeli,totalbayar,slotaktiv,hargaperslot,ktrendah,kttinggi;
private EditText tampilslotyangdibeli;
private Button beli;
    private DatabaseReference mUserDatabase,mUserDatabase2,mUserDatabase3;
    private FirebaseUser mCurrentUser;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.investor_activity_belislot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        //mendapat kan time sekarang
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String currentDateTime = dateFormat.format(new Date());


        hargaperslot = (TextView) findViewById(R.id.tv_hargaperslot);
        slotaktiv = (TextView) findViewById(R.id.tv_slotaktiv);
        ktrendah = (TextView) findViewById(R.id.tv_ktrendah);
        kttinggi = (TextView) findViewById(R.id.tv_kttinggi);
        beli = (Button) findViewById(R.id.bt_beli);




        saldo = (TextView) findViewById(R.id.tv_saldo);
        slotyangdibeli = (TextView) findViewById(R.id.tv_slotyangdibeli);
        totalbayar = (TextView) findViewById(R.id.tv_totalbayar);
        tampilslotyangdibeli = (EditText) findViewById(R.id.et_slotyangdibeli);


        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("INVESTOR").child(current_uid);
        mUserDatabase3 = FirebaseDatabase.getInstance().getReference().child("INVESTOR-LAPORAN").child(currentDateTime);
        mUserDatabase.keepSynced(true);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String simpan = dataSnapshot.child("saldo").getValue().toString();
                saldo.setText(simpan);
                String simpan1 = dataSnapshot.child("slotdibeli").getValue().toString();
                slotyangdibeli.setText(simpan1);
                String simpan5 = dataSnapshot.child("totalbayar").getValue().toString();
                totalbayar.setText(simpan5);
                String simpan2 = dataSnapshot.child("email").getValue().toString();
                email = simpan2;


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        String key = "-L6n-aado-cZYmhyoM7h";
        mUserDatabase2 = FirebaseDatabase.getInstance().getReference().child("SLOT").child(key);
        mUserDatabase2.keepSynced(true);
        mUserDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot2) {

                String simpan2 = dataSnapshot2.child("harga").getValue().toString();
                hargaperslot.setText(simpan2);
                String simpan3 = dataSnapshot2.child("slot").getValue().toString();
                slotaktiv.setText(simpan3);
                String simpan4 = dataSnapshot2.child("pkrendah").getValue().toString();
                ktrendah.setText(simpan4);
                String simpan5 = dataSnapshot2.child("pktinggi").getValue().toString();
                kttinggi.setText(simpan5);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ahargaperslot = hargaperslot.getText().toString();
                String aslotaktiv = slotaktiv.getText().toString();

                String asaldo = saldo.getText().toString();
                String munculslotyangdibeli = slotyangdibeli.getText().toString();
                String masukslotyangdibeli = tampilslotyangdibeli.getText().toString();
                String tanggal = currentDateTime.toString();

                String atotalbayar = totalbayar.getText().toString();



                int nahargaperslot = Integer.parseInt(ahargaperslot.toString());
                int naslotaktiv = Integer.parseInt(aslotaktiv.toString());

                int nasaldo = Integer.parseInt(asaldo.toString());
                int nmunculslotyangdibeli = Integer.parseInt(munculslotyangdibeli.toString());
                int nmasukslotyangdibeli = Integer.parseInt(masukslotyangdibeli.toString());
                int natotalbayar = Integer.parseInt(atotalbayar.toString());

                int tot = nmasukslotyangdibeli * nahargaperslot;
                int a = naslotaktiv - nmasukslotyangdibeli;/*

                String update2 = Integer.toString(tot);*/


                if((nasaldo) < (tot)){
                    Toast.makeText(investor_belislot.this, "SALDO ANDA KURANG", Toast.LENGTH_LONG).show();

                }else if((nasaldo) >= 0 && a > 0){
                    int hasil = nasaldo - tot;
                    String update = Integer.toString(hasil);
                    Toast.makeText(investor_belislot.this, "SLOT ATAU SALDO TIDAK MEMENUHI SARAT TRANSAKSI", Toast.LENGTH_LONG).show();


                     //nembak data ke data base
                    mProgress = new ProgressDialog(investor_belislot.this);
                    mProgress.setTitle("Saving Changes");
                    mProgress.setMessage("Please wait while we save the changes");
                    mProgress.show();



                    mUserDatabase3.child("tanggalpembelian").setValue(tanggal).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                mProgress.dismiss();

                            } else {

                                Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                            }

                        }
                    });

                    mUserDatabase3.child("email").setValue(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                mProgress.dismiss();

                            } else {

                                Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                            }

                        }
                    });

                    String base = hargaperslot.getText().toString();
                    mUserDatabase3.child("hargaperslotdiwaktuitu").setValue(base).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                mProgress.dismiss();

                            } else {

                                Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                            }

                        }
                    });





                    mUserDatabase.child("saldo").setValue(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                mProgress.dismiss();

                            } else {

                                Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                            }

                        }
                    });


                    int nsyd = nmunculslotyangdibeli +nmasukslotyangdibeli;
                    String update2 = Integer.toString(nsyd);
                    mUserDatabase.child("slotdibeli").setValue(update2).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                mProgress.dismiss();

                            } else {

                                Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                            }

                        }
                    });
                    mUserDatabase3.child("slotdibeli").setValue(update2).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                mProgress.dismiss();

                            } else {

                                Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                            }

                        }
                    });

                    int totbay = natotalbayar + tot;
                    String update3 = Integer.toString(totbay);
                    mUserDatabase.child("totalbayar").setValue(update3).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                mProgress.dismiss();

                            } else {

                                Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                            }

                        }
                    });
                    int ss = naslotaktiv - nmasukslotyangdibeli;
                    String update4 = Integer.toString(ss);
                    mUserDatabase2.child("slot").setValue(update4).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                mProgress.dismiss();

                            } else {

                                Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                            }

                        }
                    });

                }else {
                    Toast.makeText(investor_belislot.this, "SILAHKAN TULIS SLOT YANG AKAN DI BELI", Toast.LENGTH_LONG).show();

                }
            }
        });



    }

    /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_custumerservice) {
            // Handle the camera action
        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/


    }

