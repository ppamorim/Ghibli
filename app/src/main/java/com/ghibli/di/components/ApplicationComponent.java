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

import android.app.Application;
import com.ghibli.GhibliApplication;
import com.ghibli.di.ApplicationModule;
import com.ghibli.executor.InteractorExecutor;
import com.ghibli.executor.MainThread;
import dagger.Component;
import javax.inject.Singleton;

/**
 * ApplicationComponent is the top level component for this architecture. It provides generic
 * dependencies ic_like {@link InteractorExecutor} or {@link MainThread} and exposes them to
 * subcomponents and to external dependant classes.
 *
 * Scope {@link Singleton} is used to limit dependency instances across whole execution.
 *
 * @author Pedro Paulo de Amorim
 */
@Singleton @Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  void inject(GhibliApplication cameriteApplication);
  Application application();
  InteractorExecutor executor();
  MainThread mainThread();
}
