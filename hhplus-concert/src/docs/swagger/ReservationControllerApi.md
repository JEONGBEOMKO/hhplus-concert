# ReservationControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAvailableDates**](ReservationControllerApi.md#getAvailableDates) | **GET** /reservations/available-dates | 
[**getAvailableSeats**](ReservationControllerApi.md#getAvailableSeats) | **GET** /reservations/available-seats | 
[**getReservation**](ReservationControllerApi.md#getReservation) | **GET** /reservations/users/{userId}/reservations/{resId} | 
[**reserveSeat**](ReservationControllerApi.md#reserveSeat) | **POST** /reservations/users/{userId}/reserve | 

<a name="getAvailableDates"></a>
# **getAvailableDates**
> List&lt;String&gt; getAvailableDates()



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ReservationControllerApi;


ReservationControllerApi apiInstance = new ReservationControllerApi();
try {
    List<String> result = apiInstance.getAvailableDates();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ReservationControllerApi#getAvailableDates");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getAvailableSeats"></a>
# **getAvailableSeats**
> AvailableSeats getAvailableSeats(date)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ReservationControllerApi;


ReservationControllerApi apiInstance = new ReservationControllerApi();
String date = "date_example"; // String | 
try {
    AvailableSeats result = apiInstance.getAvailableSeats(date);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ReservationControllerApi#getAvailableSeats");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **date** | **String**|  |

### Return type

[**AvailableSeats**](AvailableSeats.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getReservation"></a>
# **getReservation**
> Reservation getReservation(userId, resId)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ReservationControllerApi;


ReservationControllerApi apiInstance = new ReservationControllerApi();
Long userId = 789L; // Long | 
Long resId = 789L; // Long | 
try {
    Reservation result = apiInstance.getReservation(userId, resId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ReservationControllerApi#getReservation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **Long**|  |
 **resId** | **Long**|  |

### Return type

[**Reservation**](Reservation.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="reserveSeat"></a>
# **reserveSeat**
> Reservation reserveSeat(body, userId)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ReservationControllerApi;


ReservationControllerApi apiInstance = new ReservationControllerApi();
Reservation body = new Reservation(); // Reservation | 
Long userId = 789L; // Long | 
try {
    Reservation result = apiInstance.reserveSeat(body, userId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ReservationControllerApi#reserveSeat");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Reservation**](Reservation.md)|  |
 **userId** | **Long**|  |

### Return type

[**Reservation**](Reservation.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

