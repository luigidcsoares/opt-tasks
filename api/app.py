import os
from flask import Flask
from flask_restplus import Api
from pymongo import MongoClient

from src.api import api
from src.config import config_env

app = Flask(__name__)
app.config.from_object(config_env[os.getenv('ENV') or 'dev'])
api.init_app(app)

if __name__ == '__main__':
    app.run()
