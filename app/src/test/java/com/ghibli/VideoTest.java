package com.ghibli;

import com.ghibli.domain.model.Video;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VideoTest {

  @Test public void testVideo() {

    Video video = new Video();

    Assert.assertNotNull(video);
    Assert.assertNull(video.getId());
    Assert.assertNotNull(video.getSnippet());

    video.setId("x2etjgFdhCw");

    Assert.assertNotNull(video);
    Assert.assertNotNull(video.getId());

    Assert.assertEquals(video.getId(), "x2etjgFdhCw");

    Video.Snippet snippet = new Video.Snippet();

    Assert.assertNotNull(snippet);
    Assert.assertNull(snippet.getTitle());
    Assert.assertNull(snippet.getDescription());
    Assert.assertNotNull(snippet.getThumbnails());

    snippet.setTitle("some video");
    snippet.setDescription("some description");

    Video.Snippet.Thumbnails thumbnails = new Video.Snippet.Thumbnails();
    Assert.assertNotNull(thumbnails);
    Assert.assertNotNull(thumbnails.getStandard());

    Video.Snippet.Thumbnails.Standard standard = new Video.Snippet.Thumbnails.Standard();
    Assert.assertNotNull(standard);
    Assert.assertNull(standard.getUrl());

    standard.setUrl("some image");

    Assert.assertNotNull(standard.getUrl());

    thumbnails.setStandard(standard);
    Assert.assertNotNull(thumbnails.getStandard());

    snippet.setThumbnails(thumbnails);
    Assert.assertNotNull(snippet.getThumbnails());

    video.setSnippet(snippet);

    Assert.assertNotNull(video.getSnippet());
    Assert.assertNotNull(video.getSnippet().getTitle());
    Assert.assertNotNull(video.getSnippet().getThumbnails());
    Assert.assertNotNull(video.getSnippet().getThumbnails().getStandard());
    Assert.assertNotNull(video.getSnippet().getThumbnails().getStandard().getUrl());

  }

}
