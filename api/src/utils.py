import jwt 
from flask import request, current_app as app
from flask_restplus import abort
from functools import wraps

def token_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = request.headers['X-API-KEY'] \
            if 'X-API-KEY' in request.headers \
            else None

        if not token:
            abort(403, 'Token is missing!')

        try:
            validate_token(token)
        except:
            abort(403, 'Token is invalid!')

        return f(*args, **kwargs)

    return decorated

def validate_token(token):
    return jwt.decode(token, app.config['SECRET_KEY'])

def uid_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        uid = request.headers['UID'] \
            if 'UID' in request.headers \
            else None
        
        print(uid)
        if not uid:
            abort(403, 'UID is missing!')

        return f(*args, **kwargs)

    return decorated
