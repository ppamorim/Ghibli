package com.ghibli;

import com.ghibli.domain.interactor.GetMostPopularVideo;
import com.ghibli.domain.interactor.GetMostPopularVideoImpl;
import com.ghibli.domain.model.Video;
import com.ghibli.executor.InteractorExecutor;
import com.ghibli.executor.MainThread;
import com.ghibli.executor.ThreadExecutor;
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
public class GetMostPopularVideoTest {

  InteractorExecutor interactorExecutor;
  MainThread mainThread;

  GetMostPopularVideo getMostPopularVideo;

  @Mock ArrayList<Video> videos;
  @Mock Video video;
  @Mock Video.Snippet snippet;
  @Mock Video.Snippet.Thumbnails thumbnails;
  @Mock Video.Snippet.Thumbnails.Standard standard;
  @Mock GetMostPopularVideo.Callback callback;

  @Before public void setUp() throws Exception {
    mainThread = Mockito.spy(new MainThreadImplTest());
    interactorExecutor = Mockito.spy(new ThreadExecutor());
    getMostPopularVideo = Mockito.spy(new GetMostPopularVideoImpl(interactorExecutor, mainThread));
  }

  @Test public void validateVideoValidator() {

    Video video = new Video();
    video.setId("d3AajDTThww");

    Video.Snippet snippet = new Video.Snippet();
    snippet.setDescription("video test");
    snippet.setTitle("some test");

    Video.Snippet.Thumbnails thumbnails = new Video.Snippet.Thumbnails();
    Video.Snippet.Thumbnails.Standard standard = new Video.Snippet.Thumbnails.Standard();
    standard.setUrl("some image");
    thumbnails.setStandard(standard);
    snippet.setThumbnails(thumbnails);
    video.setSnippet(snippet);

    Assert.assertNotNull(video);
    Assert.assertNotNull(video.getSnippet());
    Assert.assertNotNull(video.getSnippet().getThumbnails());
    Assert.assertNotNull(video.getSnippet().getThumbnails().getStandard());

  }

  @Test(expected = IllegalArgumentException.class)
  public void whenExecuteWithNullThenExpectedIllegalArgumentException() {
    getMostPopularVideo.execute(null);
  }

  @Test public void givenViewReadyAndRequestMostPopularVideosInitializeThenVerifyView() {

    Mockito.when(video.getId()).thenReturn("x2etjgFdhCw");

    Mockito.when(snippet.getTitle()).thenReturn("Corrida");
    Mockito.when(snippet.getDescription()).thenReturn("Corrida maluca");
    Mockito.when(video.getSnippet()).thenReturn(snippet);

    Mockito.when(snippet.getThumbnails()).thenReturn(thumbnails);
    Mockito.when(thumbnails.getStandard()).thenReturn(standard);
    Mockito.when(standard.getUrl()).thenReturn("some image");

    Mockito.when(videos.get(0)).thenReturn(video);

    Mockito.doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        Object[] args = invocation.getArguments();
        GetMostPopularVideo.Callback callback = (GetMostPopularVideo.Callback) args[0];
        callback.onSuccess(videos);
        return null;
      }
    }).when(getMostPopularVideo).execute(Mockito.any(GetMostPopularVideo.Callback.class));

    getMostPopularVideo.execute(callback);
    Mockito.verify(callback).onSuccess(videos);
  }

}