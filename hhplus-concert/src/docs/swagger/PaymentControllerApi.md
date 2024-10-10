# PaymentControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getPayment**](PaymentControllerApi.md#getPayment) | **GET** /api/payments/{paymentId} | 
[**processPayment**](PaymentControllerApi.md#processPayment) | **POST** /api/payments | 

<a name="getPayment"></a>
# **getPayment**
> Payment getPayment(paymentId)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PaymentControllerApi;


PaymentControllerApi apiInstance = new PaymentControllerApi();
Long paymentId = 789L; // Long | 
try {
    Payment result = apiInstance.getPayment(paymentId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PaymentControllerApi#getPayment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **paymentId** | **Long**|  |

### Return type

[**Payment**](Payment.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="processPayment"></a>
# **processPayment**
> String processPayment(body)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PaymentControllerApi;


PaymentControllerApi apiInstance = new PaymentControllerApi();
Payment body = new Payment(); // Payment | 
try {
    String result = apiInstance.processPayment(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PaymentControllerApi#processPayment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Payment**](Payment.md)|  |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

