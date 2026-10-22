package com.example.panol.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.panol.data.local.dao.PanolDao
import com.example.panol.data.local.dao.UsuarioDao
import com.example.panol.data.local.entity.EquipoEntity
import com.example.panol.data.local.entity.ReservaEntity
import com.example.panol.data.local.entity.UsuarioEntity


@Database(
    entities = [UsuarioEntity::class, EquipoEntity::class, ReservaEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun panolDao(): PanolDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                        "Myapp_database.db"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}