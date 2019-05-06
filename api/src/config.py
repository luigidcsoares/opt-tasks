import os

# Database configs
DB_URI = 'mongodb+srv://admin:1234@cluster0-rnl6b.azure.mongodb.net/'
DB_OPTS = '?retryWrites=true'
DB_NAME = os.getenv('DB_NAME') or 'dev'

# Base class for configs
class Config:
    SECRET_KEY = os.getenv('SECRET_KEY', '1010f2799a47a21a17763a6f7fdd678c')

# Development configs
class DevConfig(Config):
    DEBUG = True
    ENV = 'dev'

# Production configs
class ProdConfig(Config):
    DEBUG = False
    ENV = 'prod'

config_env = {
    'dev': DevConfig,
    'prod': ProdConfig
}
