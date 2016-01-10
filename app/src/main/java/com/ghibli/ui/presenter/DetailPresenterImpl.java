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
import com.ghibli.domain.model.Video;
import javax.inject.Inject;

/**
 * Essa implementação do presenter tem como objetivo
 * tratar o salvamento de instância de forma que
 * obedeça a separação das partes da aplicação,
 * além de trabalhar com a limpeza dos objetos
 * observando o lifecycle da aplicação.
 */
public class DetailPresenterImpl implements DetailPresenter {

  private Video video;
  private View view;

  @Inject public DetailPresenterImpl() { }

  @Override public void setView(View view) {
    if (view == null) {
      throw new IllegalArgumentException("The view must not be null!");
    }
    this.view = view;
  }

  @Override public void initialize() { }

  /**
   * Realiza o salvamento de instância do objeto Video.
   * @param instance Bundle vindo da activity
   * @return Instância com o parcelable adicionado.
   */
  @Override public Bundle saveInstance(Bundle instance) {
    if (video != null) {
      instance.putParcelable(Video.TAG, video);
    }
    return instance;
  }

  /**
   * Realiza a leitura do bundle em busca do
   * parcelable salvo no método `saveInstance`.
   * Após, remove o objeto do bundle.
   * @param instance
   */
  @Override public void restoreInstance(Bundle instance) {
    if (instance != null && instance.containsKey(Video.TAG)) {
      video = instance.getParcelable(Video.TAG);
      instance.remove(Video.TAG);
      loadVideo();
    }
  }

  /**
   * Limpa a instância do objeto Video quando
   * a activity é destruída.
   */
  @Override public void destroy() {
    video = null;
  }

  @Override public void setVideo(Video video) {
    this.video = video;
  }

  @Override public Video getVideo() {
    return this.video;
  }

  private void loadVideo() {
    if (view.isReady() && video != null) {
      view.loadVideo(video);
    }
  }

}
