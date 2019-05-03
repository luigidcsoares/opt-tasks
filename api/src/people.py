from flask_restplus import Namespace, Resource, fields

# Import MongoDB instance.
from .db import db

##############################
######### Namespace ##########
##############################

ns = Namespace(
    'people', 
    description='People who will be allocated',
    path='/people'
)

##############################
######## Flask Models ########
##############################

person = ns.model('Person', {
    'firstname': fields.String(required=True, description='First Name'),
    'lastname': fields.String(description='Last Name'),
    'skills': fields.List(fields.Nested(ns.model(
        'Skill',
        {'task': fields.String, 'value': fields.Integer}
    )))
})

##############################
########### Routes ###########
##############################

@ns.route('/')
class People(Resource):
    @ns.doc('Get the list of individuals')
    @ns.marshal_list_with(person)
    def get(self):
        """ List of people """
        return {
            'firstname': 'Teste',
            'lastname': '1',
            'tasks': [{'task': 'Compila', 'value': 2}]
        }

    @ns.doc('Post a new individual')
    @ns.expect(person, validate=True)
    @ns.marshal_with(person)
    def post(self):
        db.people.insert_one(ns.payload)
        return ns.payload
