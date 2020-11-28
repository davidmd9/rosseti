package com.rosseti.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import com.rosseti.R
import com.rosseti.base.BaseFragment

class VideoFragment(private val url: String): BaseFragment() {
    override var title = "Видео"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video, container, false)
        bindView(view)

        return view
    }

    private fun bindView(view: View) {
        val vView = view.findViewById<VideoView>(R.id.videoView)
        vView.setVideoPath(url)
        vView.start()
    }


}