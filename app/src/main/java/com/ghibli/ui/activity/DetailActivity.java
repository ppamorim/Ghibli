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
package com.ghibli.ui.activity;

import android.os.Bundle;
import butterknife.Bind;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ghibli.R;
import com.ghibli.domain.model.Video;
import com.ghibli.util.ImageUtil;

public class DetailActivity extends AbstractActivity {

  @Bind(R.id.video_image) SimpleDraweeView videoImage;

  @Override protected int getContentViewId() {
    return R.layout.activity_detail;
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    Video video = getIntent().getParcelableExtra(Video.TAG);
    ImageUtil.loadImage(videoImage, video.getSnippet().getThumbnails().getStandard().getUrl());
  }
}
