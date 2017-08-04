package com.example.android.miwok;

/**
 * Created by Lazarus Moleele on 2017/07/11.
 */

public class Word {

    /**Default translation for the word*/
    private  String mDefaultTranslation;

    /**Miwok translation for the word*/
    private  String mMiwokTranslation;

    /**Image resource ID for the word*/
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    /**Audio resource ID for the word*/
    private int mAudioResourceId;

    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (Such as English)
     *
     * @param miwokTranslation is the word in the Miwok language
     * @param  audioResourceId is the resource ID for the audio file associated with in this word.
     */
    public Word(String defaultTranslation, String miwokTranslation,int audioResourceId){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Create a new Word object.
     *
     * @param defaultTranslation
     * @param miwokTranslation
     * @param imageResourceId
     * @param audioResourceId
     */
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId,int audioResourceId){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Get the default translation of the word.
     */
    public String getmDefaultTranslation(){
        return mDefaultTranslation;
    }

    /**
     * Get the Miwok translation of the word.
     */
    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }
        /**
         * Get the image resource ID for the word.
         */
        public int getImageResourceId(){
        return mImageResourceId;
    }

    /**
     * Returns whether or not there is an image for this word.
     * @return
     */
    public boolean hasImage(){
        return  mImageResourceId !=NO_IMAGE_PROVIDED; }
        /**
        *Return the audio resource ID of the word.
         * @return
         */
    public int getAudioResourceId(){return mAudioResourceId;}
}
