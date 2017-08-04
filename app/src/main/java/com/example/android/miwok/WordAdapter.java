package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lazarus Moleele on 2017/07/11.
 */

public class WordAdapter extends ArrayAdapter<Word>{

    /**Resource ID for the background color for this list of words*/
    private int mColorResourceId;

    public WordAdapter(Activity context, ArrayList<Word>words, int colorResourceId){
        super(context, 0, words);
        mColorResourceId = colorResourceId;
    }
        @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            //Check if the existing view is being reused, otherwise inflate the view
            View listItemView = convertView;
            if (listItemView == null){
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_items,parent, false);
            }
            //Get {@link } object located at this position in the list
            Word currentWord = getItem(position);

            //Find the TextView in the list_item.xml layout with the ID version_name
            TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
            //
            miwokTextView.setText(currentWord.getMiwokTranslation());

            TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
            defaultTextView.setText(currentWord.getmDefaultTranslation());

            ImageView imageView = (ImageView) listItemView.findViewById(R.id.imager);

            if(currentWord.hasImage()) {
                //Set the ImagineView to the image resource specified in the current word
                imageView.setImageResource(currentWord.getImageResourceId());

                //Make sure the view is visible
                imageView.setVisibility(View.VISIBLE);
            }
            else {
                //Otherwise hide the ImageView (set visibility to GONE)
                imageView.setVisibility(View.GONE);
            }

            //Set the name color for the list item
            View textContainer = listItemView.findViewById(R.id.text_container);
            //Find the color that the resource ID maps to
            int color = ContextCompat.getColor(getContext(), mColorResourceId);
            //Set the background color of the text container View
            textContainer.setBackgroundColor(color);
            return listItemView;


    }
}

