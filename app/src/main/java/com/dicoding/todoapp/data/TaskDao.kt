package com.dicoding.todoapp.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

//TODO 2 : Define data access object (DAO)
@Dao
interface TaskDao {

    @RawQuery(observedEntities = [Task::class])
    fun getTasks(query: SupportSQLiteQuery): DataSource.Factory<Int, Task>

    @Query("Select * from Task where Task.id = :taskId")
    fun getTaskById(taskId: Int): LiveData<Task>

    @Query("Select * from Task where dueDateMillis = (select min(dueDateMillis) from Task where isCompleted = 0)")
    fun getNearestActiveTask(): Task

    @Insert
    suspend fun insertTask(task: Task): Long

    @Insert
    fun insertAll(vararg tasks: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("Update Task set isCompleted = :completed where id = :taskId")
    suspend fun updateCompleted(taskId: Int, completed: Boolean)
    
}
