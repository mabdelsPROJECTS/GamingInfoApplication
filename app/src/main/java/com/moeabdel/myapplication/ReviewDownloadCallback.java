package com.moeabdel.myapplication;

import com.android.volley.VolleyError;

public interface ReviewDownloadCallback {
    void onReviewDownloadComplete(String response, String input, int position);
    void onReviewDownloadError(VolleyError error);
}