package com.example.tronc.gamesdaily.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tronc.gamesdaily.R;

public class HeaderFragment extends Fragment {
    private Context mContext;
    private TextView myText;

    @Override
    public	void	onCreate(Bundle savedInstanceState)	{
        super.onCreate(savedInstanceState);
        mContext	= getActivity();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle	savedInstanceState)	{
        View mContentView	=	inflater.inflate(R.layout.fragment_header,	container, false);
        myText	=	(TextView) mContentView.findViewById(R.id.titleTv);
        return mContentView;
    }
}
