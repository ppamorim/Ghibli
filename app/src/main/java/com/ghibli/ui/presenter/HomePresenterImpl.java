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
package com.ghibli.ui.presenter;

import com.ghibli.domain.interactor.GetMostPopularVideo;
import com.ghibli.domain.model.Video;
import java.util.ArrayList;
import javax.inject.Inject;

public class HomePresenterImpl implements HomePresenter {

  private View view;
  private String filter;
  private ArrayList<Video> videos;
  private GetMostPopularVideo getMostPopularVideo;

  @Inject HomePresenterImpl(GetMostPopularVideo getMostPopularVideo) {
    this.getMostPopularVideo = getMostPopularVideo;
  }

  @Override public void setView(View view) {
    if (view == null) {
      throw new IllegalArgumentException("The view must not be null!");
    }
    this.view = view;
  }

  @Override public void setFilter(String filter) {
    this.filter = filter;
  }

  @Override public void initialize() {
    getUser();
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {
    if(videos != null) {
      videos.clear();
    }
    videos = null;
  }

  @Override public Video getItem(int position) {
    return videos.get(position);
  }

  private void getUser() {
    getMostPopularVideo.execute(new GetMostPopularVideo.Callback() {
      @Override public void onSuccess(ArrayList<Video> videos) {
        notifyUsers(videos);
      }

      @Override public void onEmpty() {
        notifyEmpty();
      }

      @Override public void onError() {
        notifyError();
      }

      @Override public String getFilter() {
        return filter;
      }
    });
  }

  private void notifyUsers(ArrayList<Video> videos) {
    this.videos = videos;
    if(view.isReady()) {
      view.renderUsers(videos);
    }
  }

  private void notifyEmpty() {
    if(view.isReady()) {
      view.onEmpty();
    }
  }

  private void notifyError() {
    if(view.isReady()) {
      view.onError();
    }
  }

}
