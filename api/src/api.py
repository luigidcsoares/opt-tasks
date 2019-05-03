from flask_restplus import Api

# Import api namespaces.
from . import people

api = Api(
    title='Optmizing Tasks',
    version='1.0',
    description='Optimal allocation of people into tasks'
)
api.add_namespace(people.ns)
