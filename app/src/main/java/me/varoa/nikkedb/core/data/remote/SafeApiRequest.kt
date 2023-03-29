package me.varoa.nikkedb.core.data.remote

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import logcat.logcat
import me.varoa.nikkedb.utils.ApiException
import me.varoa.nikkedb.utils.EspressoIdlingResource
import me.varoa.nikkedb.utils.NoInternetException
import org.json.JSONException
import retrofit2.Response

open class SafeApiRequest {
    suspend fun <T : Any> apiRequest(
        call: suspend () -> Response<T>,
    ): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val message = StringBuilder()
            message.append("${response.code()}: ").append(response.message())
            Log.d("Test","Error Message is $message")
            throw ApiException(message.toString())
        }
    }
}

inline fun <T> wrapFlowApiCall(crossinline function: suspend () -> Result<T>): Flow<Result<T>> = flow {
    EspressoIdlingResource.increment()
    try {
        emit(function())
    } catch (ex: ApiException) {
        emit(Result.failure(ex))
    } catch (ex: NoInternetException) {
        emit(Result.failure(ex))
    }finally{
        EspressoIdlingResource.decrement()
    }
}
