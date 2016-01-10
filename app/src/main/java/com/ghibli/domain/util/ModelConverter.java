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
package com.ghibli.domain.util;

import com.ghibli.domain.model.Video;
import com.ghibli.domain.model.realm.RealmVideo;
import io.realm.RealmResults;
import java.util.ArrayList;

/**
 * Esta classe é utilizada para converter os objetos
 * vindos do servidor para a versão do objeto para o
 * Realm. Como os objetos do Realm são thread-safe,
 * é necessário realizar essa conversão pois não é
 * possível passar esses dados de forma fácil para a
 * main thread.
 */
public class ModelConverter {

  /**
   * Converte os objetos do tipo Video para RealmVideo
   * @param videos Lista contendo objetos do tipo Video
   * @return Lista contendo objetos do tipo RealmVideo
   */
  public static ArrayList<RealmVideo> convertVideoToRealmVideo(ArrayList<Video> videos) {
    ArrayList<RealmVideo> realmVideos = new ArrayList<>(videos.size());
    for(Video video : videos) {
      Video.Snippet snippet = video.getSnippet();
      realmVideos.add(
          new RealmVideo(video.getId(),
          snippet.getTitle(),
          snippet.getDescription(),
          snippet.getThumbnails().getStandard().getUrl()));
    }
    return realmVideos;
  }

  /**
   * Converte os objetos do tipo RealmVideo para Video
   * @param realmVideos Lista contendo objetos do tipo RealmVideo
   * @return Lista contendo objetos do tipo Video
   */
  public static ArrayList<Video> convertRealmVideoToVideo(RealmResults<RealmVideo> realmVideos) {
    ArrayList<Video> videos = new ArrayList<>(realmVideos.size());
    for(RealmVideo realmVideo : realmVideos) {
      Video video = new Video();
      video.setId(realmVideo.getId());
      video.getSnippet().setTitle(realmVideo.getTitle());
      video.getSnippet().setDescription(realmVideo.getDescription());
      video.getSnippet().getThumbnails().getStandard().setUrl(realmVideo.getStandardImageUrl());
      videos.add(video);
    }
    return videos;
  }

}
