package com.example.stevenperegrine.simba_cardemo

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import java.lang.reflect.ParameterizedType

/*class ArrayAdapterFactory : TypeAdapterFactory {

    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {

        var typeAdapter: TypeAdapter<T>? = null

        try {
            if (type.rawType == List<*>::class.java)
                typeAdapter = ArrayAdapter(
                    (type.type as ParameterizedType)
                        .actualTypeArguments[0] as Class<*>
                )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return typeAdapter


    }

}

*/