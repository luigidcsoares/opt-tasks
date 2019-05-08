from flask import current_app as app
from flask_restplus import Namespace, Resource, fields

# Import util functions
from .utils import validate_token

##############################
######### Namespace ##########
##############################

ns = Namespace(
    'Auth', 
    description='Token-based auth',
    path='/api/auth'
)

##############################
######## Flask Models ########
##############################

tokenRequest = ns.model('TokenRequest', {
    'token': fields.String(require=True, description='Access token')
})

tokenResponse = ns.model('TokenResponse', {
    'user': fields.String(description='Username from token'),
    'exp': fields.String(description='Token expires in')
})

##############################
########### Routes ###########
##############################

@ns.route('/verify')
class AuthToken(Resource):
    @ns.doc('Auth user by using token-based approach')
    @ns.expect(tokenRequest, validate=True)
    @ns.marshal_with(tokenResponse)
    def post(self):
        try:
            print(validate_token(ns.payload['token']))
            return validate_token(ns.payload['token'])
        except:
            return { 'message': 'Token is invalid!' }, 403


