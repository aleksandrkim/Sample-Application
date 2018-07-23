package aleksandrkim.sampleapplication.network.models

/**
 * Created by Aleksandr Kim on 23 Jul, 2018 8:44 PM for SampleApplication
 */
sealed class ProcessedResponse {

    open class SuccessfulResponse(val content: Any): ProcessedResponse()

    data class UnknownError(val code: String = "unknown", val message: String = "service is not responding") :
        ProcessedResponse()

//    object ApiKeyDisabled: ProcessedResponse()
//
//    object ApiKeyExhausted: ProcessedResponse()

    object ApiKeyInvalid: ProcessedResponse()

//    object ApiKeyMissing: ProcessedResponse()

//    object ParameterInvalid: ProcessedResponse()

    object BadRequest: ProcessedResponse()

//    object ParametersMissing: ProcessedResponse()

    object RateLimited: ProcessedResponse()

//    object SourcesTooMany: ProcessedResponse()

//    object SourceDoesNotExist: ProcessedResponse()

    object UnexpectedError: ProcessedResponse()

}