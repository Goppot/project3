package com.amr.project.service.impl;

import com.amr.project.service.abstracts.PaymentService;
import com.amr.project.service.abstracts.SettingService;
import com.amr.project.webapp.paypalsettings.PayPalOrderResponse;
import com.amr.project.webapp.paypalsettings.PaymentSettingBag;
import lombok.NoArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * User: Hajimurad Suleymanov
 * Date: 10.11.2021
 */

@Service
@NoArgsConstructor
public class PayPalService implements PaymentService {

    private SettingService settingService;

    private static final String GET_ORDER_API = ".../orders/";

    @Override
    public boolean validateOrder(String orderId) {
        PayPalOrderResponse orderResponse = getOrderDetails(orderId);

        return orderResponse.validate(orderId);
    }


    private PayPalOrderResponse getOrderDetails(String orderId) {
        ResponseEntity<PayPalOrderResponse> response = makeRequest(orderId);

        HttpStatus statusCode = response.getStatusCode();

        if (!statusCode.equals(HttpStatus.OK)) {
            throwExceptionForNonOKResponse(statusCode);
        }

        return response.getBody();
    }

    private ResponseEntity<PayPalOrderResponse> makeRequest(String orderId) {

        PaymentSettingBag paymentSettings = settingService.getPaymentSettings();
        String baseURL = paymentSettings.getURL();
        String clientId = paymentSettings.getClientID();
        String clientSecret = paymentSettings.getClientSecret();
        String requestURL = baseURL + GET_ORDER_API + orderId;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Accept-Language", "ru_RU");
        headers.setBasicAuth(clientId, clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(
                requestURL, HttpMethod.GET, request, PayPalOrderResponse.class);
    }

    private void throwExceptionForNonOKResponse(HttpStatus statusCode) {
        String message = null;

        switch (statusCode) {
            case NOT_FOUND:
                message = "Order ID not found";

            case BAD_REQUEST:
                message = "Bad Request to PayPal Checkout API";

            case INTERNAL_SERVER_ERROR:
                message = "PayPal server error";

            default:
                message = "PayPal returned non-OK status code";
        }
    }
}
