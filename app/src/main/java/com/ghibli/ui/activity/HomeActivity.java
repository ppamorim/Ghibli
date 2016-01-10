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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import com.ghibli.GhibliApplication;
import com.ghibli.R;
import com.ghibli.di.ActivityModule;
import com.ghibli.di.HomeActivityModule;
import com.ghibli.di.components.DaggerHomeActivityComponent;
import com.ghibli.di.components.HomeActivityComponent;
import com.ghibli.domain.model.Video;
import com.ghibli.ui.adapter.VideoAdapter;
import com.ghibli.ui.listener.RecyclerItemClickListener;
import com.ghibli.ui.presenter.HomePresenter;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * Principal atividade da aplicação, nela contém uma Toolbar
 * e uma lista simples (RecyclerView), além de conter uma
 * view de loading e outra de erro.
 */
public class HomeActivity extends AbstractActivity implements HomePresenter.View,
    RecyclerItemClickListener.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

  private HomeActivityComponent homeActivityComponent;

  @Inject HomePresenter homePresenter;

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;
  @Bind(R.id.recycler_view) RecyclerView recyclerView;
  @Bind(R.id.loading_view) ProgressBar progressBar;
  @Bind(R.id.error_view) TextView errorView;

  @Override protected int getContentViewId() {
    return R.layout.activity_home;
  }

  /**
   * Cria o componente e injeta os módulos na activity.
   * Configura o presenter e seus callbacks são inicializados.
   * @param savedInstanceState Salvamento de instância
   */
  @Override protected void onCreate(Bundle savedInstanceState) {
    homeActivityComponent().inject(this);
    super.onCreate(savedInstanceState);
    homePresenter.restoreInstance(savedInstanceState);
    homePresenter.setView(this);
  }

  /**
   * Configura as views e inicializa a requisção dos videos.
   * @param savedInstanceState
   */
  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    configToolbar();
    configRecyclerView();
    refreshMostPopular();
  }

  /**
   * Ao finalizar a activity, invalida os dados no presenter.
   */
  @Override protected void onDestroy() {
    super.onDestroy();
    homePresenter.destroy();
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(homePresenter.saveInstance(outState));
  }

  /**
   * Retorna se a activity ainda está presente.
   * @return Verifica se a activity está finilizada
   */
  @Override public boolean isReady() {
    return !isFinishing();
  }

  /**
   * Finaliza o refresh do SwipeRefreshLayout, adiciona os
   * itens no adapter e mostra a RecyclerView.
   * @param video
   */
  @Override public void renderPopularVideo(ArrayList<Video> video) {
    stopRefresh();
    ((VideoAdapter)recyclerView.getAdapter()).setVideos(video);
    showRecyclerView();
  }

  @Override public void onEmpty() {
    stopRefresh();
    showErrorView(R.string.request_no_item);
  }

  @Override public void onError() {
    stopRefresh();
    showErrorView(R.string.request_error);
  }

  /**
   * Carrega o item de determinada posição da lista numa nova activity.
   * @param view View do item clicado.
   * @param position Posição do item na lista.
   */
  @Override public void onItemClick(View view, int position) {
    showUserDetails(homePresenter.getItem(position));
  }

  @Override public void onRefresh() {
    homePresenter.initialize();
  }

  /**
   * Realiza o carregamento da listagem de vídeos
   * populares caso detecte que a lista está vazia.
   */
  private void refreshMostPopular() {
    if (homePresenter.isMostPopularEmpty()) {
      showProgressBar();
      homePresenter.initialize();
    }
  }

  /**
   * Exibe a lista após carregar os itens.
   */
  private void showRecyclerView() {
    swipeRefreshLayout.setVisibility(View.VISIBLE);
    progressBar.setVisibility(View.GONE);
    errorView.setVisibility(View.GONE);
  }

  /**
   * Exibe o progressBar, somente é exibido quando o aplicativo
   * abre e carrega os dados pela primeira vez.
   */
  private void showProgressBar() {
    swipeRefreshLayout.setVisibility(View.GONE);
    progressBar.setVisibility(View.VISIBLE);
    errorView.setVisibility(View.GONE);
  }

  /**
   * Mostra a tela de erro, podendo ser erro com a requisição ou
   * lista vazia.
   * @param errorId
   */
  private void showErrorView(int errorId) {
    swipeRefreshLayout.setVisibility(View.GONE);
    progressBar.setVisibility(View.GONE);
    errorView.setVisibility(View.VISIBLE);
    errorView.setText(errorId);
  }

  /**
   * Remove o icone de refresh do SwipeRefreshLayout.
   */
  private void stopRefresh() {
    swipeRefreshLayout.setRefreshing(false);
  }

  /**
   * Declara a toolbar no layout como ActionBar da activity,
   * após, determina que ela possui o titulo do app aparente.
   */
  private void configToolbar() {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setHomeButtonEnabled(true);
    }
  }

  /**
   * Configura o listener da SwipeRefreshLayout,
   * o LayoutManager, Adapter e TouchListener
   * da RecyclerView.
   */
  private void configRecyclerView() {
    swipeRefreshLayout.setOnRefreshListener(this);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(new VideoAdapter());
    recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, this));
  }

  /**
   * Inicia a activity de detalhes do vídeo, passando
   * o objeto video como parâmetro.
   * @param video Item clicado na lista
   */
  private void showUserDetails(Video video) {
    Intent intent = new Intent(this, DetailActivity.class);
    intent.putExtra(Video.TAG, video);
    startActivity(intent);
  }

  private HomeActivityComponent homeActivityComponent() {
    if (homeActivityComponent == null) {
      homeActivityComponent = DaggerHomeActivityComponent.builder()
          .applicationComponent(((GhibliApplication) getApplication()).component())
          .activityModule(new ActivityModule(this))
          .homeActivityModule(new HomeActivityModule())
          .build();
    }
    return homeActivityComponent;
  }

}
