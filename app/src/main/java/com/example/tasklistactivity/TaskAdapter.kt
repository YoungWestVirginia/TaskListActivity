package com.example.tasklistactivity

class TaskAdapter(private val tasks: MutableList<Task>, private val onDelete: (Int) -> Unit) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.textViewTask.text = task.title
            binding.root.setOnClickListener {
                // Handle item click if needed
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
        holder.itemView.setOnLongClickListener {
            onDelete(position)
            true
        }
    }

    override fun getItemCount(): Int = tasks.size
}

