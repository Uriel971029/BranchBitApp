package com.example.katia.examenandroidbranchbitapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.katia.examenandroidbranchbitapp.request.dto.EmployeeDTO

@Dao
interface EmployeeDAO {
    @Query("select * from employee")
    fun getAll(): List<EmployeeDTO>

    @Query("SELECT * FROM employee where id = :id")
    fun getById(id: Int): EmployeeDTO

    @Insert
    fun insert(employeeDTO: EmployeeDTO)

    @Delete
    fun detele(employeeDTO: EmployeeDTO)
}