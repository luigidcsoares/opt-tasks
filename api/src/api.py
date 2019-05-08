from flask_restplus import Api

# Import api namespaces.
from . import auth, simulations, users

authorizations = {
    'apikey': {
        'type': 'apiKey',
        'in': 'header',
        'name': 'X-API-KEY'
    }
}

api = Api(
    title='Optmizing Tasks',
    version='1.0',
    authorizations=authorizations,
    description='Optimal allocation of students into group tasks'
)
api.add_namespace(auth.ns)
api.add_namespace(simulations.ns)
api.add_namespace(users.ns)
