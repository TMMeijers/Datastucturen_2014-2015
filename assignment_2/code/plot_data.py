# -*- coding: utf-8 -*-
"""
Created on Sat Feb 14 19:46:18 2015

@author: markus
"""

import os
import csv
from matplotlib import pyplot as plt
import numpy as np

#%%

methods = ['LinearProbing', 'QuadraticProbing', 'CollisionChaining']

build_times = {}
run_times = {}
loads = {}

for m in methods:
    build_times[m] = {}
    run_times[m] = {}
    loads[m] = {}

def get_iteration_and_hash_size(out_file):
    parts = out_file.split('_')
    return (int(parts[1]), int(parts[2].split('.')[0]))

iterations = []
exponents = []
for dirname, dirnames, filenames in os.walk('output/'):
    for out_file in filenames:

        parts = out_file.split('.')
        if parts[-1] != 'txt':
            continue
        whole_path = dirname + out_file
        (iteration, exponent) = get_iteration_and_hash_size(out_file)
        if iteration not in iterations:
            iterations.append(iteration)
        if exponent not in exponents:
            exponents.append(exponent)
        #max_iteration = max(iteration, max_iteration)
        with open(whole_path) as in_file:
            print 'parse: ' + out_file
            file_read = csv.reader(in_file, delimiter=',')
            for (i, row) in enumerate(file_read):
                #print row
                if not i: 
                    continue
                method = row[0]
                build_time = int(row[1])
                run_time = int(row[2])
                load = float(row[3])
                #if method not in methods:
                #    continue
                
                if exponent not in build_times[method]:
                    build_times[method][exponent] = []
                build_times[method][exponent].append(build_time)
                
                if exponent not in run_times[method]:
                    run_times[method][exponent] = []
                run_times[method][exponent].append(run_time)
                
                if exponent not in loads[method]:
                    loads[method][exponent] = []
                loads[method][exponent].append(load)
#%%
    
for m in methods:
    accum = 0
    for e in exponents:
       run_times[m][e] = sum(run_times[m][e]) / float(len(run_times[m][e]))
       build_times[m][e] = sum(build_times[m][e]) / float(len(build_times[m][e]))
       loads[m][e] = sum(loads[m][e]) / float(len(loads[m][e]))

    print run_times[m]

    run_times[m] = np.matrix(sorted([[k, v] for k, v in run_times[m].iteritems()]))
#    run_times[m].sort(axis=0)
    build_times[m] = np.matrix(sorted([[k, v] for k, v in build_times[m].iteritems()]))
#    build_times[m].sort(axis=0)
    loads[m] = np.matrix(sorted([[k, v] for k, v in loads[m].iteritems()]))

#%%           
xs = range(0, len(np.array(run_times[m][0:,0]).flatten()))
x_ticks = sorted([int(np.log(e)/np.log(2)) for e in exponents])
              
           
#%%
    
#%matplotlib inline
    
# plot run times
for m in methods:
    plt.plot(xs, np.array(run_times[m][0:,1]).flatten())
plt.xticks(range(len(x_ticks)), x_ticks, size="small")#, rotation='vertical')
plt.title('Hash Size vs. Runtime')
plt.xlabel('initial hash size ($log_2$)')
plt.ylabel('runtime in ms')
plt.legend(methods)
plt.savefig('figs/runtime_all.png')
#plt.show()
plt.clf()

for m in ['LinearProbing', 'QuadraticProbing']:
    plt.plot(xs, np.array(run_times[m][0:,1]).flatten())
plt.xticks(range(len(x_ticks)), x_ticks, size="small")#, rotation='vertical')
plt.title('Hash Size vs. Runtime')
plt.xlabel('initial hash size ($log_2$)')
plt.ylabel('runtime in ms')
plt.legend(['LinearProbing', 'QuadraticProbing'])
plt.savefig('figs/runtime_probing.png')
plt.clf()
#plt.show()

#%%
    
#%matplotlib inline
    
# plot build times
for m in methods:
    plt.plot(xs, np.array(build_times[m][0:,1]).flatten())
plt.xticks(range(len(x_ticks)), x_ticks, size="small")#, rotation='vertical')
plt.title('Hash Size vs. Build Time')
plt.xlabel('initial hash size ($log_2$)')
plt.ylabel('runtime in ms')
plt.legend(methods)
plt.savefig('figs/buildtime_all.png')
plt.clf()
#plt.show()

#%%
    
#%matplotlib inline
    
# plot loads

plt.plot(xs, np.array(loads['LinearProbing'][0:,1]).flatten())
plt.xticks(range(len(x_ticks)), x_ticks, size="small")#, rotation='vertical')
plt.title('Hash Size vs. Loads')
plt.xlabel('initial hash size ($log_2$)')
plt.ylabel('runtime in ms')
plt.legend('Linear/Quadratic Probing')
plt.savefig('figs/loads_probing.png')
plt.clf()
#plt.show()

plt.plot(xs, np.array(loads['CollisionChaining'][0:,1]).flatten())
plt.xticks(range(len(x_ticks)), x_ticks, size="small")#, rotation='vertical')
plt.title('Hash Size vs. Loads')
plt.xlabel('initial hash size ($log_2$)')
plt.ylabel('runtime in ms')
plt.legend('CollisionChaining')
plt.savefig('figs/loads_chaining.png')
plt.clf()
#plt.show()