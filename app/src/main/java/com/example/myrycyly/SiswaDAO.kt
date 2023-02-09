package com.example.myrycyly

import androidx.room.*
@Dao
interface SiswaDAO{
    @Insert
    fun addsiswa(siswa : DataClass)

    @Update
    fun update (siswa: DataClass)

    @Delete
    fun delete (siswa: DataClass)

    @Query("SELECT * FROM dataclass")
    fun tampilsemua (): List<DataClass>

    @Query("SELECT * FROM dataclass WHERE nis =:Idsiswa")
    fun getid (Idsiswa : Int): List<DataClass>
}