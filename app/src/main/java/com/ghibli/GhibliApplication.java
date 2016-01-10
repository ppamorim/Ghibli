/*
* Copyright (C) 2016 Pedro Paulo de Amorim
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the
* License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.ghibli;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.ghibli.di.ApplicationModule;
import com.ghibli.di.components.ApplicationComponent;
import com.ghibli.di.components.DaggerApplicationComponent;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Classe que determina as configurações principais da aplicação.
 */
public class GhibliApplication extends Application {

  public static final String API = "https://www.googleapis.com/youtube/v3/";
  public static final String YOUTUBE_KEY = "AIzaSyCnbgeDrb8glx7NpFNvEubiqWfMZjGZdXc";

  private ApplicationComponent applicationComponent;

  public GhibliApplication() {
    super();
  }

  /**
   * Injeta as dependências principais (Interadores e Contextos).
   */
  @Override public void onCreate() {
    super.onCreate();
    initializeDependencyInjector().inject(this);
    Fresco.initialize(this);
    configRealm();
  }

  @Override public void onTerminate() {
    super.onTerminate();
    Fresco.shutDown();
  }

  /**
   * Configura o banco de dados não relacional
   */
  private void configRealm() {
    RealmConfiguration config = new RealmConfiguration.Builder(this)
        .name("ghibli_sample.realm")
        .schemaVersion(1)
        .build();
    Realm.setDefaultConfiguration(config);
  }

  /**
   * Inicializa o componente da aplicação para permitir a
   * injeção de dependência.
   * @return O componente da aplicação.
   */
  private ApplicationComponent initializeDependencyInjector() {
    if(applicationComponent == null) {
      applicationComponent = DaggerApplicationComponent.builder()
          .applicationModule(new ApplicationModule(this))
          .build();
    }
    return applicationComponent;
  }

  public ApplicationComponent component() {
    return applicationComponent;
  }

}
