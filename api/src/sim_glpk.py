'''
Optimal allocation of students into group tasks

Authors: Gabriel Luciano, Geovane Fonseca and Luigi Domenico  2019
'''
# Import PuLP modeler functions
from functools import reduce
from pulp import *
from unidecode import unidecode

def sim(documents):
    name = documents.get('name')
    students = documents.get('students')
    tasks = documents.get('tasks')

    # Create the 'prob' variable to contain the problem data
    prob = LpProblem('Optmizing Tasks - {}'.format(name), LpMaximize)

    dvars = [] # List of decision variables
    factors = [] # List of factors (competencies of each student)
    cstudents = {} # Dict of constraints (dvars) by students
    for s in students:
        cstudents[s['name']] = []

        for sk in s['skills']:
            v = '{}@{}'.format(unidecode(s['name']), unidecode(sk['task']))
            dvars.append(v)
            factors.append(sk['competency'])
            cstudents[s['name']].append(v)

    # LpDict of decision variables
    dvars_dict = LpVariable.dicts('Var', dvars, cat=LpBinary)

    # The objective function
    prob += lpSum([f * dvars_dict[v] for v, f in zip(dvars, factors)]), \
        'Allocated Members'

    # The constraints for students
    for s in students:
        prob += lpSum([dvars_dict[v] for v in cstudents[s['name']]]) >= 1, \
            'Lower bound for student {}'.format(unidecode(s['name']))

    # The constraints for tasks
    for t in tasks:
        ctask = []
        factors = []

        for s in students:
            ctask.append('{}@{}'.format(unidecode(s['name']), unidecode(t['name'])))
            factors.append(
                next(item for item in s['skills'] if item['task'] == t['name'])
                    ['competency']
            )

        prob += lpSum([f * dvars_dict[v] for v, f in zip(ctask, factors)]) >= 1, \
            'Lower bound for task {}'.format(unidecode(t['name']))
        prob += lpSum([f * dvars_dict[v] for v, f in zip(ctask, factors)]) <= t['level'], \
            'Upper bound for task {}'.format(unidecode(t['name']))

    
    # The problem data is written to an .lp file
    prob.writeLP('OptmizingTasks.lp')

    # The problem is solved using PuLP's GLPK
    prob.solve(pulp.GLPK())
    
    optimization = {} # Optimization return of the function

    # The status of the solution is printed to the screen
    #print('Status:', LpStatus[prob.status])

    # Each of the variables is printed with it's resolved optimum value
    for v in prob.variables():
        optimization[v.name] = v.varValue

    # The optimised objective function value is printed to the screen
    optimization['Z'] = value(prob.objective)

    return optimization
