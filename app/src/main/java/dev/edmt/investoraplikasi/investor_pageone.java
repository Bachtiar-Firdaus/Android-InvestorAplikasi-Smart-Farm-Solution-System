package dev.edmt.investoraplikasi;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class investor_pageone extends Fragment {

    private Button btnmapslahan;
    private Button btnbelislot;
    private Button btntarikuang;
    private Button btnverifikasi;

    View view;

    public investor_pageone() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.investor_pageone, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        btnmapslahan();
        btnbelislot();
        btntarikuang();
        btnverifikasi();

    }

    private void btnmapslahan() {
        btnmapslahan = (Button)view.findViewById(R.id.btnmapslahan);
        btnmapslahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),investor_mapslahan.class);
                startActivity(intent);
            }
        });
    }

    private void btnbelislot() {
        btnbelislot = (Button)view.findViewById(R.id.btnbelislot);
        btnbelislot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),investor_belislot.class);
                startActivity(intent);
            }
        });
    }

    private void btntarikuang() {
        btntarikuang = (Button)view.findViewById(R.id.btntarikuang);
        btntarikuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),investor_tarikuang.class);
                startActivity(intent);
            }
        });

    }

    private void btnverifikasi() {
        btnverifikasi = (Button)view.findViewById(R.id.btnverifikasi);
        btnverifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),investor_verifikasi.class);
                startActivity(intent);
            }
        });

    }



}
