package com.hulkhiretech.payments.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import com.hulkhiretech.payments.constants.ErrorCodeEnum; // ✅ Corrected import
import com.hulkhiretech.payments.exception.PaypalProviderException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HttpServiceEngine {

	private final RestClient restClient;

	public HttpServiceEngine(RestClient restClient) {
		this.restClient = restClient;
	}

	public ResponseEntity<String> makeHttpCall(HttpRequest httpRequest) {
		log.info("makeHttpCall called | httpRequest: {}", httpRequest);

		try {
			ResponseEntity<String> responseEntity = restClient.method(httpRequest.getHttpMethod())
					.uri(httpRequest.getUrl())
					.headers(headers -> headers.addAll(httpRequest.getHeaders()))
					.body(httpRequest.getRequestBody())
					.retrieve()
					.toEntity(String.class);

			log.info("responseEntity: {}", responseEntity);
			return responseEntity;

		} catch (HttpClientErrorException | HttpServerErrorException e) {
			log.error("HTTP error occurred: {}", e.getStatusCode(), e);

			HttpStatusCode status = e.getStatusCode();

			if (status == HttpStatus.SERVICE_UNAVAILABLE || status == HttpStatus.GATEWAY_TIMEOUT) {
				throw new PaypalProviderException(
						ErrorCodeEnum.UNABLE_TO_CONNECT_PAYPAL.getCode(),
						ErrorCodeEnum.UNABLE_TO_CONNECT_PAYPAL.getMessage(),
						HttpStatus.valueOf(status.value()));
			}

			String errorJson = e.getResponseBodyAsString();
			return ResponseEntity.status(status).body(errorJson);

		} catch (Exception e) {
			log.error("Exception occurred while making HTTP call: {}", e.getMessage(), e);

			throw new PaypalProviderException(
					ErrorCodeEnum.UNABLE_TO_CONNECT_PAYPAL.getCode(),
					ErrorCodeEnum.UNABLE_TO_CONNECT_PAYPAL.getMessage(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
}
