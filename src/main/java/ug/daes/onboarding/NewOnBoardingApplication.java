package ug.daes.onboarding;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.apache.hc.client5.http.ssl.DefaultHostnameVerifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;


@EnableAsync
@SpringBootApplication
@EnableScheduling
public class NewOnBoardingApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewOnBoardingApplication.class, args);
	}


	@Bean
	public RestTemplate restTemplate() {

		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(Timeout.ofSeconds(10))
				.setConnectTimeout(Timeout.ofSeconds(10))
				.setResponseTimeout(Timeout.ofSeconds(30))
				.build();

		SSLConnectionSocketFactory sslSocketFactory =
				SSLConnectionSocketFactoryBuilder.create()
						.setSslContext(SSLContexts.createSystemDefault())
						.setHostnameVerifier(new DefaultHostnameVerifier())
						.build();

		PoolingHttpClientConnectionManager connectionManager =
				PoolingHttpClientConnectionManagerBuilder.create()
						.setSSLSocketFactory(sslSocketFactory)
						.build();

		CloseableHttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(requestConfig)
				.setConnectionManager(connectionManager)
				.evictExpiredConnections()
				.evictIdleConnections(TimeValue.ofMinutes(1))
				.build();

		HttpComponentsClientHttpRequestFactory requestFactory =
				new HttpComponentsClientHttpRequestFactory(httpClient);

		return new RestTemplate(requestFactory);
	}

}
