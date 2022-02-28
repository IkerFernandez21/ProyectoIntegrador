package com.ifernandez.proyectointegrador;

import android.content.Context;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.time.Instant;

public class settingsFragment extends Fragment {
    private RelativeLayout r1;
    private RelativeLayout r2;
    private RelativeLayout r3;
    View view;
    Context context;
    String pagWeb;
    Fragment fragment;
    ImageView mIker;
    ImageView mAlex;
    ImageView mEnrique;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.list_item, container, false);

        mIker = view.findViewById(R.id.fotoIker);

        Glide.with(getActivity())
                .load(R.drawable.iker)
                .centerCrop()
                .circleCrop()
                .into(mIker);
        mAlex = view.findViewById(R.id.fotoAlex);

        Glide.with(getActivity())
                .load(R.drawable.alex)
                .centerCrop()
                .circleCrop()
                .into(mAlex);


        mEnrique = view.findViewById(R.id.fotoEnrique);
        Glide.with(getActivity())
                .load(R.drawable.enrique)
                .centerCrop()
                .circleCrop()
                .into(mEnrique);

        return view;
    }









    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        r1 = view.findViewById(R.id.relative1);
        r2 = view.findViewById(R.id.relative2);
        r3 = view.findViewById(R.id.relative3º);

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("profile_url", "https://github.com/IkerFernandez21");

                // fragment manager
                fragment = new webFragment();
                fragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.linearLayoutParent, fragment)
                        .commit();
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("profile_url", "https://github.com/rojasruiz");

                // fragment manager
                fragment = new webFragment();
                fragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.linearLayoutParent, fragment)
                        .commit();
            }
        });
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("profile_url", "https://github.com/EnriqueMoranjobs");

                // fragment manager
                fragment = new webFragment();
                fragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.linearLayoutParent, fragment)
                        .commit();
            }
        });
    }
}