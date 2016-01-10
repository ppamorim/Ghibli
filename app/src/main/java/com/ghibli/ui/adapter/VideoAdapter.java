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
package com.ghibli.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ghibli.R;
import com.ghibli.domain.model.Video;
import com.ghibli.util.ImageUtil;
import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

  private ArrayList<Video> videos = null;

  @Override public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
      int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.adapter_video, parent, false));
  }

  /**
   * Realiza o carregamento de dados no item do adapter.
   * @param holder Instância forte do item da lista.
   * @param position Posição do item na lista.
   */
  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    Video.Snippet snippet = videos.get(position).getSnippet();
    if (snippet != null) {
      holder.videoName.setText(snippet.getTitle());
      ImageUtil.loadImage(holder.videoImage,
          snippet.getThumbnails().getStandard().getUrl());
    }
  }

  /**
   * Retorna a contagem dos itens na lista para o RecyclerView.
   * @return contagem dos itens na lista.
   */
  @Override public int getItemCount() {
    return videos.size();
  }

  /**
   * Atualiza a lista, é chamado toda vez que a
   * requisição dos dados é feita com sucesso.
   * @param videos Lista de videos
   */
  public void setVideos(ArrayList<Video> videos) {
    this.videos = videos;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.video_image_adapter) SimpleDraweeView videoImage;
    @Bind(R.id.video_name) TextView videoName;

    public ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
    }
  }

}
