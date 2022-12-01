package com.example.generateinitialitinerary

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Array
import kotlin.String
import kotlin.jvm.JvmStatic

public data class DraftItineraryArgs(
  public val userName: Array<String>
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putStringArray("userName", this.userName)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("userName", this.userName)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): DraftItineraryArgs {
      bundle.setClassLoader(DraftItineraryArgs::class.java.classLoader)
      val __userName : Array<String>?
      if (bundle.containsKey("userName")) {
        __userName = bundle.getStringArray("userName")
        if (__userName == null) {
          throw IllegalArgumentException("Argument \"userName\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"userName\" is missing and does not have an android:defaultValue")
      }
      return DraftItineraryArgs(__userName)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): DraftItineraryArgs {
      val __userName : Array<String>?
      if (savedStateHandle.contains("userName")) {
        __userName = savedStateHandle["userName"]
        if (__userName == null) {
          throw IllegalArgumentException("Argument \"userName\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"userName\" is missing and does not have an android:defaultValue")
      }
      return DraftItineraryArgs(__userName)
    }
  }
}
