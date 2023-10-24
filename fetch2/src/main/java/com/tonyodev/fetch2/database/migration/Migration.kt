package com.tonyodev.fetch2.database.migration

import androidx.room.migration.Migration


abstract class MyMigration constructor(startVersion: Int, endVersion: Int)
    : Migration(startVersion, endVersion)