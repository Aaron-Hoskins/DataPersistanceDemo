package com.examples.coding.datapersistancedemo

const val DATABASE_NAME = "data_per_database"
const val TABLE_NAME = "person_table"
const val DATABASE_VERSION = 1
const val COL_FIRST_NAME = "first_name"
const val COL_LAST_NAME = "last_name"
const val COL_SSN = "ssn"

const val CREATE_PERSON_TABLE =
    "CREATE TABLE $TABLE_NAME (" +
            "$COL_FIRST_NAME String," +
            "$COL_LAST_NAME String," +
            "$COL_SSN String PRIMARY_KEY)"