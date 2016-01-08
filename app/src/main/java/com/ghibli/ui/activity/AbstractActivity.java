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

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import butterknife.ButterKnife;

/**
 * Esta classe abstrata infla o layout e aplica o binder do ButterKnife,
 * assim ajudando na criação das Activities da aplicação.
 */
public abstract class AbstractActivity extends AppCompatActivity {

  /**
   * Infla o layout da view e usa o ButterKnife para aplicar o binder.
   * @param savedInstanceState Salvamento de instância
   */
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getContentViewId());
    ButterKnife.bind(this);
  }

  /**
   * Esconde o teclado caso esteja aberto.
   */
  public void hideKeyboard() {
    View view = getCurrentFocus();
    if (view != null) {
      ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
          hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
  }

  /**
   * @return Referência do layout da Activity nos resources.
   */
  protected abstract int getContentViewId();

}