import json
from flask import request
from flask_restplus import Namespace, Resource, fields, reqparse
from bson.objectid import ObjectId
from pymongo import ReturnDocument
from unidecode import unidecode

# Import pulp glpk solver for our case
from .sim_glpk import sim

# Import MongoDB instance.
from .db import db

# Import util functions
from .utils import token_required, uid_required

##############################
######### Namespace ##########
##############################

ns = Namespace(
    'Simulations', 
    description='Endpoints for linear problems simulations',
    path='/api/simulations'
)

##############################
######## Flask Models ########
##############################

simulation = ns.model('Simulation', {
    '_id': fields.String(description='Simulation ID'),
    'name': fields.String(required=True, description='Simulation Name'),
    'tasks': fields.List(
        fields.Nested(ns.model(
            'Task', {
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
        fields.Nested(ns.model('Student', {
            'name': fields.String(required=True, unique=True, descripton='Student Name'),
            'skills': fields.List(fields.Nested(ns.model('Skill', {
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
        fields.Nested(ns.model('Allocation', {
            'student': fields.String(),
            'tasks': fields.List(fields.String)
    }))),
    'Z': fields.Integer(description='Optimal value found by GLPK'),
    'status': fields.String(description='Status of GLPK solution')
})

##############################
########### Routes ###########
##############################

parser = reqparse.RequestParser()
parser.add_argument('UID', location='headers', required=True)

@ns.route('')
class Simulations(Resource):
    @ns.doc(
        'Get the list of simulations', security='apikey',
        parser=parser
    )
    @ns.marshal_list_with(simulation)
    @uid_required
    @token_required
    def get(self):
        """ List of Simulations """
        return list(db.simulations.find({
            'uid': request.headers['UID']
        }))

    @ns.doc(
        'Post a new simulation', security='apikey',
        body=simulation, parser=parser
    )
    @ns.expect(simulation, validate=True)
    @ns.marshal_with(simulation)
    @token_required
    @uid_required
    def post(self):
        # Get User ID using header
        ns.payload['uid'] = request.headers['UID']

        # Call PulP GLPK solver
        retrieve = sim(ns.payload)

        ns.payload['allocations'] = []
        ns.payload['Z'] = retrieve.pop('Z')
        ns.payload['status'] = retrieve.pop('status')

        tmp = set(list(map(
            lambda x: next(item['name'] for item in ns.payload['students']
                           if unidecode(item['name']) == cleanRetrieve(x)[0]),
            retrieve.keys()
        )))

        for t in tmp:
            ns.payload['allocations'].append({ 'student' : t, 'tasks': [] })

        for key, value in retrieve.items():
            if value == 1:
                tr = cleanRetrieve(key)
                i = next(i for i, item in enumerate(ns.payload['allocations'])
                            if unidecode(item['student']) == tr[0])
                task = next(item['name'] for item in ns.payload['tasks']
                            if unidecode(item['name']) == tr[1])
                ns.payload['allocations'][i]['tasks'].append(task)

        db.simulations.insert_one(ns.payload)
        return ns.payload

@ns.route('/<string:id>')
class Simulation(Resource):
    @ns.doc(
        'Get a simulation', security='apikey',
        parser=parser
    )
    @ns.marshal_with(simulation)
    @uid_required
    @token_required
    def get(self, id):
        return db.simulations.find_one({
            'uid': request.headers['UID'], 
            '_id': ObjectId(id)
        })

def cleanRetrieve(s):
    return s[4:].replace("_", " ").split("@")
