package com.example.kidlearns;

/**
 * Created by manoj on 3/28/2017.
 */

public class Word {

    /*IT will contain the text like a,1,red,humpty dumpty */
    private final int mText;

    /** Audio resource ID for the word */
    private final int mAudioResourceId;

    /** Image resource ID for the word */
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    /** Constant value that represents no image was provided for this word */
    private static final int NO_IMAGE_PROVIDED = -1;

   //creating word object
    public Word( int defaultTextId, int audioResourceId) {
        mText = defaultTextId;
        mAudioResourceId = audioResourceId;
    }

  //creating a new
    public Word(int defaultTextId, int imageResourceId, int audioResourceId) {
        mText = defaultTextId;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Get the string resource ID for the default translation of the word.
     */
    public int getDefaultTranslationId() {
        return mText;
    }


    /**
     * Return the image resource ID of the word.
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    /**
     * Returns whether or not there is an image for this word.
     */
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    /**
     * Return the audio resource ID of the word.
     */
    public int getAudioResourceId() {
        return mAudioResourceId;
    }
}