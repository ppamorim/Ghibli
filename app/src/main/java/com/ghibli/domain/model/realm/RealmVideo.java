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
package com.ghibli.domain.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Esta classe, uma cópia da classe Video, representa
 * a classe que será utilizada no Realm para salvar
 * os objetos.
 */
public class RealmVideo extends RealmObject {

  @PrimaryKey private String id;
  private String title;
  private String description;
  private String standardImageUrl;

  public RealmVideo() {
    super();
  }

  public RealmVideo(String id, String title, String description, String standardImageUrl) {
    super();
    this.id = id;
    this.title = title;
    this.description = description;
    this.standardImageUrl = standardImageUrl;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getStandardImageUrl() {
    return standardImageUrl;
  }

  public void setStandardImageUrl(String standardImageUrl) {
    this.standardImageUrl = standardImageUrl;
  }

}
