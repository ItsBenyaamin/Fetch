
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://github.com/tonyofrancis/Fetch/blob/master/LICENSE)

![ScreenShot](https://github.com/tonyofrancis/Fetch/blob/v2/full_logo.png)

Overview
--------

Fetch is a simple, powerful, customizable file download manager library for Android.

![ScreenShot](https://github.com/tonyofrancis/Fetch/blob/v2/screenshot.png)

Features
--------

* Simple and easy to use API.
* Continuous downloading in the background.
* Concurrent downloading support.
* Ability to pause and resume downloads.
* Set the priority of a download.
* Network-specific downloading support.
* Ability to retry failed downloads.
* Ability to group downloads.
* Easy progress and status tracking.
* Download remaining time reporting (ETA).
* Download speed reporting.
* Save and Retrieve download information anytime.
* Notification Support.
* Storage Access Framework, Content Provider and URI support.
* And more...

Prerequisites
-------------

If you are saving downloads outside of your application's sandbox, you will need to
add the following storage permissions to your application's manifest. For Android SDK version
23(M) and above, you will also need to explicitly request these permissions from the user.

```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```
Also, as you are going to use Internet to download files. We need to add the Internet access permissions
in the Manifest.

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

How to use Fetch
----------------

Using Fetch is easy! Just add the Gradle dependency to your application's build.gradle file.

Gradle:
```java
val fetchVersion = "v3.3"
implementation "com.github.graymind75.Fetch:fetch2:$fetchVersion"
implementation "com.github.graymind75.Fetch:fetch2core:$fetchVersion"
implementation "com.github.graymind75.Fetch:fetch2okhttp:$fetchVersion"
```

Next, get an instance of Fetch and request a download.

```java
public class TestActivity extends AppCompatActivity {

    private Fetch fetch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

 FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(this)
                .setDownloadConcurrentLimit(3)
                .build();

        fetch = Fetch.Impl.getInstance(fetchConfiguration);

        String url = "http:www.example.com/test.txt";
        String file = "/downloads/test.txt";
        
        final Request request = new Request(url, file);
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG");
        
        fetch.enqueue(request, updatedRequest -> {
            //Request was successfully enqueued for download.
        }, error -> {
            //An error occurred enqueuing the request.
        });

    }
}
```

Tracking a download's progress and status is very easy with Fetch. 
Simply add a FetchListener to your Fetch instance, and the listener will be notified whenever a download's
status or progress changes.

```java
FetchListener fetchListener = new FetchListener() {
    @Override
    public void onQueued(@NotNull Download download, boolean waitingOnNetwork) {
        if (request.getId() == download.getId()) {
            showDownloadInList(download);
        }
    }

    @Override
    public void onCompleted(@NotNull Download download) {

    }

    @Override
    public void onError(@NotNull Download download) {
        Error error = download.getError();
    }

    @Override
    public void onProgress(@NotNull Download download, long etaInMilliSeconds, long downloadedBytesPerSecond) {
        if (request.getId() == download.getId()) {
            updateDownload(download, etaInMilliSeconds);
        }
        int progress = download.getProgress();
    }

    @Override
    public void onPaused(@NotNull Download download) {

    }

    @Override
    public void onResumed(@NotNull Download download) {

    }

    @Override
    public void onCancelled(@NotNull Download download) {

    }

    @Override
    public void onRemoved(@NotNull Download download) {

    }

    @Override
    public void onDeleted(@NotNull Download download) {

    }
};

fetch.addListener(fetchListener);

//Remove listener when done.
fetch.removeListener(fetchListener);
```

Fetch supports pausing and resuming downloads using the request's id.
A request's id is a unique identifier that maps a request to a Fetch Download.
A download returned by Fetch will have have an id that matches the request id that
started the download.

```java
Request request1 = new Request(url, file);
Request request2 = new Request(url2, file2);

fetch.pause(request1.getId());

...

fetch.resume(request2.getId());

```

You can query Fetch for download information in several ways.

```java
//Query all downloads
fetch.getDownloads(new Func<List<? extends Download>>() {
    @Override
        public void call(List<? extends Download> downloads) {
            //Access all downloads here
        }
});

//Get all downloads with a status
fetch.getDownloadsWithStatus(Status.DOWNLOADING, new Func<List<? extends Download>>() {
    @Override
        public void call(List<? extends Download> downloads) {
            //Access downloads that are downloading
        }
});

// You can also access grouped downloads
int groupId = 52687447745;
fetch.getDownloadsInGroup(groupId, new Func<List<? extends Download>>() {
    @Override
      public void call(List<? extends Download> downloads) {
              //Access grouped downloads
      }
});
```

When you are done with an instance of Fetch, simply release it.

```java
//do work

fetch.close();

//do more work
```

Downloaders
----------------

By default Fetch uses the HttpUrlConnection client via the HttpUrlConnectionDownloader
to download requests. Add the following Gradle dependency to your application's build.gradle
to use the OkHttp Downloader instead. You can create your custom downloaders
if necessary. See the Java docs for details.

```java
implementation "com.tonyodev.fetch2okhttp:fetch2okhttp:3.0.12"
```
Androidx use:
```java
implementation "androidx.tonyodev.fetch2okhttp:xfetch2okhttp:3.1.6"
```

Set the OkHttp Downloader for Fetch to use.
```java
OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(this)
    .setDownloadConcurrentLimit(10)
    .setHttpDownloader(new OkHttpDownloader(okHttpClient))
    .build();

Fetch fetch = Fetch.Impl.getInstance(fetchConfiguration);
```

Contribute
----------

Fetch can only get better if you make code contributions. Found a bug? Report it.
Have a feature idea you'd love to see in Fetch? Contribute to the project!

License
-------

```
Copyright (C) 2017 Tonyo Francis.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
