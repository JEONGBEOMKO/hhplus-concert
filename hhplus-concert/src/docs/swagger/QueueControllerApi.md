# QueueControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**generateQueueToken**](QueueControllerApi.md#generateQueueToken) | **POST** /queue/users/{userId}/token | 
[**getQueueStatus**](QueueControllerApi.md#getQueueStatus) | **GET** /queue/users/{userId}/token | 

<a name="generateQueueToken"></a>
# **generateQueueToken**
> Queue generateQueueToken(userId)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.QueueControllerApi;


QueueControllerApi apiInstance = new QueueControllerApi();
Long userId = 789L; // Long | 
try {
    Queue result = apiInstance.generateQueueToken(userId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling QueueControllerApi#generateQueueToken");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **Long**|  |

### Return type

[**Queue**](Queue.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getQueueStatus"></a>
# **getQueueStatus**
> Queue getQueueStatus(userId)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.QueueControllerApi;


QueueControllerApi apiInstance = new QueueControllerApi();
Long userId = 789L; // Long | 
try {
    Queue result = apiInstance.getQueueStatus(userId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling QueueControllerApi#getQueueStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **Long**|  |

### Return type

[**Queue**](Queue.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

