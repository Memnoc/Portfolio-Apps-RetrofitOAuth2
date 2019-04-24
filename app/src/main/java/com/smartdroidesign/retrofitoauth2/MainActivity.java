package com.smartdroidesign.retrofitoauth2;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.smartdroidesign.retrofitoauth2.api.Imgur;
import com.smartdroidesign.retrofitoauth2.api.OAuthUtil;
import com.smartdroidesign.retrofitoauth2.api.Service;
import com.smartdroidesign.retrofitoauth2.model.Basic;
import com.smartdroidesign.retrofitoauth2.model.Image;
import com.smartdroidesign.retrofitoauth2.view.ImageAdapter;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.btn_sign_in)
    View signInBtn;
    @Bind(R.id.btn_upload_anon)
    View uploadAnon;

    @Bind(R.id.account_images_container)
    View accountImagesContainer;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.btn_upload)
    View upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        signInBtn.setOnClickListener(this);
        uploadAnon.setOnClickListener(this);
        upload.setOnClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new ImageAdapter(this));

        if (OAuthUtil.isAuthorized()) {
            // TODO set title
            showAccountImages();
        } else {
            toolbar.setTitle("Login");
            showLoginOrAnon();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(Imgur.REDIRECT_URL)) {

            // create a temp Uri to make it easier to pull out the data we need
            Uri temp = Uri.parse("https://smartdroidworkshop.com?" + uri.getFragment().trim());

            OAuthUtil.set(OAuthUtil.ACCESS_TOKEN, temp.getQueryParameter(OAuthUtil.ACCESS_TOKEN));
            OAuthUtil.set(OAuthUtil.EXPIRES_IN, System.currentTimeMillis() + (Long.parseLong(temp.getQueryParameter(OAuthUtil.EXPIRES_IN)) * 1000));
            OAuthUtil.set(OAuthUtil.TOKEN_TYPE, temp.getQueryParameter(OAuthUtil.TOKEN_TYPE));
            OAuthUtil.set(OAuthUtil.REFRESH_TOKEN, temp.getQueryParameter(OAuthUtil.REFRESH_TOKEN));
            OAuthUtil.set(OAuthUtil.ACCOUNT_USERNAME, temp.getQueryParameter(OAuthUtil.ACCOUNT_USERNAME));

            if (OAuthUtil.isAuthorized()) {
                toolbar.setTitle(OAuthUtil.get(OAuthUtil.ACCOUNT_USERNAME));
                showAccountImages();
            } else {
                toolbar.setTitle("Login");
                showLoginOrAnon();
            }
        }
    }

    private void showLoginOrAnon() {
        accountImagesContainer.setVisibility(View.GONE);
        signInBtn.setVisibility(View.VISIBLE);
        uploadAnon.setVisibility(View.VISIBLE);
    }

    private void showAccountImages() {
        // TODO 1 - Define fetchAccountImages();
        fetchAccountImages();

        accountImagesContainer.setVisibility(View.VISIBLE);
        signInBtn.setVisibility(View.GONE);
        uploadAnon.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_upload_anon:
                // TODO
                break;
            case R.id.btn_upload:
                // TODO
                break;
            case R.id.btn_sign_in:
                // TODO start login process
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Imgur.AUTHORIZATION_URL)));
                break;
        }
    }

    private void fetchAccountImages() {
        Snackbar.make(upload, "Getting images for Account", Snackbar.LENGTH_SHORT).show();
        Service.getAuthedApi().images(OAuthUtil.get(OAuthUtil.ACCOUNT_USERNAME), 0)
                .enqueue(new Callback<Basic<ArrayList<Image>>>() {
                    @Override
                    public void onResponse(Call<Basic<ArrayList<Image>>> call, Response<Basic<ArrayList<Image>>> response) {

                    }

                    @Override
                    public void onFailure(Call<Basic<ArrayList<Image>>> call, Throwable t) {
                        Snackbar.make(upload, "Failed :(", Snackbar.LENGTH_SHORT).show();
                    }
                });
    }


}