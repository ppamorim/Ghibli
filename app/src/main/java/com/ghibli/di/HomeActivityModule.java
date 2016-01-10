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
package com.ghibli.di;

import com.ghibli.di.scopes.ActivityScope;
import com.ghibli.domain.interactor.GetMostPopularVideo;
import com.ghibli.domain.interactor.GetMostPopularVideoImpl;
import com.ghibli.ui.presenter.HomePresenter;
import com.ghibli.ui.presenter.HomePresenterImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Modulo que gera a instância do presenter e interactor
 * para o HomePresenter.
 */
@Module public class HomeActivityModule {

  @Provides @ActivityScope HomePresenter provideHomePresenter(
      HomePresenterImpl presenter) {
    return presenter;
  }

  @Provides @ActivityScope GetMostPopularVideo provideGetUser(GetMostPopularVideoImpl
      getMostPopularVideo) {
    return getMostPopularVideo;
  }

}