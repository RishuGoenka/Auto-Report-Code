package com.rest.controller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class TriggerJenkins {

	public static Logger log = Logger.getLogger(TriggerJenkins.class.getClass());

	public static void main(String[] args) {
		try {
			// http://jenkins.example.com/view/testing/job/sample_selenium_test/buildWithParameters?token=YOUR_TOKEN¶meter1=VALUE1¶meter2=VALUE2#sthash.H4w7RY7o.dpuf
			// http://192.168.1.91:8080/view/All/job/iContract_CI_Auto/buildWithParameters?token=TOKEN_NAME&parameter1=VALUE1&parameter2=VALUE2
			// http://server/job/myjob/buildWithParameters?token=TOKEN\&PARAMETER=Value

			// Credentials
			String username = "avinashk.b";
			String password = "Dec@2015";

			// Jenkins url
			String jenkinsUrl = "http://192.168.1.91:8080";

			// Build name
			String jobName = "iContract_CI_Auto";

			// Build token
			String buildToken = "iContract_CI_Auto";

			String parameter = "&Trigger_Type=Automated&Build=Yes&Deploy=Yes&Test=Yes&BRANCH=iContract&RELEASE_VER=16.06.1.0&REPO_URL=http://192.168.2.85/svn/iContract&SVN_TAG_BASE_URL=http://192.168.2.85/svn/iContract/tags/JENKINS_BUILD_NEW&JENKINS_HOME=/u01/jenkins&AUTOMATION_DIR=../../automation&PRODUCT_NAME=iContract&JAVA_VERSION=/u01/jdk/jdk1.6.0_45&FINDBUGS_HOME=/u01/tools/findbugs-3.0.1&FINDBUGS_SRC_DIR=../../automation&Destination_Path=setup_iContract&Activity=AutoUpgrade&Destination_Server=192.168.1.177&AutomationEnvironment=Local_Contract&TestingType=IcontractSanityChromeRun.xml";

			// Create your httpclient
			DefaultHttpClient client = new DefaultHttpClient();

			// Then provide the right credentials
			client.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
					new UsernamePasswordCredentials(username, password));

			// Generate BASIC scheme object and stick it to the execution
			// context
			BasicScheme basicAuth = new BasicScheme();
			BasicHttpContext context = new BasicHttpContext();
			context.setAttribute("preemptive-auth", basicAuth);

			// Add as the first (because of the zero) request interceptor
			// It will first intercept the request and preemptively initialize
			// the
			// authentication scheme if there is not
			client.addRequestInterceptor(new PreemptiveAuth(), 0);

			// You get request that will start the build
			String getUrl = jenkinsUrl + "/job/" + jobName + "/buildWithParameters?token=" + buildToken + parameter;
			HttpGet get = new HttpGet(getUrl);

			// Execute your request with the given context
			HttpResponse response = client.execute(get, context);
			HttpEntity entity = response.getEntity();
			EntityUtils.consume(entity);
		} catch (Exception e) {
			log.debug(e);
		}
	}

	static class PreemptiveAuth implements HttpRequestInterceptor {
		@Override
		public void process(HttpRequest request, HttpContext context) {
			try {
				// Get the AuthState
				AuthState authState = (AuthState) context.getAttribute(ClientContext.TARGET_AUTH_STATE);

				// If no auth scheme available yet, try to initialize it
				// preemptively
				if (authState.getAuthScheme() == null) {
					AuthScheme authScheme = (AuthScheme) context.getAttribute("preemptive-auth");
					CredentialsProvider credsProvider = (CredentialsProvider) context
							.getAttribute(ClientContext.CREDS_PROVIDER);
					HttpHost targetHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
					if (authScheme != null) {
						Credentials creds = credsProvider
								.getCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()));
						if (creds == null)
							log.info("No credentials for preemptive authentication");
						else {
							authState.setAuthScheme(authScheme);
							authState.setCredentials(creds);
						}
					}
				}
			} catch (Exception e) {
				log.debug(e);
			}
		}

	}

}
