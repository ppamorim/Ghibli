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
package com.ghibli.di.components;

import android.app.Activity;
import com.ghibli.di.ActivityModule;
import com.ghibli.di.DetailActivityModule;
import com.ghibli.di.scopes.ActivityScope;
import com.ghibli.ui.activity.DetailActivity;
import com.ghibli.ui.presenter.DetailPresenter;
import dagger.Component;

/**
 * Este componente é utilizado pela activity e ele gera
 * a instância do presenter para ser injetado na activity.
 */
@ActivityScope @Component(dependencies = ApplicationComponent.class, modules = {
    ActivityModule.class,
    DetailActivityModule.class })
public interface DetailActivityComponent extends AbstractActivityComponent {
  void inject(DetailActivity detailActivity);
  Activity activityContext();
  DetailPresenter getPresenter();
}