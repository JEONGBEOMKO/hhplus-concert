# BalanceControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**chargeBalance**](BalanceControllerApi.md#chargeBalance) | **POST** /balance/charge | 
[**getBalance**](BalanceControllerApi.md#getBalance) | **GET** /balance/{userId} | 

<a name="chargeBalance"></a>
# **chargeBalance**
> String chargeBalance(body)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.BalanceControllerApi;


BalanceControllerApi apiInstance = new BalanceControllerApi();
ChargeRequest body = new ChargeRequest(); // ChargeRequest | 
try {
    String result = apiInstance.chargeBalance(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BalanceControllerApi#chargeBalance");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ChargeRequest**](ChargeRequest.md)|  |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getBalance"></a>
# **getBalance**
> Balance getBalance(userId)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.BalanceControllerApi;


BalanceControllerApi apiInstance = new BalanceControllerApi();
Long userId = 789L; // Long | 
try {
    Balance result = apiInstance.getBalance(userId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BalanceControllerApi#getBalance");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **Long**|  |

### Return type

[**Balance**](Balance.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

