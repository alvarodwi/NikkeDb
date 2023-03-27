package me.varoa.nikkedb.core.data.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import logcat.logcat
import me.varoa.nikkedb.utils.ApiException
import me.varoa.nikkedb.utils.NoInternetException
import org.json.JSONException
import retrofit2.Response

open class SafeApiRequest {
    suspend fun <T : Any> apiRequest(
        call: suspend () -> Response<T>,
        decodeErrorJson: suspend (String) -> String
    ): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            error?.let {
                try {
                    message.append(decodeErrorJson(it))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            throw ApiException(message.toString())
        }
    }
}

inline fun <T> wrapFlowApiCall(crossinline function: suspend () -> Result<T>): Flow<Result<T>> = flow {
    try {
        emit(function())
    } catch (ex: ApiException) {
        emit(Result.failure(ex))
    } catch (ex: NoInternetException) {
        emit(Result.failure(ex))
    }
}
