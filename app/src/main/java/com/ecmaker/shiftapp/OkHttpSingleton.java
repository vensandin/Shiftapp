package com.ecmaker.shiftapp;

import java.util.concurrent.TimeUnit;


public class OkHttpSingleton {
    private static OkHttpSingleton singletonInstance;

    // No need to be static; OkHttpSingleton is unique so is this.
    private okhttp3.OkHttpClient client;

    // Private so that this cannot be instantiated.
    private OkHttpSingleton() {

        //HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        //logInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        client = new okhttp3.OkHttpClient.Builder()
                //.connectTimeout(500, TimeUnit.MILLISECONDS)/*timeout: 5 seconds*/
                .readTimeout(1800, TimeUnit.SECONDS)
                .writeTimeout(1800, TimeUnit.SECONDS)
                //.addNetworkInterceptor(logInterceptor)
                //.eventListenerFactory(new LoggingEventListener.Factory())
                .build();
        /*
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build();

        client = new okhttp3.OkHttpClient.Builder()
                .connectionSpecs(Collections.singletonList(spec))
                .connectTimeout(1800, TimeUnit.SECONDS)
                .readTimeout(1800, TimeUnit.SECONDS)
                .writeTimeout(1800, TimeUnit.SECONDS)
                .build();
        */

    }
    /*
    public class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            Log.d("HttpLogInfo", message);
        }
    }
    */
    public static OkHttpSingleton getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new OkHttpSingleton();
        }
        return singletonInstance;
    }

    // In case you just need the unique OkHttpClient instance.
    public okhttp3.OkHttpClient getClient() {
        return client;
    }

    public void closeConnections() {
        client.dispatcher().cancelAll();
    }
}