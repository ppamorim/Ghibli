/*
* Copyright (C) 2016 Pedro Paulo de Amorim
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.ghibli.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import com.ghibli.GhibliApplication;
import com.ghibli.R;
import com.ghibli.di.ActivityModule;
import com.ghibli.di.HomeActivityModule;
import com.ghibli.di.components.DaggerHomeActivityComponent;
import com.ghibli.di.components.HomeActivityComponent;
import com.ghibli.domain.model.Video;
import com.ghibli.ui.adapter.VideoAdapter;
import com.ghibli.ui.listener.RecyclerItemClickListener;
import com.ghibli.ui.presenter.HomePresenter;
import java.util.ArrayList;
import javax.inject.Inject;

public class HomeActivity extends AbstractActivity implements HomePresenter.View,
    RecyclerItemClickListener.OnItemClickListener {

  private HomeActivityComponent homeActivityComponent;

  @Inject HomePresenter homePresenter;

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.recycler_view) RecyclerView recyclerView;
  @Bind(R.id.loading_view) ProgressBar progressBar;
  @Bind(R.id.error_view) TextView errorView;

  @Override protected int getContentViewId() {
    return R.layout.activity_home;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    homeActivityComponent().inject(this);
    super.onCreate(savedInstanceState);
    homePresenter.setView(this);
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    configToolbar();
    configRecyclerView();
    searchUser();
  }

  @Override public boolean isReady() {
    return !isFinishing();
  }

  @Override public void renderUsers(ArrayList<Video> video) {
    ((VideoAdapter)recyclerView.getAdapter()).setVideos(video);
    showRecyclerView();
  }

  @Override public void onEmpty() {
    showErrorView(R.string.request_no_item);
  }

  @Override public void onError() {
    showErrorView(R.string.request_error);
  }

  @Override public void onItemClick(View view, int position) {
    showUserDetails(homePresenter.getItem(position));
  }

  private void searchUser() {
    showProgressBar();
    homePresenter.setFilter("Vintage");
    homePresenter.initialize();
  }

  private void showRecyclerView() {
    recyclerView.setVisibility(View.VISIBLE);
    progressBar.setVisibility(View.GONE);
    errorView.setVisibility(View.GONE);
  }

  private void showProgressBar() {
    recyclerView.setVisibility(View.GONE);
    progressBar.setVisibility(View.VISIBLE);
    errorView.setVisibility(View.GONE);
  }

  private void showErrorView(int errorId) {
    recyclerView.setVisibility(View.GONE);
    progressBar.setVisibility(View.GONE);
    errorView.setVisibility(View.VISIBLE);
    errorView.setText(errorId);
  }

  private void configToolbar() {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setTitle(R.string.app_name);
    }
  }

  private void configRecyclerView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(new VideoAdapter());
    recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, this));
  }

  private void showUserDetails(Video video) {
    Intent intent = new Intent(this, DetailActivity.class);
    intent.putExtra(Video.TAG, video);
    startActivity(intent);
  }

  private HomeActivityComponent homeActivityComponent() {
    if(homeActivityComponent == null) {
      homeActivityComponent = DaggerHomeActivityComponent.builder()
          .applicationComponent(((GhibliApplication) getApplication()).component())
          .activityModule(new ActivityModule(this))
          .homeActivityModule(new HomeActivityModule())
          .build();
    }
    return homeActivityComponent;
  }

}
