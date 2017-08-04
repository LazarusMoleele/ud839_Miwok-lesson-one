/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    /**Handles playback of all the sound files*/
    private MediaPlayer mMediaPlayer;

    /**Handles audio focus when playing a sound file*/
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener(){

                public  void  onAudioFocusChange(int focusChange){
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        //The AUDIOFOCUS_LOSS_TRANSIENT case means that we`ve lost audio focus
                        //short amount of time.The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means
                        //our app is allowed tp continue playing sound but at a lower volume. Where
                        //both cases that same way because our app is playing short sound files

                        //Pause playback and reset player to the start of the file. that way, we
                        //play the word from the beginning when we resume playback.
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    }else  if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                        //The AUDIOFOCUS_GAIN case means we have regained focus and can
                        // resume playback
                        mMediaPlayer.start();
                    }else  if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                        //The AUDIO_LOSS case means we`ve lost audio focus and
                        //stop playback and clean up resources
                        releaseMediaPlayer();
                    }
                }
            };

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * player the audio file.
     */
    private  MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        //Create an ArrayList of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?","tinne oyaase'ne",R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset",R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?","michekses",R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?","hee' eenem",R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I'm coming.","eenem",R.raw.phrase_yes_im_coming));
        words.add(new Word("Let's go.","yoowutis",R.raw.phrase_lets_go));
        words.add(new Word("Come here.","enni'nem",R.raw.phrase_where_are_you_going));


        //adapter knows to create layouts for each item in the list, using the
        //simple_list_item_1.xml layout resource defined in the Android framework.
        //this list item layout contains a single {@link TextView}, which the adapter will set the
        //display a single word.
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);


        //Find the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        //There should be a {@ ListView} with the view ID called list, which is declared in
        //word_listyout file.
        ListView listView = (ListView) findViewById(R.id.list);

        //{@link ListView} will display list items for each word in thw list of words.
        //Do this by calling the setAdapter method on the {@link ListView} object and pass in
        //1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void  onItemClick(AdapterView<?> parent, View view, int position, long id){
                //Get the {@link Word} object at the given position the user clicked on
                Word word = words.get(position);

                //Release the media player if it currently exists because we are about to
                //play a different sound file.
                releaseMediaPlayer();

                //Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        //Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        //Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //We have audio focus now.


                    //Create and setup the {@link MediaPlayer} for the audio resource associated
                    //with the current word
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudioResourceId());

                    //Start the audio file
                    mMediaPlayer.start();


                    //Setup a listener on the media player, so that we can stop and release the
                    //media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        //When the activity is stopped, release the media palyer resources because we wont
        //be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     *Clean up the media player by releasing its resources.
     */
    private void  releaseMediaPlayer(){
        //If the media player is not null, the it may be currently playing a sound.
        if (mMediaPlayer !=null){
            //Regardless of the current state of the media player, release its resource
            //because we no longer need it.
            mMediaPlayer.release();

            //Set the media player back to null. For our code, we`ve decided that
            //setting the media player to null is an easy way to tell that the media player
            //is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            //Regardless of whether or not we were granted audio focus, abandon it. This also
            //unregistered the AudioChangeListener so we don`t get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
