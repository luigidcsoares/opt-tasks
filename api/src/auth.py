from flask import current_app as app
from flask_restplus import Namespace, Resource, fields

# Import util functions
from .utils import token_required

##############################
######### Namespace ##########
##############################

ns = Namespace(
    'Auth', 
    description='Token-based auth',
    path='/api/auth'
)


##############################
########### Routes ###########
##############################

@ns.route('/token')
class AuthToken(Resource):
    @ns.doc('Auth user by using token-based approach', security='apikey')
    @token_required
    def post(self):
        # Just return a message since `token_required` will
        # take cara of ther validation
        return { 'message': 'Authorized' }
