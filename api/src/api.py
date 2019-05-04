from flask_restplus import Api

# Import api namespaces.
from . import simulations

api = Api(
    title='Optmizing Tasks',
    version='1.0',
    description='Optimal allocation of students into group tasks'
)
api.add_namespace(simulations.ns)
