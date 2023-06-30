import os.path, subprocess
import random
from subprocess import STDOUT, PIPE
import numpy as np
import matplotlib.pyplot as plt
import time


def compile_java(java_file):
    subprocess.check_call(['javac', java_file])


def execute_java(java_file, file_num):
    inp_f = open("input/input" + str(file_num) + ".txt", "r")
    stdin = bytes(inp_f.read(), 'utf-8')
    inp_f.close()

    start_time = time.time()

    java_class, ext = os.path.splitext(java_file)
    cmd = ['java', java_class]
    proc = subprocess.Popen(cmd, stdin=PIPE, stdout=PIPE, stderr=STDOUT)
    stdout, stderr = proc.communicate(stdin)
    out_f = open("output/output" + str(file_num) + ".txt", "w")
    b = bytes.decode(stdout, 'utf-8')

    end_time = time.time()

    out_f.write(b)
    out_f.close()

    return end_time - start_time


def create_random_input(file_num, n):
    if n % 2 == 1:
        n += 1
    inp_f = open("input/input" + str(file_num) + ".txt", "w")
    inp_f.write(str(n) + "\n")
    for i in range(n // 2):
        inp_f.write("add " + str(random.randint(1, 100)) + "\n")
        inp_f.write("find " + str(random.randint(1, 100)) + "\n")
    inp_f.close()


def create_gaussian_input(file_num, n):
    if n % 2 == 1:
        n += 1
    mean = 50
    variance = 25
    samples = np.random.normal(mean, np.sqrt(variance), n)
    inp_f = open("input/input" + str(file_num) + ".txt", "w")
    inp_f.write(str(n) + "\n")
    for i in range(n // 2):
        inp_f.write("add " + str(int(samples[i * 2])) + "\n")
        inp_f.write("find " + str(int(samples[i * 2 + 1])) + "\n")
    inp_f.close()


compile_java('Main.java')

num = 1.5 * 1.5 * 1.5
base = 1.5

random_sizes = []
random_execs = []

gaussian_sizes = []
gaussian_execs = []

for i in range(30):
    num *= base
    size = int(num)


    create_random_input(i + 1, size)
    time_exec = execute_java('Main.java', i + 1)
    random_sizes.append(size)
    random_execs.append(time_exec)

    create_gaussian_input(i + 31, size)
    time_exec = execute_java('Main.java', i + 31)
    gaussian_sizes.append(size)
    gaussian_execs.append(time_exec)

fig, ax = plt.subplots(figsize=(10, 5))
ax.plot(random_sizes, random_execs)
ax.plot(gaussian_sizes, gaussian_execs)
plt.show()