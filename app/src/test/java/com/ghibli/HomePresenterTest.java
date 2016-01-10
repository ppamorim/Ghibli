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

  @Test public void
  givenViewReadyAndUserWithEmailAndNameWhenInitializeThenVerifyViewOnSendEmailSuccess() {
    Mockito.when(view.isReady()).thenReturn(true);

    Mockito.when(video.getId()).thenReturn("d3AajDTThww");

    Mockito.when(snippet.getTitle()).thenReturn("some test");
    Mockito.when(snippet.getDescription()).thenReturn("video test");
    Mockito.when(video.getSnippet()).thenReturn(snippet);

    Mockito.when(snippet.getThumbnails()).thenReturn(thumbnails);
    Mockito.when(thumbnails.getStandard()).thenReturn(standard);
    Mockito.when(standard.getUrl()).thenReturn("some image");

    Mockito.when(videos.get(0)).thenReturn(video);

    Mockito.doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {

        ArrayList<Video> videos = new ArrayList<>();

        Video video = new Video();

        Assert.assertNotNull(video);
        Assert.assertNull(video.getId());
        Assert.assertNull(video.getSnippet());
        video.setId("d3AajDTThww");
        Assert.assertNotNull(video.getId());

        Video.Snippet snippet = new Video.Snippet();
        Assert.assertNotNull(snippet);
        Assert.assertNull(snippet.getTitle());
        Assert.assertNull(snippet.getDescription());
        snippet.setTitle("some test");
        snippet.setDescription("video test");
        Assert.assertNotNull(snippet.getTitle());
        Assert.assertNotNull(snippet.getDescription());

        Video.Snippet.Thumbnails thumbnails = new Video.Snippet.Thumbnails();
        Assert.assertNotNull(thumbnails);
        Assert.assertNull(thumbnails.getStandard());

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

        videos.add(video);

        Object[] args = invocation.getArguments();
        GetMostPopularVideo.Callback callback = (GetMostPopularVideo.Callback) args[0];
        callback.onSuccess(videos);
        return null;
      }
    }).when(getMostPopularVideo).execute(Mockito.any(GetMostPopularVideo.Callback.class));

    //homePresenter.setView(view);
    //homePresenter.initialize();
    //
    //Mockito.verify(view).renderPopularVideo(videos);

  }

}
