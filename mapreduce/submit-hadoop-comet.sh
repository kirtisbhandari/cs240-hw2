#!/bin/bash
#SBATCH --job-name="wordcount"
#SBATCH --output="wordcount.%j.%N.out"
#SBATCH --partition=compute
#SBATCH --nodes=1
#SBATCH --ntasks-per-node=24
#SBATCH -t 00:15:00
#SBATCH -A csb159

export HADOOP_CONF_DIR=/home/$USER/cometcluster
export WORKDIR=`pwd`
module load hadoop/1.2.1
#module load hadoop/2.6.0
myhadoop-configure.sh
start-all.sh
hadoop dfs -mkdir input

#copy data
#hadoop dfs -copyFromLocal $WORKDIR/SINGLE.TXT input/
#hadoop jar $WORKDIR/AnagramJob.jar input/SINGLE.TXT output

hadoop dfs -copyFromLocal $WORKDIR/test.txt input/
#hadoop jar $WORKDIR/AnagramJob.jar /user/$USER/input/SINGLE.TXT /user/$USER/output
hadoop jar $WORKDIR/wordcount.jar  wordcount input/ output/


#fail if the directory does not exist. rm -r $WORKDIR/WC-output
#the folllowing command does not report error even the file does not exist

rm -rf WC-out >/dev/null || true
mkdir -p WC-out
#hadoop dfs -copyToLocal output/part* $WORKDIR
hadoop dfs -copyToLocal output/part* WC-out

#hadoop dfs -get /user/$USER/output/part* $WORKDIR/
#hadoop dfs -copyToLocal output/ WC-output
#hadoop dfs -copyToLocal output/part* $WORKDIR

stop-all.sh
myhadoop-cleanup.sh
