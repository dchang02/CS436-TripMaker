package course.examples.ui.recyclerview

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
//import androidx.navigation.fragment.findNavController
import course.examples.ui.recyclerview.databinding.FramentRecyclerViewBinding
import course.examples.ui.recyclerview.databinding.ListItemLayoutBinding


class FragmentRecyclerView : Fragment() {

    class RecyclerViewAdapter(
        private val name: MutableList<String>,
        private val vicinity: MutableList<String>,
        private val viewModel: RecyclerViewModel
    ) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

        /**
         * Called [RecyclerView] to create a new [ViewHolder]
         * that contains an list item [View]. [onBindViewHolder]
         * will be called next.
         */

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            return ViewHolder(
                ListItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        /**
         * Called by [RecyclerView] to bind item at [position] to the passed
         * [ViewHolder]. The [RecyclerView] maintains only a small number
         * of [ViewHolder] instances and therefore needs to recycle, or
         * rebind, those instances as the user scrolls new items into view.
         */
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            //input the location information
            holder.name.text = name[position]
        }

        /**
         * Called by [RecyclerView] when iterating through the list items.
         */
        override fun getItemCount(): Int = name.size

        //Removes the location at the specified position
        fun addItem(position: Int) {
            viewModel.addItem(position)
            notifyDataSetChanged()
        }

        /**
         * A custom [RecyclerView.ViewHolder] used to display each list item
         * and for handling button click input events.
         */
        inner class ViewHolder(binding: ListItemLayoutBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val name: TextView = binding.textView

            init {
                itemView.setOnLongClickListener {

                    //Creates a pop up asking to delete the specified item
                    var builder = AlertDialog.Builder(binding.root.context)
                    builder.setTitle(R.string.confirm_add)
                    builder.setMessage("Are you sure you want to add this item?: ${name.text}")
                    builder.setPositiveButton(R.string.yes, DialogInterface.OnClickListener { dialog, id ->
                        //Add item to the trip list
                        addItem(this.absoluteAdapterPosition)
                        dialog.cancel()
                    })
                    builder.setNegativeButton(R.string.no, DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })

                    var alert = builder.create()
                    alert.show()

                    return@setOnLongClickListener true
                }
            }
        }
    }
}