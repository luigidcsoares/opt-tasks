from flask import Flask
from flask_restplus import Api
from pymongo import MongoClient

from src.api import api

app = Flask(__name__)
api.init_app(app)
app.run(debug=True)
