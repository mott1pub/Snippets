#!/bin/bash

#
## dbEnv - sets which DB you are connecting To
##  Parm #1 is the Environment to check.. 
#

env="UR_PP" ## Default to Preprod..
	# Valid values are UR_PN UR_PP UR_QA 

function dbEnv() 
{
	echo "Default DB2 Database:PP(PreProd)"	

	if [ "$#" == 0  ]; then
		echo "#1 -- required Environment"
		exit 1
	fi
	
	case $1 in 
	["qQ"] | ["qQ"]["aA"]) 
		env="UR_QA"
		;;
	["nN"] | ["pP"]["nN"]) 
		env="UR_PN"
		;;
	["pP"] | ["pP"]["rR"]["eE"]["pP"])
		env="UR_PP"
		;;
	["mM"] | ["mM"]["oO"]["cC"]["kK"])
		env="UR_MOCK"
		;;
	["lL] | ["lL"]["oO"]["gG"]["pP"])
		env="logpp"
	["lLpPpP"] | ["lL"]["oO"]["gG"][pPpP")
		env="logpn"
	esac
	echo "Using DB2: $env"
} 

#
## ConnectToDB2
##  Parm required Alias of Environment
#
function ConnectToDB2() 
{

	if [ "$#" == 0  ]; then
		echo "#1 -- required Environment"
		exit 1
	fi

	echo connecting to "$1"
	db2 connect to $1 user "urrep" using "tjU3$>m"

}


#
## DB2 Execute Select
##  Parm Environment	(Required)
##  Parm SelectStatementFile (Required)
##  Parm outputFromSelectFile (Required)
## *Note: This query requiest the Selectatement to be in a File
#
function QueryDb2() 
{
	if [ "$#" == 0  ]; then
		echo "#1 -- Missing required Select File & Results File!"
		exit 1
	fi

	if [ -z "$1"  ]; then
		echo "#1 -- Missing required Select File Name!"
		exit 1
	fi

	if [ -z "$2"  ]; then
		echo "#2 -- Missing required Results File Name!"
		exit 1
	fi

	echo "Outputing to File:$2" 
	db2 -tf $1 > $2

}

#
## connectReset
#
function connectionReset()
{

	db2 connect reset;

}
