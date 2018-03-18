package org.freactive.flawnkid.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.fragment_game.*
import android.widget.Toast
import android.webkit.WebView
import android.webkit.WebChromeClient
import kotlinx.android.synthetic.main.activity_home.*
import org.freactive.flawnkid.R
import java.util.*


/**
 * Created by sashank on 18/3/18.
 */

class GameFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //activity.window.requestFeature(Window.FEATURE_PROGRESS)
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView.settings.javaScriptEnabled = true

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                //activity.setProgress(progress * 1000)
            }
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                Toast.makeText(context, "Oh no! $description", Toast.LENGTH_SHORT).show()
            }
        }

        val PREFS_KEY = "org.freactive.flawnkid.PREFS"

        webView.settings.builtInZoomControls = true

        webView.loadUrl("https://flawnkid-game2.surge.sh")
    }
}