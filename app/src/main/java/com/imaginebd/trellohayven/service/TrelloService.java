package com.imaginebd.trellohayven.service;

import com.imaginebd.trellohayven.vo.BoardListVO;
import com.imaginebd.trellohayven.vo.BoardVO;
import com.imaginebd.trellohayven.vo.CardVO;
import com.imaginebd.trellohayven.vo.MemberVO;
import com.imaginebd.trellohayven.vo.NotificationVO;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONException;
import org.json.JSONObject;


public class TrelloService {
    private static final String USER_AGENT_STRING = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:5.0.1) Gecko/20100101 Firefox/5.0.1";
    
    private static final String PUBLIC_KEY = "ee531a6418b5706b6a4d8ed0452dd396";
    
    private static final String TRELLO_URL = "https://trello.com/";
    private static final String TRELLO_API_URL = "https://api.trello.com/1/";
    private static final String ME_BOARDS = "data/me/boards";
    private static final String DATA_BOARD = "data/board";
    private static final String DATA = "data";
    private static final String API_APP = "api/app";
    
    private static final Object METHOD_LOGIN = "login";



    public String mToken;
    public JsonFactory mJsonFactory;
    public ObjectMapper mObjectMapper;
    public HttpContext mContext;
    public CookieStore mCookieStore;
    
    public TrelloService() {
        mJsonFactory = new JsonFactory();
        mObjectMapper = new ObjectMapper()
            .configure(Feature.AUTO_DETECT_FIELDS, true)
            .configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(Feature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
            .configure(Feature.FAIL_ON_NUMBERS_FOR_ENUMS, false)
            .configure(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
            .configure(Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true)
            .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
            .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        
        mCookieStore = new BasicCookieStore();
        mContext = new BasicHttpContext();
        mContext.setAttribute(ClientContext.COOKIE_STORE, mCookieStore);
    }

    public void setToken(String token) {
        mToken = token;
    }

    public String getRequestLink() {
        return "https://trello.com/1/connect?key=ee531a6418b5706b6a4d8ed0452dd396&name=babul.itl240618@gmail.com&response_type=token&context=read,write";
    }
    
    
    
    public ArrayList<BoardVO> getAllBoards() {
        ArrayList<BoardVO> result = null;
        
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("key", PUBLIC_KEY));
        params.add(new BasicNameValuePair("token", mToken));
        
        HttpGet httpGet = new HttpGet(TRELLO_API_URL + "members/" + "me/" + "boards/" + "all?" + URLEncodedUtils.format(params, "UTF-8"));
        HttpClient httpClient = getHttpClient();
        
        try {
            httpGet.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT_STRING);

            HttpResponse response = httpClient.execute(httpGet, mContext);
            
            if (response != null) {
                result = mObjectMapper.readValue(mJsonFactory.createJsonParser(new InputStreamReader(response.getEntity().getContent(), "UTF-8")), new TypeReference<ArrayList<BoardVO>>(){});
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
    }

    public ArrayList<NotificationVO> getNotifications() {
        ArrayList<NotificationVO> result = null;
        
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("key", PUBLIC_KEY));
        params.add(new BasicNameValuePair("token", mToken));
        
        HttpGet httpGet = new HttpGet(TRELLO_API_URL + "members/" + "me/" + "notifications?" + URLEncodedUtils.format(params, "UTF-8"));
        HttpClient httpClient = getHttpClient();
        
        try {
            httpGet.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT_STRING);

            HttpResponse response = httpClient.execute(httpGet, mContext);
            
            if (response != null) {
                result = mObjectMapper.readValue(mJsonFactory.createJsonParser(new InputStreamReader(response.getEntity().getContent(), "UTF-8")), new TypeReference<ArrayList<NotificationVO>>(){});
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
    }

    public MemberVO getUser() {
        MemberVO result = null;
        
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("key", PUBLIC_KEY));
        params.add(new BasicNameValuePair("token", mToken));
        
        HttpGet httpGet = new HttpGet(TRELLO_API_URL + "members/" + "me?" + URLEncodedUtils.format(params, "UTF-8"));
        HttpClient httpClient = getHttpClient();
        
        try {
            httpGet.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT_STRING);

            HttpResponse response = httpClient.execute(httpGet, mContext);
            
            if (response != null) {
                result = mObjectMapper.readValue(mJsonFactory.createJsonParser(new InputStreamReader(response.getEntity().getContent(), "UTF-8")), MemberVO.class);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
    }

    public ArrayList<BoardListVO> getListsByBoard(String boardId) {
        ArrayList<BoardListVO> result = null;
        
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("key", PUBLIC_KEY));
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("cards", "none"));
        
        HttpGet httpGet = new HttpGet(TRELLO_API_URL + "boards/" + boardId + "/lists?" + URLEncodedUtils.format(params, "UTF-8"));
        HttpClient httpClient = getHttpClient();
        
        try {
            httpGet.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT_STRING);

            HttpResponse response = httpClient.execute(httpGet, mContext);
            
            if (response != null) {
                result = mObjectMapper.readValue(mJsonFactory.createJsonParser(new InputStreamReader(response.getEntity().getContent(), "UTF-8")), new TypeReference<ArrayList<BoardListVO>>(){});
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
    }

    public ArrayList<CardVO> getCardsByBoardList(String boardListId) {
        ArrayList<CardVO> result = null;
        
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("key",     PUBLIC_KEY));
        params.add(new BasicNameValuePair("token",   mToken));
        params.add(new BasicNameValuePair("filter",  "open"));
        params.add(new BasicNameValuePair("badges",  "true"));
        params.add(new BasicNameValuePair("labels",  "true"));
        params.add(new BasicNameValuePair("members", "true"));
        
        HttpGet httpGet = new HttpGet(TRELLO_API_URL + "lists/" + boardListId + "/cards?" + URLEncodedUtils.format(params, "UTF-8"));
        HttpClient httpClient = getHttpClient();
        
        try {
            httpGet.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT_STRING);

            HttpResponse response = httpClient.execute(httpGet, mContext);
            
            if (response != null) {
                result = mObjectMapper.readValue(mJsonFactory.createJsonParser(new InputStreamReader(response.getEntity().getContent(), "UTF-8")), new TypeReference<ArrayList<CardVO>>(){});
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
    }

    public Boolean addCard(String boardListId, String name) {
        Boolean result = false;
        
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("key", PUBLIC_KEY));
        params.add(new BasicNameValuePair("token", mToken));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("idList", boardListId));
        
        HttpPost httpPost = new HttpPost(TRELLO_API_URL + "cards?" + URLEncodedUtils.format(params, "UTF-8"));
        HttpClient httpClient = getHttpClient();
        
        try {
            httpPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT_STRING);

            HttpResponse response = httpClient.execute(httpPost, mContext);
            
            if (response != null) {
                result = true;
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public class CustomSSLSocketFactory extends SSLSocketFactory {

        private SSLContext sslContext = SSLContext.getInstance("TLS");

        public CustomSSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            super(truststore);

            TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

            };

            sslContext.init(null, new TrustManager[] {tm}, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }

    }
    
    public HttpClient getHttpClient() {

        DefaultHttpClient client = null;

        try {

            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new CustomSSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            
            // Setting up parameters
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, "utf-8");
            params.setBooleanParameter("http.protocol.expect-continue", true);

            // Setting timeout
            HttpConnectionParams.setConnectionTimeout(params, 100000);
            HttpConnectionParams.setSoTimeout(params, 100000);

            // Registering schemes for both HTTP and HTTPS
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            // Creating thread safe client connection manager
            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            // Creating HTTP client
            client = new DefaultHttpClient(ccm, params);

        } catch (Exception e) {
            client = new DefaultHttpClient();
        }

        return client;
    }
}