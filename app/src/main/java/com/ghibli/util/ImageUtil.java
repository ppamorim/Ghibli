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
package com.ghibli.util;

import android.net.Uri;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class ImageUtil {

  public static void loadImage(SimpleDraweeView simpleDraweeView, String url) {
    ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
        .setImageType(ImageRequest.ImageType.SMALL)
        .build();
    PipelineDraweeControllerBuilder pipelineDraweeControllerBuilder =
        Fresco.newDraweeControllerBuilder();
    pipelineDraweeControllerBuilder.setImageRequest(imageRequest);
    simpleDraweeView.setController(pipelineDraweeControllerBuilder
        .setOldController(simpleDraweeView.getController())
        .setAutoPlayAnimations(true)
        .build());
  }

}
