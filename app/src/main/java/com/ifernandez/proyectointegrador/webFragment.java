package com.ifernandez.proyectointegrador;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class webFragment extends Fragment {

    View view;
    WebView miVisorWeb;
    String url;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_web, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        miVisorWeb = view.findViewById(R.id.vistaweb);
        url = getArguments().getString("profile_url");
        loadUrl();

    }

    private void loadUrl() {

        if (url != null) {
            miVisorWeb.getSettings().setJavaScriptEnabled(true);
            miVisorWeb.setWebViewClient(new WebViewClient());
            miVisorWeb.loadUrl(url);
        }


    }
}