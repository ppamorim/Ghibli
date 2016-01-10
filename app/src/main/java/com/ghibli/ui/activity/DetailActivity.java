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
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ghibli.GhibliApplication;
import com.ghibli.R;
import com.ghibli.di.ActivityModule;
import com.ghibli.di.DetailActivityModule;
import com.ghibli.di.components.DaggerDetailActivityComponent;
import com.ghibli.di.components.DetailActivityComponent;
import com.ghibli.domain.model.Video;
import com.ghibli.ui.presenter.DetailPresenter;
import com.ghibli.util.ImageUtil;
import javax.inject.Inject;

public class DetailActivity extends AbstractActivity implements DetailPresenter.View {

  private DetailActivityComponent detailActivityComponent;

  @Inject DetailPresenter detailPresenter;

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.video_image) SimpleDraweeView videoImage;
  @Bind(R.id.title) TextView title;
  @Bind(R.id.description) TextView description;

  @OnClick(R.id.video_image) void onVideoClick() {
    Video video = detailPresenter.getVideo();
    if (video != null) {
      startActivity(new Intent(Intent.ACTION_VIEW,
          Uri.parse("http://www.youtube.com/watch?v=" + video.getId())));
    }
  }

  @Override protected int getContentViewId() {
    return R.layout.activity_detail;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    detailActivityComponent().inject(this);
    super.onCreate(savedInstanceState);
    detailPresenter.setView(this);
    configToolbar();
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    Intent intent = getIntent();
    if (intent.hasExtra(Video.TAG)) {
      Video video = getIntent().getParcelableExtra(Video.TAG);
      detailPresenter.setVideo(video);
      loadVideo(video);
      intent.removeExtra(Video.TAG);
    } else {
      detailPresenter.restoreInstance(savedInstanceState);
    }
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(detailPresenter.saveInstance(outState));
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override public boolean isReady() {
    return !isFinishing();
  }

  @Override public void loadVideo(Video video) {
    ImageUtil.loadImage(videoImage, video.getSnippet().getThumbnails().getStandard().getUrl());
    title.setText(video.getSnippet().getTitle());
    description.setText(video.getSnippet().getDescription());
    detailPresenter.setVideo(video);
  }

  private void configToolbar() {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setDisplayShowTitleEnabled(false);
    }
  }

  private DetailActivityComponent detailActivityComponent() {
    if (detailActivityComponent == null) {
      detailActivityComponent = DaggerDetailActivityComponent.builder()
          .applicationComponent(((GhibliApplication) getApplication()).component())
          .activityModule(new ActivityModule(this))
          .detailActivityModule(new DetailActivityModule())
          .build();
    }
    return detailActivityComponent;
  }

}
