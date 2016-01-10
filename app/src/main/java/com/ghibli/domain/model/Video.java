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
package com.ghibli.domain.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Este objeto representa cada video do Youtube, nela
 * contemos outras classes estáticas que informam para
 * o processador do JSON(LoganSquare) o formato da classe.
 */
@JsonObject
public class Video implements Parcelable {

  public static final String TAG = "Video";

  @JsonField private String id;
  @JsonField(name = "snippet") private Snippet snippet;

  public static final Parcelable.Creator<Video> CREATOR
      = new Parcelable.Creator<Video>() {
    public Video createFromParcel(Parcel in) {
      return new Video(in);
    }

    public Video[] newArray(int size) {
      return new Video[size];
    }
  };

  public Video() {
    super();
  }

  public Video(Parcel in) {
    setId(in.readString());
    getSnippet().setTitle(in.readString());
    getSnippet().setDescription(in.readString());
    getSnippet().getThumbnails().getStandard().setUrl(in.readString());
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Snippet getSnippet() {
    if(snippet == null) {
      snippet = new Snippet();
    }
    return snippet;
  }

  public void setSnippet(Snippet snippet) {
    this.snippet = snippet;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(getId());
    dest.writeString(getSnippet().getTitle());
    dest.writeString(getSnippet().getDescription());
    dest.writeString(getSnippet().getThumbnails().getStandard().getUrl());
  }

  @JsonObject
  public static class Snippet {

    @JsonField private String title;
    @JsonField private String description;
    @JsonField(name = "thumbnails") Thumbnails thumbnails;

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getDescription() {
      return description;
    }

    public Thumbnails getThumbnails() {
      if(thumbnails == null) {
        thumbnails = new Thumbnails();
      }
      return thumbnails;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    @JsonObject
    public static class Thumbnails {
      @JsonField(name = "standard") private Standard standard;

      @JsonObject
      public static class Standard {
        @JsonField private String url;

        public String getUrl() {
          return url;
        }

        public void setUrl(String url) {
          this.url = url;
        }
      }

      public Standard getStandard() {
        if(standard == null) {
          standard = new Standard();
        }
        return standard;
      }

      public void setStandard(Standard standard) {
        this.standard = standard;
      }
    }

  }

}
