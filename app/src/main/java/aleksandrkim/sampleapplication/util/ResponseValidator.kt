package aleksandrkim.sampleapplication.util

import aleksandrkim.sampleapplication.network.models.NewsApiResponse
import aleksandrkim.sampleapplication.network.models.ProcessedResponse
import android.util.Log
import retrofit2.Response

/**
 * Created by Aleksandr Kim on 23 Jul, 2018 9:13 PM for SampleApplication
 */
class ResponseValidator<T> : io.reactivex.functions.Function<Response<T>, ProcessedResponse> where T: NewsApiResponse{
    override fun apply(response: Response<T>): ProcessedResponse {
        Log.d("Repository Validator", "response all: " + response.toString())
        Log.d("Repository Validator", "response code: " + response.code())

//        if (response.code() == 200)
//            return
//        else
            return when (response.code()) {

                200 -> ProcessedResponse.SuccessfulResponse(response.body()!!)

                400 -> ProcessedResponse.ParameterInvalid

                401 -> ProcessedResponse.ApiKeyInvalid

                429 -> ProcessedResponse.RateLimited

                500 -> ProcessedResponse.UnexpectedError

//                "apiKeyDisabled" -> ProcessedResponse.ApiKeyDisabled
//
//                "apiKeyExhausted" -> ProcessedResponse.ApiKeyExhausted
//
//                "apiKeyInvalid" -> ProcessedResponse.ApiKeyInvalid
//
//                "apiKeyMissing" -> ProcessedResponse.ApiKeyMissing
//
//                "parameterInvalid" -> ProcessedResponse.ParameterInvalid
//
//                "parametersMissing" -> ProcessedResponse.ParametersMissing
//
//                "rateLimited" -> ProcessedResponse.RateLimited
//
//                "sourcesTooMany" -> ProcessedResponse.SourcesTooMany
//
//                "sourceDoesNotExist" -> ProcessedResponse.SourceDoesNotExist
//
//                "unexpectedError" -> ProcessedResponse.UnexpectedError

                else -> ProcessedResponse.UnknownError(response.body()?.code ?: "unknown", response.body()?.message ?: "unknown")
            }

    }
}