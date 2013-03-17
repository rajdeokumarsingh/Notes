http://hc.apache.org/httpcomponents-client-ga/quickstart.html



AndroidHttpClient
    client = AndroidHttpClient.newInstance(userAgent(), mContext);


    HttpGet request = new HttpGet(state.mRequestUri);

    HttpResponse response = client.execute(request);

    Header header = response.getFirstHeader("Content-Disposition");

    InputStream entityStream = response.getEntity().getContent();
    for (;;) {
        int bytesRead = entityStream.read(data);
        if (bytesRead == -1) { // success, end of stream already reached
            handleEndOfStream(state, innerState);
        }
    }

    request.abort();


