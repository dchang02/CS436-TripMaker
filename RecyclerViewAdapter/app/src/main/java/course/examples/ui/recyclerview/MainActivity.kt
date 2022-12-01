package course.examples.ui.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.fragment.findNavController
import course.examples.ui.recyclerview.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    //private lateinit var binding: MainActivityBinding
    private lateinit var recyclerViewModel: RecyclerViewModel

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use the provided ViewBinding class to inflate the layout.
        val binding = MainActivityBinding.inflate(layoutInflater)

        // Set content view to the binding root view.
        setContentView(binding.root)

        recyclerViewModel = ViewModelProvider(this)[RecyclerViewModel::class.java]
        recyclerViewModel.bindToActivityLifecycle(this)

        //CHANGE THESE TO ADD THE CORRECT LOCATIONS AND VICINITIES
        recyclerViewModel.setNames(resources.getStringArray(R.array.names).toMutableList())
        recyclerViewModel.setVicinity(resources.getStringArray(R.array.colors).toMutableList())
        recyclerViewModel.setNamesToAdd(resources.getStringArray(R.array.names).toMutableList())
        recyclerViewModel.setVicinityToAdd(resources.getStringArray(R.array.colors).toMutableList())

        /*binding.fab.setOnClickListener {
            findNavController()
        }*/

        // Create a new custom RecyclerView adapter to manage
        // a list of colors and color names loaded from resource
        // arrays.
        RecyclerViewAdapter(
            //resources.obtainTypedArray(R.array.colors),
            //Replaces this with the array of locations
            recyclerViewModel
        ).also {
            // Set the RecyclerView to this adapter.
            binding.list.adapter = it

            // Setting a fixed size adapter improves performance.
            binding.list.setHasFixedSize(true)
        }
    }
}