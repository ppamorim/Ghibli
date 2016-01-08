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
package com.ghibli.domain.binder;

import com.bluelinelabs.logansquare.LoganSquare;
import com.ghibli.domain.model.User;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserBinder {

  public static List<User> getUserArray(InputStream inputStream) throws IOException {
    return LoganSquare.parseList(inputStream, User.class);
  }

}
