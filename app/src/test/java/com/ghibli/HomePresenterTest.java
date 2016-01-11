package com.ghibli;

import com.ghibli.domain.interactor.GetMostPopularVideo;
import com.ghibli.domain.interactor.GetMostPopularVideoImpl;
import com.ghibli.domain.model.Video;
import com.ghibli.executor.InteractorExecutor;
import com.ghibli.executor.MainThread;
import com.ghibli.executor.ThreadExecutor;
import com.ghibli.ui.presenter.HomePresenter;
import com.ghibli.ui.presenter.HomePresenterImpl;
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class HomePresenterTest {

  @Mock HomePresenterImpl.View view;
  @Mock Video video;
  @Mock Video.Snippet snippet;
  @Mock Video.Snippet.Thumbnails thumbnails;
  @Mock Video.Snippet.Thumbnails.Standard standard;
  @Mock ArrayList<Video> videos;

  InteractorExecutor interactorExecutor;
  MainThread mainThread;
  GetMostPopularVideo getMostPopularVideo;
  HomePresenter homePresenter;

  @Before public void setUp() throws Exception {
    mainThread = Mockito.spy(new MainThreadImplTest());
    interactorExecutor = Mockito.spy(new ThreadExecutor());
    getMostPopularVideo = Mockito.spy(new GetMostPopularVideoImpl(interactorExecutor, mainThread));
    homePresenter = new HomePresenterImpl(getMostPopularVideo);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSetViewWithNullThenExpectedIllegalArgumentException() {
    homePresenter.setView(null);
  }

  @Test public void whenSetViewThenVerifyZeroInteractionsOnView() {
    homePresenter.setView(view);
    Mockito.verifyZeroInteractions(view);
  }

  @Test public void givenViewReadyAndGenerateVideoObjectThenAssertRequestReturn() {
    Mockito.when(view.isReady()).thenReturn(true);

    Mockito.when(video.getId()).thenReturn("d3AajDTThww");

    Mockito.when(snippet.getTitle()).thenReturn("some title");
    Mockito.when(snippet.getDescription()).thenReturn("video description");
    Mockito.when(video.getSnippet()).thenReturn(snippet);

    Mockito.when(snippet.getThumbnails()).thenReturn(thumbnails);
    Mockito.when(thumbnails.getStandard()).thenReturn(standard);
    Mockito.when(standard.getUrl()).thenReturn("some image");

    Mockito.when(videos.get(0)).thenReturn(video);

    Mockito.doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {

        ArrayList<Video> videosInner = new ArrayList<>();

        Video videoInner = new Video();

        Assert.assertNotNull(videoInner);
        Assert.assertNull(videoInner.getId());
        Assert.assertNotNull(videoInner.getSnippet());
        videoInner.setId("d3AajDTThww");
        Assert.assertNotNull(videoInner.getId());

        Video.Snippet snippetInner = new Video.Snippet();
        Assert.assertNotNull(snippetInner);
        Assert.assertNull(snippetInner.getTitle());
        Assert.assertNull(snippetInner.getDescription());
        snippetInner.setTitle("some title");
        snippetInner.setDescription("video description");
        Assert.assertNotNull(snippetInner.getTitle());
        Assert.assertNotNull(snippetInner.getDescription());

        Video.Snippet.Thumbnails thumbnailsInner = new Video.Snippet.Thumbnails();
        Assert.assertNotNull(thumbnailsInner);
        Assert.assertNotNull(thumbnailsInner.getStandard());

        Video.Snippet.Thumbnails.Standard standardInner = new Video.Snippet.Thumbnails.Standard();
        Assert.assertNotNull(standardInner);
        Assert.assertNull(standardInner.getUrl());
        standardInner.setUrl("some image");
        Assert.assertNotNull(standardInner.getUrl());

        thumbnailsInner.setStandard(standardInner);
        Assert.assertNotNull(thumbnailsInner.getStandard());

        snippetInner.setThumbnails(thumbnailsInner);
        Assert.assertNotNull(snippetInner.getThumbnails());

        videoInner.setSnippet(snippetInner);
        Assert.assertNotNull(videoInner.getSnippet());

        videosInner.add(videoInner);

        Object[] args = invocation.getArguments();
        GetMostPopularVideo.Callback callback = (GetMostPopularVideo.Callback) args[0];
        callback.onSuccess(videosInner);
        return null;
      }
    }).when(getMostPopularVideo).execute(Mockito.any(GetMostPopularVideo.Callback.class));

    homePresenter.setView(view);
    homePresenter.initialize();

  }

  @Test public void givenViewReadyAndReturnEmpty() {
    Mockito.when(view.isReady()).thenReturn(true);

    Mockito.doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        Object[] args = invocation.getArguments();
        GetMostPopularVideo.Callback callback = (GetMostPopularVideo.Callback) args[0];
        callback.onEmpty();
        return null;
      }
    }).when(getMostPopularVideo).execute(Mockito.any(GetMostPopularVideo.Callback.class));

    homePresenter.setView(view);
    homePresenter.initialize();

    Mockito.verify(view).onEmpty();

  }

  @Test public void givenViewReadyAndReturnError() {
    Mockito.when(view.isReady()).thenReturn(true);

    Mockito.doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        Object[] args = invocation.getArguments();
        GetMostPopularVideo.Callback callback = (GetMostPopularVideo.Callback) args[0];
        callback.onError();
        return null;
      }
    }).when(getMostPopularVideo).execute(Mockito.any(GetMostPopularVideo.Callback.class));

    homePresenter.setView(view);
    homePresenter.initialize();

    Mockito.verify(view).onError();

  }

}
