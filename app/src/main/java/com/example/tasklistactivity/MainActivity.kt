package com.example.tasklistactivity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import android.widget.Toast
import com.example.tasklistactivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tasks = mutableListOf<Task>() // Assuming you have a Task class defined
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupFab()
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter(tasks) { position -> deleteTask(position) }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Swipe to delete
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                deleteTask(position)
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        // Dialog implementation to add a task
    }

    private fun deleteTask(position: Int) {
        // Task deletion implementation
        if (position < tasks.size) {
            val deletedTask = tasks[position]
            tasks.removeAt(position)
            adapter.notifyItemRemoved(position)
            Snackbar.make(binding.recyclerView, "Deleted: ${deletedTask.title}", Snackbar.LENGTH_LONG)
                .setAction("Undo") {
                    tasks.add(position, deletedTask)
                    adapter.notifyItemInserted(position)
                }.show()
        } else {
            Toast.makeText(this, "Error: Task not found", Toast.LENGTH_SHORT).show()
        }
    }
}
