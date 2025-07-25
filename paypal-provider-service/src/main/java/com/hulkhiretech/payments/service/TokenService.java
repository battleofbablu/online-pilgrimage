package com.hulkhiretech.payments.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hulkhiretech.payments.constants.Constants;
import com.hulkhiretech.payments.http.HttpRequest;
import com.hulkhiretech.payments.http.HttpServiceEngine;
import com.hulkhiretech.payments.paypal.OAuthToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

	private final HttpServiceEngine httpServiceEngine;
	private final ObjectMapper objectMapper;

	@Value("${paypal.clientId}")
	private String clientId;

	@Value("${paypal.clientSecret}")
	private String clientSecret;

	@Value("${paypal.oAuthUrl}")
	private String oAuthUrl;

	// TODO: For production use, manage accessToken with Redis or a cache.
	private static String accessToken;

	public String getAccessToken() {
		log.info("getAccessToken called");

		if (accessToken != null) {
			log.info("Returning cached accessToken: {}", accessToken);
			return accessToken;
		}

		log.info("Calling PayPal to generate new accessToken");

		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(clientId, clientSecret);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
		formParams.add(Constants.GRANT_TYPE, Constants.CLIENT_CREDENTIALS);

		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setHttpMethod(HttpMethod.POST);
		httpRequest.setUrl(oAuthUrl);
		httpRequest.setHeaders(headers);
		httpRequest.setRequestBody(formParams);

		log.info("httpRequest: {}", httpRequest);

		ResponseEntity<String> oAuthResponse = httpServiceEngine.makeHttpCall(httpRequest);
		String responseBody = oAuthResponse.getBody();
		log.info("PayPal OAuth Response Body: {}", responseBody);

		try {
			OAuthToken oAuthObj = objectMapper.readValue(responseBody, OAuthToken.class);
			accessToken = oAuthObj.getAccessToken();
			log.info("Generated accessToken: {}", accessToken);
		} catch (Exception e) {
			log.error("Failed to parse PayPal access token response", e);
		}

		return accessToken;
	}
}
