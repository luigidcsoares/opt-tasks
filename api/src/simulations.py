from flask_restplus import Namespace, Resource, fields
from bson.objectid import ObjectId
from pymongo import ReturnDocument

from .sim_glpk import sim

# Import MongoDB instance.
from .db import db 

##############################
######### Namespace ##########
##############################

ns = Namespace(
    'simulations', 
    description='Simulations who will be allocated',
    path='/simulations'
)

##############################
######## Flask Models ########
##############################

simulation = ns.model('simulation', {
    'name': fields.String(required=True, description='Simulation Name'),
    'people': fields.List(
        fields.Nested(ns.model(
            'person', {
                'name': fields.String(required=True, unique=True, description='Person Name')
            }
        ))
    ),
    'tasks': fields.List(
        fields.Nested(ns.model(
            'task', {
                'name': fields.String(required=True, unique=True, description='Task Name'),
                'level': fields.Integer(required=True)
            }
        ))
    ),
    'skills': fields.List(
        fields.Nested(ns.model(
            'skill', { 
                'person': fields.String(required=True, unique=True, description='Person Name'),
                'task': fields.String(required=True, unique=True, description='Task Name'),
                'value': fields.Integer(required=True)
            }
        ))
    ),
})

##############################
########### Routes ###########
##############################

@ns.route('/')
class Simulations(Resource):
    @ns.doc('Get the list of simulations')
    @ns.marshal_list_with(simulation)
    def get(self):
        """ List of Simulations """
        return list(db.simulations.find({}))

    @ns.doc('Post a new simulation')
    @ns.expect(simulation, validate=True)
    @ns.marshal_with(simulation)
    def post(self):
        db.simulations.insert_one(ns.payload)
        sim(document)
        return ns.payload

@ns.route('/<string:id>')
class Simulation(Resource):
    @ns.doc('Get a simulation')
    @ns.marshal_with(simulation)
    def get(self, id):
        document = db.simulations.find_one({"_id": ObjectId(id)})
        return document

    """
    @ns.doc('Change a simulation')
    @ns.expect(simulation, validate=True)
    @ns.marshal_with(simulation)
    def put(self, id):
        return db.simulations.find_one_and_update(
            {"_id": ObjectId(id)},
            {"$inc": ns.payload},
            return_document = ReturnDocument.AFTER
        )
    """
