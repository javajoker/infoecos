package com.infoecos.util;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;


/**
 * HTTP utilities
 * 
 * apache httpclient required, http://hc.apache.org/httpclient-3.x/
 * 
 * @author Ning Hu
 * 
 */
public class HttpUtil {
	/**
	 * Socket timeout
	 */
	public static int MAX_SO_TIMEOUT = 120 * 1000;

	/**
	 * Get HTTP response
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String getHttpResponse(String url) throws Exception {
		return getHttpResponse(url, (String) null);
	}

	/**
	 * Get HTTP response, use specified proxy
	 * 
	 * @param url
	 * @param proxy
	 *            proxy string, null for none proxy
	 * @return
	 * @throws Exception
	 */
	public static String getHttpResponse(String url, String proxy)
			throws Exception {
		return getHttpResponse(url, proxy, null);
	}

	/**
	 * Get HTTP response, use specified proxy and socket timeout
	 * 
	 * @param url
	 * @param proxy
	 *            proxy string, null for none proxy
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static String getHttpResponse(String url, String proxy, int timeout)
			throws Exception {
		return getHttpResponse(url, proxy, null, timeout);
	}

	/**
	 * Get HTTP response, use specified request parameters
	 * 
	 * @param url
	 * @param inputs
	 *            input parameters, {{key, value}, {key2,value2}, ...}
	 * @return
	 * @throws Exception
	 */
	public static String getHttpResponse(String url, String[][] inputs)
			throws Exception {
		return getHttpResponse(url, null, inputs);
	}

	/**
	 * Get HTTP response, use specified proxy and request parameters
	 * 
	 * @param url
	 * @param proxy
	 *            proxy string, null for none proxy
	 * @param inputs
	 *            input parameters, {{key, value}, {key2,value2}, ...}
	 * @return
	 * @throws Exception
	 */
	public static String getHttpResponse(String url, String proxy,
			String[][] inputs) throws Exception {
		return getHttpResponse(url, proxy, inputs, MAX_SO_TIMEOUT);
	}

	/**
	 * Get HTTP response, use specified proxy, request parameters and socket
	 * timeout.
	 * 
	 * Use utf8 encoding for default
	 * 
	 * @param url
	 * @param proxy
	 *            proxy string, null for none proxy
	 * @param inputs
	 *            input parameters, {{key, value}, {key2,value2}, ...}
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static String getHttpResponse(String url, String proxy,
			String[][] inputs, int timeout) throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 5000);
		HttpConnectionParams.setSoTimeout(httpclient.getParams(), timeout);
		if (null != proxy && !proxy.isEmpty()) {
			String[] ss = proxy.split(":");
			HttpHost proxyHost = new HttpHost(ss[0], Integer.parseInt(ss[1]),
					"http");
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxyHost);
		}

		try {
			HttpUriRequest request = null;
			if (inputs != null) {
				HttpPost httpost = new HttpPost(url);
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (String[] input : inputs)
					nvps.add(new BasicNameValuePair(input[0], input[1]));
				httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
				request = httpost;
			} else {
				HttpGet httpget = new HttpGet(url);
				request = httpget;
			}

			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			String encoding = "utf8";
			try {
				encoding = entity.getContentEncoding().getValue();
			} catch (Exception e) {
			}
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new UnicodeReader(
					entity.getContent(), encoding));
			String s;
			while ((s = br.readLine()) != null)
				sb.append(s + "\n");
			s = sb.toString();

			if (s.indexOf("<!-- bodycontent -->") >= 0)
				return null;

			return s;
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
	}
}
