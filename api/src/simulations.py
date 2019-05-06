from flask_restplus import Namespace, Resource, fields
from bson.objectid import ObjectId
from pymongo import ReturnDocument
from unidecode import unidecode

import json

from .sim_glpk import sim

# Import MongoDB instance.
from .db import db 

##############################
######### Namespace ##########
##############################

ns = Namespace(
    'simulations', 
    description='Endpoints for linear problems simulations',
    path='/api/simulations'
)

##############################
######## Flask Models ########
##############################

simulation = ns.model('simulation', {
    '_id': fields.String(description='Simulation ID'),
    'name': fields.String(required=True, description='Simulation Name'),
    'tasks': fields.List(
        fields.Nested(ns.model(
            'task', {
                'name': fields.String(
                    required=True,
                    unique=True,
                    description='Task Name'
                ),
                'level': fields.Integer(
                    required=True, 
                    description='Difficulty level of this task'
                )
            }
        ))
    ),
    'students': fields.List(
        fields.Nested(ns.model('student', {
            'name': fields.String(required=True, unique=True, descripton='Student Name'),
            'skills': fields.List(fields.Nested(ns.model('skill', {
                'task': fields.String(
                    required=True, 
                    unique=True, 
                    description='Task Name/ID'
                ),
                'competency': fields.Integer(
                    required=True,
                    description='Competency associated with a task'
                )
            })))
        }))
    ),
    'allocations': fields.List(
        fields.Nested(ns.model('allocation', {
            'student': fields.String(),
            'tasks': fields.List(fields.String)
    }))),
    'Z': fields.Integer(description='Optimal value found by GLPK'),
    'status': fields.String(description='Status of GLPK solution')
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
        retrieve = sim(ns.payload)

        ns.payload['allocations'] = []
        ns.payload['Z'] = retrieve.pop('Z')
        ns.payload['status'] = retrieve.pop('status')

        tmp = set(list(map(
            lambda x: cleanRetrieve(x)[0], 
            retrieve.keys()
        )))

        for t in tmp:
            ns.payload['allocations'].append({ 'student' : t, 'tasks': [] })

        for key, value in retrieve.items():
            if value == 1:
                tr = cleanRetrieve(key)
                i = next(i for i, item in enumerate(ns.payload['allocations'])
                            if unidecode(item['student']) == tr[0])
                ns.payload['allocations'][i]['tasks'].append(tr[1])

        db.simulations.insert_one(ns.payload)
        return ns.payload

@ns.route('/<string:id>')
class Simulation(Resource):
    @ns.doc('Get a simulation')
    @ns.marshal_with(simulation)
    def get(self, id):
        return db.simulations.find_one({'_id': ObjectId(id)})

def cleanRetrieve(s):
    return s[4:].replace("_", " ").split("@")
