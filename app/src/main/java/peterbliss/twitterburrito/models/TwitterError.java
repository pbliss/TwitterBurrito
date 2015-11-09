package peterbliss.twitterburrito.models;

/**
 * Created by pbliss on 11/7/2015.
 */
public class TwitterError {

    //{"errors":[{"message":"Sorry, that page does not exist","code":34}]}

    private String message;
    private int code;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public void setMessage(String value) {
        message = value;
    }

    public void setCode(int value) {
        code = value;
    }

    public String getErrorMessageFromCode(int code) {
        switch(code) {
            case 200:
                return "OK	Success!";

            case 304:
                return "Not Modified	There was no new data to return.";

            case 400:
                return "Bad Request	The request was invalid or cannot be otherwise served. An accompanying error message will explain further. In API v1.1, requests without authentication are considered invalid and will yield this response.";

            case 401:
                return "Unauthorized	Authentication credentials were missing or incorrect. Also returned in other circumstances, for example all calls to API v1 endpoints now return 401 (use API v1.1 instead).";

            case 403:
                return "Forbidden	The request is understood, but it has been refused or access is not allowed. An accompanying error message will explain why. This code is used when requests are being denied due to update limits. Other reasons for this status being returned are listed alongside the response codes in the table below.";

            case 404:
                return "Not Found	The URI requested is invalid or the resource requested, such as a user, does not exists. Also returned when the requested format is not supported by the requested method.";

            case 406:
                return "Not Acceptable	Returned by the Search API when an invalid format is specified in the request.";

            case 410:
                return "Gone	This resource is gone. Used to indicate that an API endpoint has been turned off. For example: “The Twitter REST API v1 will soon stop functioning. Please migrate to API v1.1.”";

            case 420:
                return "Enhance Your Calm	Returned by the version 1 Search and Trends APIs when you are being rate limited.";

            case 422:
                return "Unprocessable Entity	Returned when an image uploaded to POST account / update_profile_banner is unable to be processed.";

            case 429:
                return "Too Many Requests	Returned in API v1.1 when a request cannot be served due to the application’s rate limit having been exhausted for the resource. See Rate Limiting in API v1.1.";

            case 500:
                return "Internal Server Error	Something is broken. Please post to the developer forums so the Twitter team can investigate.";

            case 502:
                return "Bad Gateway	Twitter is down or being upgraded.";

            case 503:
                return "Service Unavailable	The Twitter servers are up, but overloaded with requests. Try again later.";

            case 504:
                return "Gateway timeout";
        }

        return "Unknown Error";
    }
}
