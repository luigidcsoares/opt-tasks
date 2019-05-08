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
            data = jwt.decode(token, app.config['SECRET_KEY'])
        except:
            abort(403, 'Token is invalid!')

        return f(*args, **kwargs)

    return decorated
