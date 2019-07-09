/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.songdetailstart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.songdetailstart.content.SongUtils;

import java.util.List;

/**
 * https://codelabs.developers.google.com/codelabs/advanced-android-training-fragment-communication/#3
 * - demonstrates  to use a Fragment to implement a two-pane master/detail layout
 *      for a horizontal tablet display
 * - how to take code from an Activity and encapsulate it within a Fragment
 * - MainActvity has a recycleView contains list of item in view holder (SongUtils.Song), recycleview adaptor
 *  returns a holder that associates item data, event listener and UI view;
 *  event listener of item to trigger Fragment or Fragment in Activity to display UI of song details.
 */
public class MainActivity extends AppCompatActivity {
    private boolean mTwoPane = false;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    /**
     * Sets up a song list as a RecyclerView.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        // only found if the screen wide is large than 900dp
        //  as song_list.xml(w900dp) has this layout defined
        //  song_list.xml is included activity_song_list.xml
        if (findViewById(R.id.song_detail_container) != null) {
            mTwoPane = true;
        }

        // Set the toolbar as the app bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Get the song list as a RecyclerView.
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.song_list);
        // the adaptor binds view for each list item
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter());

    }


    /**
     * The ReyclerView adaptor for the song list.
     */
    class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<SongUtils.Song> mSongItems;

        SimpleItemRecyclerViewAdapter() {
            // feed data
            mSongItems =SongUtils.SONG_ITEMS;
        }

        //called when to create view holder
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //inflated view  is a LinearLayout as defined in song_list_content.xml
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.song_list_content, parent, false);
            //Log.e("test ",view.getClass().toString());
            return new ViewHolder(view);
        }

        //called when to associate the holder to view and data;
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            //the view holder instance is passed in respective to the position of adapter
            //    the position is direct mapping to mSongItems list
            holder.mSong = mSongItems.get(position);
            holder.mSongIdView.setText(String.valueOf(position + 1));
            holder.mSongNameView.setText(mSongItems.get(position).song_title);
            //holder.mView is the LinearLayout defined in song_list_content.xml
            //      add event listen to trigger the fragment instance or activity for the song detail
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        //if bigger screen instants the fragment and then
                        // replace song_detail_container framelayout that defined in song_list.xml(w900dp)
                        int selectedSong = holder.getAdapterPosition();
                        SongDetailFragment fragment =SongDetailFragment.newInstance(selectedSong);
                        //
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.song_detail_container, fragment)
                                .addToBackStack(null)
                                .commit();
                    } else {
                        //if default screen size, calls the activity(new screen)
                        // where the the fragment will be created
                        Context context = v.getContext();
                        Intent intent = new Intent(context, SongDetailActivity.class);
                        intent.putExtra(SongUtils.SONG_ID_KEY,
                                holder.getAdapterPosition());
                        context.startActivity(intent);
                    }
                }
            });

        }

        /**
         * Get the count of song list items.
         * @return
         */
        @Override
        public int getItemCount() {
            return mSongItems.size();
        }


        /**
         * ViewHolder describes an item view and metadata about its place
         * within the RecyclerView.
         */
        class ViewHolder extends RecyclerView.ViewHolder {
            final View mView; //the LinearLayout as defined in song_list_content.xml
            final TextView mSongIdView;
            final TextView mSongNameView;
            SongUtils.Song mSong;

            ViewHolder(View view) {
                super(view);
                mView = view;
                mSongIdView = (TextView) view.findViewById(R.id.song_id);
                mSongNameView = (TextView) view.findViewById(R.id.song_name);
            }
        } // ViewHolder
    } //SimpleItemRecyclerViewAdapter
}
