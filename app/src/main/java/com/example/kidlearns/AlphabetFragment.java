package com.example.kidlearns;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlphabetFragment extends Fragment {
    /**
     * Handles playback of all the sound files
     */
    private MediaPlayer mMediaPlayer;

    /**
     * Handles audio focus when playing a sound file
     */
    private AudioManager mAudioManager;

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private final AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private final MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    public AlphabetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word(R.string.alphabet_a,R.drawable.alphabet_a, R.raw.a));
        words.add(new Word(R.string.alphabet_b,R.drawable.alphabet_b, R.raw.b));
        words.add(new Word(R.string.alphabet_c,R.drawable.alphabet_c, R.raw.c));
        words.add(new Word(R.string.alphabet_d,R.drawable.alphabet_d, R.raw.d));
        words.add(new Word(R.string.alphabet_e,R.drawable.alphabet_e, R.raw.e));
        words.add(new Word(R.string.alphabet_f,R.drawable.alphabet_f, R.raw.f));
        words.add(new Word(R.string.alphabet_g,R.drawable.alphabet_g, R.raw.g));
        words.add(new Word(R.string.alphabet_h,R.drawable.alphabet_h, R.raw.h));
        words.add(new Word(R.string.alphabet_i,R.drawable.alphabet_i, R.raw.i));
        words.add(new Word(R.string.alphabet_j,R.drawable.alphabet_j, R.raw.j));
        words.add(new Word(R.string.alphabet_k,R.drawable.alphabet_k, R.raw.k));
        words.add(new Word(R.string.alphabet_l,R.drawable.alphabet_l, R.raw.l));
        words.add(new Word(R.string.alphabet_m,R.drawable.alphabet_m, R.raw.m));
        words.add(new Word(R.string.alphabet_n,R.drawable.alphabet_n, R.raw.n));
        words.add(new Word(R.string.alphabet_o,R.drawable.alphabet_o, R.raw.o));
        words.add(new Word(R.string.alphabet_p,R.drawable.alphabet_p, R.raw.p));
        words.add(new Word(R.string.alphabet_q,R.drawable.alphabet_q, R.raw.q));
        words.add(new Word(R.string.alphabet_r,R.drawable.alphabet_r, R.raw.r));
        words.add(new Word(R.string.alphabet_s,R.drawable.alphabet_s, R.raw.s));
        words.add(new Word(R.string.alphabet_t,R.drawable.alphabet_t, R.raw.t));
        words.add(new Word(R.string.alphabet_u,R.drawable.alphabet_u, R.raw.u));
        words.add(new Word(R.string.alphabet_v,R.drawable.alphabet_v, R.raw.v));
        words.add(new Word(R.string.alphabet_w,R.drawable.alphabet_w, R.raw.w));
        words.add(new Word(R.string.alphabet_x,R.drawable.alphabet_x, R.raw.x));
        words.add(new Word(R.string.alphabet_y,R.drawable.alphabet_y, R.raw.y));
        words.add(new Word(R.string.alphabet_z,R.drawable.alphabet_z, R.raw.z));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_alphabet);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        GridView listView = (GridView) rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        // Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                // Get the {@link Word} object at the given position the user clicked on
                Word word = words.get(position);

                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());

                    // Start the audio file
                    mMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();

        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}