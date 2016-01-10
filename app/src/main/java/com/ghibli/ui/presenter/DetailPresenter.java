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

import com.ghibli.domain.model.Video;

/**
 * Interface que cria a conexão entre a
 * DetailActivity e a implementação do presenter.
 */
public interface DetailPresenter extends Presenter {
  void setView(View view);
  void setVideo(Video video);
  Video getVideo();
  interface View {
    boolean isReady();
    void loadVideo(Video video);
  }
}
