package org.covital.common.data.datasource.remote

import com.squareup.moshi.JsonEncodingException
import java.lang.reflect.Type
import okhttp3.ResponseBody
import org.covital.common.domain.model.Empty
import retrofit2.Converter
import retrofit2.Retrofit

class EmptyResponseFactory : Converter.Factory() {

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        val delegate = retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
        return Converter<ResponseBody, Any> {
                body ->
            if (body.contentLength() == 0L) {
                Empty()
            } else {
                try {
                    delegate.convert(body)
                } catch (ex: JsonEncodingException) {
                    Empty()
                }
            }
        }
    }
}
