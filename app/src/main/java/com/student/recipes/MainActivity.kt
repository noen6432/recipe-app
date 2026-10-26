package com.student.recipes

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var tvRecipeInfo: TextView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvRecipeInfo = findViewById(R.id.tvRecipeInfo)
        webView = findViewById(R.id.webView)

        loadRecipeData()
        setupWebView()
    }

    private fun loadRecipeData() {
        try {
            val jsonString = assets.open("recipes.json").bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonString)
            if (jsonArray.length() > 0) {
                val firstRecipe = jsonArray.getJSONObject(0)
                val title = firstRecipe.getString("name")
                val calories = firstRecipe.getInt("calories")
                tvRecipeInfo.text = "Featured: $title ($calories kcal)"
            }
        } catch (e: Exception) {
            tvRecipeInfo.text = "Recipe catalog ready."
        }
    }

    private fun setupWebView() {
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = WebViewClient()
        // Replace with your Task 3 website URL
        webView.loadUrl("https://noen6432.github.io/pantry-chef-website/")
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
