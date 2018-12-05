package com.track.brachio.donationtracker.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.track.brachio.donationtracker.R;
import com.track.brachio.donationtracker.model.Address;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.User;

import java.util.List;

public class UserListAdapter extends
        RecyclerView.Adapter<UserListAdapter.UserViewHolder>{

    private final List<User> users;

    private final UserListAdapter.OnItemClickListener userListener;
    /**
     * location list adapter
     * @param array array being displayed
     * @param listener listener for recycler view
     */
    public UserListAdapter(List<User> array, UserListAdapter.OnItemClickListener listener) {
        users = array;
        userListener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_user_list, parent, false);

        return new UserListAdapter.UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int position) {
        User user  = users.get(position);
        userViewHolder.fnText.setText(user.getFirstName());
        userViewHolder.lnText.setText(user.getLastName()+",");
        userViewHolder.emailText.setText(user.getEmail());
        if (user.getUserType() != null){
            userViewHolder.typeText.setText(user.getUserType().name());
        }
        userViewHolder.statusText.setText("True");
        userViewHolder.bind(user, userListener);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    /**
     * listener for clicking of list item
     */
    public interface OnItemClickListener {
        /**
         * abstract listener for onClick
         * @param item item being clicked
         */
        void onItemClick(User item);
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private final TextView fnText;
        private final TextView lnText;
        private final TextView emailText;
        private final TextView typeText;
        private final TextView statusText;
        private final View v;

        /**
         * constructor for LocationViewHolder
         * @param v current view
         */
        UserViewHolder(View v) {
            super(v);
            this.v = v;
            fnText = v.findViewById(R.id.fnText);
            lnText = v.findViewById(R.id.lnText);
            emailText = v.findViewById(R.id.emailText);
            typeText = v.findViewById(R.id.typeText);
            statusText = v.findViewById(R.id.statusText);

        }

        /**
         * binds
         * @param item current item being bound
         * @param listener current listener
         */
        private void bind(final User item, final UserListAdapter.OnItemClickListener listener) {
            v.setOnClickListener(v -> {
                Log.d("UserListActivity", "To Manage");
                listener.onItemClick(item);
            });
        }
    }


}


