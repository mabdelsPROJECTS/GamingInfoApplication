package com.moeabdel.myapplication;

import com.android.volley.VolleyError;

public interface DownloadCallback {
    void onDownloadComplete(String response);
    void onDownloadError(VolleyError error);
}