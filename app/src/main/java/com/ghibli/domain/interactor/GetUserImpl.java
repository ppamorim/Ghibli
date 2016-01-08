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

import com.ghibli.domain.binder.UserBinder;
import com.ghibli.domain.model.User;
import com.ghibli.domain.service.UserService;
import com.ghibli.executor.Interactor;
import com.ghibli.executor.InteractorExecutor;
import com.ghibli.executor.MainThread;
import com.ghibli.util.DebugUtil;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class GetUserImpl implements Interactor, GetUser {

  private final InteractorExecutor interactorExecutor;
  private final MainThread mainThread;
  private Callback callback;

  @Inject GetUserImpl(InteractorExecutor interactorExecutor, MainThread mainThread) {
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
      InputStream result = new UserService().searchUser(callback.getFilter());
      List<User> users = UserBinder.getUserArray(result);
      DebugUtil.log("users> " + users.size());
      if(users != null) {
        onUserLoaded((ArrayList<User>) users);
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

  private void onUserLoaded(final ArrayList<User> users) {
    mainThread.post(new Runnable() {
      @Override public void run() {
        callback.onSuccess(users);
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

