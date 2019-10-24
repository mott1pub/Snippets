#!/bin/bash

#
## parse a file lookingfor things..
##   
#

if [ $# == 0 ]; then 
	echo "Usage: $0 filenames.txt(Required) FileServer(Optional)"
	exit 1
fi

source fileFunctions.sh


function parmsEdit() 
{
	echo "parmsEdit"
	
	if [ $# -gt 3 ]; then
		echo "#Optional Parms > 2. See Usage!"
		exit 1
	fi

	#Check if Env Passed
	if [ ! -z "$2"  ]; then
		dbEnv "$2"
	fi
}

##
## Main 
##

if [ ! -f "$1" ]; then
	echo "#1 -- no such File!"
	exit 1
fi

parmsEdit "$@"

tempFileName "$1" 

echo "Output Captured to:" $outputFile

echo "Reading File to build Parms:$tempFileName"

awk -v outfile="$outputFile" 'BEGIN { FS=" ";}  
/from/ { printf $2 "\n" > outfile; }
END { print "From!"}' $1


awk -v outfile="$outputFile" 'BEGIN { FS=" ";}  
/left/ && /outer/ && /join/ { printf $4 "\n" >> outfile; }
END { print "From!"}' $1


echo "Process competed at:$(date +%F-%T)"

## End of Main
