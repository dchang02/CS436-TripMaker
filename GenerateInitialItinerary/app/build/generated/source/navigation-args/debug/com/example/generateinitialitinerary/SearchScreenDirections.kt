package com.example.generateinitialitinerary

import android.os.Bundle
import androidx.navigation.NavDirections
import kotlin.Array
import kotlin.Int
import kotlin.String

public class SearchScreenDirections private constructor() {
  private data class ActionLoginFragmentToHelloFragment(
    public val userName: Array<String>
  ) : NavDirections {
    public override val actionId: Int = R.id.action_loginFragment_to_helloFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putStringArray("userName", this.userName)
        return result
      }
  }

  public companion object {
    public fun actionLoginFragmentToHelloFragment(userName: Array<String>): NavDirections =
        ActionLoginFragmentToHelloFragment(userName)
  }
}
