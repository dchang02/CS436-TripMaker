package course.examples.ui.recyclerview

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import course.examples.ui.recyclerview.databinding.ListItemLayoutBinding

/**
 * A [RecyclerView.Adapter] that displays a list of colors.
 */
class RecyclerViewAdapter(
    //private val colors: TypedArray,
    private val locations: Array<String>,
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
        holder.name.text = locations[position]
        //holder.name.setBackgroundColor(colors.getColor(position, 0))
    }

    /**
     * Called by [RecyclerView] when iterating through the list items.
     */
    override fun getItemCount(): Int = locations.size

    /**
     * A custom [RecyclerView.ViewHolder] used to display each list item
     * and for handling button click input events.
     */
    inner class ViewHolder(binding: ListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.textView

        init {
            itemView.setOnLongClickListener {
                // Display a Toast message indicting the selected item

                //create pop up to delete the selected item
                var builder = AlertDialog.Builder(binding.root.context)
                builder.setTitle(R.string.confirm_delete)
                builder.setMessage(R.string.delete_confirmation_message)
                builder.setPositiveButton(R.string.yes, DialogInterface.OnClickListener { dialog, id ->
                    //Set the TextView to be the new location
                    binding.textView.text = "Replaced"

                    dialog.cancel()
                })
                builder.setNegativeButton(R.string.no, DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

                var alert = builder.create()
                alert.show()

                /*Toast.makeText(
                    binding.root.context,
                    name.text,
                    Toast.LENGTH_SHORT
                ).show()*/
                return@setOnLongClickListener true
            }
        }
    }
}
