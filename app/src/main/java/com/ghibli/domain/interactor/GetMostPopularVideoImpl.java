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
package com.ghibli.domain.interactor;

import com.ghibli.domain.binder.VideoBinder;
import com.ghibli.domain.model.Video;
import com.ghibli.domain.service.VideoService;
import com.ghibli.executor.Interactor;
import com.ghibli.executor.InteractorExecutor;
import com.ghibli.executor.MainThread;
import com.ghibli.util.DebugUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.json.JSONObject;

public class GetMostPopularVideoImpl implements Interactor, GetMostPopularVideo {

  private final InteractorExecutor interactorExecutor;
  private final MainThread mainThread;
  private Callback callback;

  @Inject GetMostPopularVideoImpl(InteractorExecutor interactorExecutor, MainThread mainThread) {
    this.interactorExecutor = interactorExecutor;
    this.mainThread = mainThread;
  }

  @Override public void execute(Callback callback) {
    if (callback == null) {
      throw new IllegalArgumentException("Callback parameter can't be null");
    }
    this.callback = callback;
    this.interactorExecutor.run(this);
  }

  @Override public void run() {
    try {
      String result = new VideoService().searchUser(callback.getFilter());
      if(result != null) {
        JSONObject obj = new JSONObject(result);
        String items = obj.getString("items");
        List<Video> videos = VideoBinder.getVideoArray(items);
        if(videos.size() > 0) {
          onUserLoaded((ArrayList<Video>) videos);
        } else {
          onEmpty();
        }
      } else {
        onError();
      }
    } catch (Exception e) {
      if (DebugUtil.DEBUG) {
        e.printStackTrace();
      }
      onError();
    }
  }

  private void onUserLoaded(final ArrayList<Video> videos) {
    mainThread.post(new Runnable() {
      @Override public void run() {
        callback.onSuccess(videos);
      }
    });
  }

  private void onEmpty() {
    mainThread.post(new Runnable() {
      @Override public void run() {
        callback.onEmpty();
      }
    });
  }

  private void onError() {
    mainThread.post(new Runnable() {
      @Override public void run() {
        callback.onError();
      }
    });
  }

}

