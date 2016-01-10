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
import com.ghibli.ui.presenter.DetailPresenter;
import com.ghibli.ui.presenter.DetailPresenterImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Modulo que gera a instância do presenter para
 * o DetailPresenter.
 */
@Module public class DetailActivityModule {

  @Provides @ActivityScope DetailPresenter provideDetailPresenter(DetailPresenterImpl presenter) {
    return presenter;
  }

}