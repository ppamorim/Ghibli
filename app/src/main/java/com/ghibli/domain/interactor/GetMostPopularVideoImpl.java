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
import com.ghibli.domain.getter.VideoGetter;
import com.ghibli.domain.handler.VideoHandler;
import com.ghibli.domain.model.Video;
import com.ghibli.domain.service.VideoService;
import com.ghibli.domain.util.ModelUtil;
import com.ghibli.executor.Interactor;
import com.ghibli.executor.InteractorExecutor;
import com.ghibli.executor.MainThread;
import com.ghibli.util.DebugUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.json.JSONObject;

/**
 * Essa classe trata toda a parte assincrona da requisição
 * para o Youtube. Nela é contida a requisição, bind e
 * salvamento, tudo somente numa thread.
 */
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

  /**
   * Executa de forma assincrona a requisição,
   * bind e salvamento de dados no banco.
   */
  @Override public void run() {
    try {
      String result = new VideoService().getMostPopular();
      if(result != null) {
        JSONObject obj = new JSONObject(result);
        String items = obj.getString("items");
        List<Video> videos = VideoBinder.getVideoArray(items);
        if(videos.size() > 0) {
          ModelUtil.cleanVideos();
          VideoHandler.addOrUpdateVideos((ArrayList<Video>) videos);
          onVideosLoaded((ArrayList<Video>) videos);
        } else {
          onEmpty();
        }
      } else {
        loadSavedPopularVideos();
      }
    } catch (Exception e) {
      if (DebugUtil.DEBUG) {
        e.printStackTrace();
      }
      loadSavedPopularVideos();
    }
  }

  /**
   * Tenta carregar os itens do banco de dados,
   * caso não contenha itens, retorna erro.
   */
  private void loadSavedPopularVideos() {
    ArrayList<Video> videos = VideoGetter.getAllVideos();
    if(videos != null) {
      onVideosLoaded(videos);
    } else {
      onError();
    }
  }

  /**
   * Retorna os dados para a main thread.
   * @param videos
   */
  private void onVideosLoaded(final ArrayList<Video> videos) {
    mainThread.post(new Runnable() {
      @Override public void run() {
        callback.onSuccess(videos);
      }
    });
  }

  /**
   * Informa a main thread que a lista está vazia.
   */
  private void onEmpty() {
    mainThread.post(new Runnable() {
      @Override public void run() {
        callback.onEmpty();
      }
    });
  }

  /**
   * Informa a main thread que ocorreu algum erro.
   */
  private void onError() {
    mainThread.post(new Runnable() {
      @Override public void run() {
        callback.onError();
      }
    });
  }

}

