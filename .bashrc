# .bashrc

# If not running interactively, don't do anything
[[ $- == *i* ]] || return

# Source global definitions
if [ -f /etc/bashrc ]; then
	. /etc/bashrc
fi
if [ -f ./etc/bash_completion ]; then
	./etc/bash_completion
fi

# User specific aliases and functions
## Set color prompt..
PS1='\[\033[1;36m\]\u\[\033[1;31m\]@\[\033[1;32m\]\h:\[\033[1;35m\]\w\[\033[1;31m\]\$\[\033[0m\] '

# *Note:run the following as source
source $(id -u -n)
