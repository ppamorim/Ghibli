package com.ghibli;

import android.os.Bundle;
import com.ghibli.domain.model.Video;
import com.ghibli.ui.presenter.DetailPresenter;
import com.ghibli.ui.presenter.DetailPresenterImpl;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DetailPresenterTest {

  @Mock Bundle instance;

  @Mock DetailPresenterImpl.View view;
  @Mock Video video;
  @Mock Video.Snippet snippet;
  @Mock Video.Snippet.Thumbnails thumbnails;
  @Mock Video.Snippet.Thumbnails.Standard standard;

  DetailPresenter detailPresenter;

  @Before public void setUp() throws Exception {
    detailPresenter = new DetailPresenterImpl();
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSetViewWithNullThenExpectedIllegalArgumentException() {
    detailPresenter.setView(null);
  }

  @Test public void whenSetViewThenVerifyZeroInteractionsOnView() {
    detailPresenter.setView(view);
    Mockito.verifyZeroInteractions(view);
  }

  @Test public void whenActivityStateChangeSaveInstanceOfVideo() {

    Video innerVideo = new Video();

    Assert.assertNotNull(innerVideo);
    Assert.assertNull(innerVideo.getId());
    Assert.assertNotNull(innerVideo.getSnippet());
    innerVideo.setId("d3AajDTThww");
    Assert.assertNotNull(innerVideo.getId());

    Video.Snippet innerSnippet = new Video.Snippet();
    Assert.assertNotNull(innerSnippet);
    Assert.assertNull(innerSnippet.getTitle());
    Assert.assertNull(innerSnippet.getDescription());
    innerSnippet.setTitle("some test");
    innerSnippet.setDescription("video test");
    Assert.assertNotNull(innerSnippet.getTitle());
    Assert.assertNotNull(innerSnippet.getDescription());

    Video.Snippet.Thumbnails innerThumbnails = new Video.Snippet.Thumbnails();
    Assert.assertNotNull(innerThumbnails);
    Assert.assertNotNull(innerThumbnails.getStandard());

    Video.Snippet.Thumbnails.Standard innerStandard = new Video.Snippet.Thumbnails.Standard();
    Assert.assertNotNull(innerStandard);
    Assert.assertNull(innerStandard.getUrl());
    innerStandard.setUrl("some image");
    Assert.assertNotNull(innerStandard.getUrl());

    innerThumbnails.setStandard(innerStandard);
    Assert.assertNotNull(innerThumbnails.getStandard());

    innerSnippet.setThumbnails(innerThumbnails);
    Assert.assertNotNull(innerSnippet.getThumbnails());

    innerVideo.setSnippet(innerSnippet);
    Assert.assertNotNull(innerVideo.getSnippet());

    detailPresenter.setVideo(innerVideo);
    instance = detailPresenter.saveInstance(instance);

    Mockito.when(video.getId()).thenReturn("d3AajDTThww");

    Mockito.when(snippet.getTitle()).thenReturn("some test");
    Mockito.when(snippet.getDescription()).thenReturn("video test");
    Mockito.when(video.getSnippet()).thenReturn(snippet);

    Mockito.when(snippet.getThumbnails()).thenReturn(thumbnails);
    Mockito.when(thumbnails.getStandard()).thenReturn(standard);
    Mockito.when(standard.getUrl()).thenReturn("some image");

    Assert.assertNotNull(instance);

    detailPresenter.restoreInstance(instance);
    Video videoRestored = detailPresenter.getVideo();

    Assert.assertNotNull(videoRestored);

    Assert.assertEquals(this.video.getId(), videoRestored.getId());
    Assert.assertEquals(this.video.getSnippet().getTitle(), videoRestored.getSnippet().getTitle());
    Assert.assertEquals(this.video.getSnippet().getDescription(),
        videoRestored.getSnippet().getDescription());
    Assert.assertEquals(this.video.getSnippet().getThumbnails().getStandard().getUrl(),
        videoRestored.getSnippet().getThumbnails().getStandard().getUrl());

  }

}
