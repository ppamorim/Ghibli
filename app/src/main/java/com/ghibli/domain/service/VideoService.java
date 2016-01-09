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
package com.ghibli.domain.service;

import com.ghibli.GhibliApplication;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class VideoService {

  OkHttpClient client = new OkHttpClient();

  public String searchUser(String userName) throws IOException {
    String endPoint = new StringBuilder(GhibliApplication.API)
        .append("videos?part=snippet&chart=mostPopular&regionCode=br&videoCategoryId=17&key=")
        .append(GhibliApplication.YOUTUBE_KEY)
        .toString();
    Request request = new Request.Builder().url(endPoint).build();
    return client.newCall(request).execute().body().string();
  }

}
