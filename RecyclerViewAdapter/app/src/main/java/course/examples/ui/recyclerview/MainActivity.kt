package course.examples.ui.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import course.examples.ui.recyclerview.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use the provided ViewBinding class to inflate the layout.
        val binding = MainActivityBinding.inflate(layoutInflater)

        // Set content view to the binding root view.
        setContentView(binding.root)

        // Create a new custom RecyclerView adapter to manage
        // a list of colors and color names loaded from resource
        // arrays.
        RecyclerViewAdapter(
            //resources.obtainTypedArray(R.array.colors),
            //Replaces this with the array of locations
            resources.getStringArray(R.array.names),
        ).also {
            // Set the RecyclerView to this adapter.
            binding.list.adapter = it

            // Setting a fixed size adapter improves performance.
            binding.list.setHasFixedSize(true)
        }
    }
}