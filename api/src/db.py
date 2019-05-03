from pymongo import MongoClient

_db_uri = 'mongodb+srv://admin:1234@cluster0-rnl6b.azure.mongodb.net/'
_db_name = 'dev'
_db_opts = '?retryWrites=true'

db = MongoClient(_db_uri + _db_opts)[_db_name]
