#!/bin/bash


#
## File Handling Functions.
##   
#
outputFile="temp.sql" # Default name if not passed.

#
## OutputFile - Using a passed string Build an outputfile
##  outputFile - Global file name returned.
##	Parm #1 is the file 
##   
#
function tempFileName() {

	echo "FileName Builder"	

	if [ "$#" == 0  ]; then
		echo "#1 -- required a file name"
		exit 1
	fi

	selectQueryFile="$1"
	
	date=$(date +%F-%T)

	outputFile="${selectQueryFile%.*}$date.out"

}

#
## createDirectory - 
##  If it does not exist create it.
##	if It does exist just return
##   
#
function createDirectory() {

	echo "Create Directory"	

	if [ "$#" == 0  ]; then
		echo "#1 -- required a Directory name"
		exit 1
	fi
	
	echo "Checking for Directory:$1"

}
