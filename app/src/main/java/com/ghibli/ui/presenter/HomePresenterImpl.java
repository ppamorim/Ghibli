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

import android.os.Bundle;
import com.ghibli.domain.interactor.GetMostPopularVideo;
import com.ghibli.domain.model.Video;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * Essa implementação do presenter tem como objetivo
 * tratar o salvamento de instância de forma que
 * obedeça a separação das partes da aplicação,
 * além de trabalhar com a limpeza dos objetos
 * observando o lifecycle da aplicação.
 * Ela também realiza a requisição para adquirir os
 * vídeos mais populares do Youtube BR.
 */
public class HomePresenterImpl implements HomePresenter {

  private static final String VIDEOS = "videos";

  private View view;
  private ArrayList<Video> videos;
  private GetMostPopularVideo getMostPopularVideo;

  /**
   * Declara a instância do interactor assincrono.
   * @param getMostPopularVideo Instância do interactor.
   */
  @Inject HomePresenterImpl(GetMostPopularVideo getMostPopularVideo) {
    this.getMostPopularVideo = getMostPopularVideo;
  }

  @Override public void setView(View view) {
    if (view == null) {
      throw new IllegalArgumentException("The view must not be null!");
    }
    this.view = view;
  }

  @Override public void initialize() {
    getMostPopularVideo();
  }

  /**
   * Realiza o salvamento de instância do objeto array de Video.
   * @param instance Bundle vindo da activity.
   * @return Instância com o parcelable adicionado.
   */
  @Override public Bundle saveInstance(Bundle instance) {
    if (instance != null && videos != null && videos.size() > 0) {
      instance.putParcelableArrayList(VIDEOS, videos);
    }
    return instance;
  }

  /**
   * Realiza a leitura do bundle em busca do array de
   * parcelable salvo no método `saveInstance`. Após,
   * remove o objeto do bundle.
   * @param instance Instância a ser salva.
   */
  @Override public void restoreInstance(Bundle instance) {
    if (instance != null && instance.containsKey(VIDEOS)) {
      videos = instance.getParcelableArrayList(VIDEOS);
      instance.remove(VIDEOS);
    }
  }

  /**
   * Remove os itens da lista se necessário
   * e posteriormente invalida o objeto.
   */
  @Override public void destroy() {
    if (videos != null) {
      videos.clear();
    }
    videos = null;
  }

  /**
   * Verifica se a lista de vídeos está vazia.
   * @return videos é igual a null ou se a lista está vazia.
   */
  @Override public boolean isMostPopularEmpty() {
    return videos == null || videos.isEmpty();
  }

  /**
   * Retorna o item da lista de vídeos a partir da
   * posição, caso contrário, retorna null.
   * @param position Posição do item na lista.
   * @return Item da lista.
   */
  @Override public Video getItem(int position) {
    return videos != null ? videos.get(position) : null;
  }

  /**
   * Realiza, de forma assincrona, a requisição
   * dos vídeos mais populares.
   * O Callback informa o estado final da requisição.
   */
  private void getMostPopularVideo() {
    getMostPopularVideo.execute(new GetMostPopularVideo.Callback() {
      @Override public void onSuccess(ArrayList<Video> videos) {
        notifyMostPopularVideo(videos);
      }

      @Override public void onEmpty() {
        notifyEmpty();
      }

      @Override public void onError() {
        notifyError();
      }

    });
  }

  private void notifyMostPopularVideo(ArrayList<Video> videos) {
    this.videos = videos;
    if (view.isReady()) {
      view.renderPopularVideo(videos);
    }
  }

  private void notifyEmpty() {
    if (view.isReady()) {
      view.onEmpty();
    }
  }

  private void notifyError() {
    if (view.isReady()) {
      view.onError();
    }
  }

}
