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
