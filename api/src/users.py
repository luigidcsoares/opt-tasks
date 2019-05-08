import jwt
import datetime
from flask import current_app as app
from flask_restplus import Namespace, Resource, fields
from flask_bcrypt import generate_password_hash, check_password_hash

# Import MongoDB instance.
from .db import db

##############################
######### Namespace ##########
##############################

ns = Namespace(
    'Users', 
    description='Endpoints for sign-in/sign-up',
    path='/api/user'
)

##############################
######## Flask Models ########
##############################

user = ns.model('User', {
    '_id': fields.String(description='User ID'),
    'fullname': fields.String(required=True, description='User name'),
})

userSecret = ns.model('UserSecret', {
    'username': fields.String(
        required=True, unique = True, 
        description='User login'
    ),
    'password': fields.String(
        required=True, unique=True,
        description='User password'
    )
})

userSignUp = ns.inherit('UserSignUp', user, userSecret, {
})

userSignIn = ns.model('UserSignIn', {
    'token': fields.String(description='Generated token')
})

##############################
########### Routes ###########
##############################

@ns.route('/signup')
class UserSignUp(Resource):
    @ns.doc('Store user info')
    @ns.expect(userSignUp, validate=True)
    @ns.marshal_with(user)
    def post(self):
        # Generate password hash to store in the database
        ns.payload['password'] = generate_password_hash(
            ns.payload['password'], 10
        )

        # Save new user and return it without password
        db.users.insert_one(ns.payload)
        return ns.payload
       

@ns.route('/signin')
class UserSignIn(Resource):
    @ns.doc('Send user info to authenticate and get token')
    @ns.expect(userSecret, validate=True)
    @ns.marshal_with(userSignIn)
    def post(self):
        # Find user by username
        user = db.users.find_one({'username': ns.payload['username']})

        if not user:
            return { 'message': 'User not found!' }, 404

        # Check if password is correct
        if check_password_hash(user['password'], ns.payload['password']):
            # Get new valid token
            token = jwt.encode({
                'user': ns.payload['username'],
                'exp': datetime.datetime.utcnow() + datetime.timedelta(days=30)
            }, app.config['SECRET_KEY'])
            
            return { 'token': token.decode('UTF-8') }
        else:
            abort(401, 'Password is invalid!', {
                'WWW-Authenticate': 'Basic realm="Login required"'
            })
