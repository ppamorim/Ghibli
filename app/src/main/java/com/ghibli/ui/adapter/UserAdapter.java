package com.ghibli.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.ghibli.R;
import com.ghibli.domain.model.User;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

  private ArrayList<User> users = null;

  @Override public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
      int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.adapter_user, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.userName.setText(users.get(position).getUserName());
  }

  @Override public int getItemCount() {
    return users.size();
  }

  public void setUsers(ArrayList<User> users) {
    this.users = users;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.user_name) TextView userName;

    public ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
    }
  }

}
