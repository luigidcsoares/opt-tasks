from pymongo import MongoClient
import os

from .config import DB_NAME, DB_OPTS, DB_URI

db = MongoClient(DB_URI + DB_OPTS)[DB_NAME]
