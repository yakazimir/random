## a mini NL interpreter written in a functional style.
##
### run by typing ``python lecture2.py'' on the command line 

## example sets 

students_studying = set(["john","mary"])
students_sleeping = set(["bill","francis"])

## interpretation rules as anonymous functions  

## set operations

study = lambda x : (x in students_studying)
sleep = lambda x : (x in students_sleeping)

## negation

neg = lambda F : (lambda x : not F(x)) ## returns a negated anonymous function

## disjunction ``or'' -- implemented as a binary relation 

log_or = lambda x,y : (lambda  F : F(x) or F(y))

## conjunction ``and'' -- implemented as a binary relation 

log_and = lambda x,y : (lambda F : F(x) and F(y))


## function application

## takes a function and val and returns the function aplied to this value 
fun_application = lambda fun, val : fun(val)


## this gets called when you run python lecture2.py

if __name__ == "__main__":

    ## execute ``John studies''
    print "John studies: %s" % fun_application(study,"john")

    ## execute ``Bill studies''
    print "Bill studies: %s" % fun_application(study,"bill")

    ## execute ``Mary sleeps''
    print "Mary sleeps: %s" % fun_application(sleep,"mary")

    ## execute ``Bill does not studying''
    print "Bill does not study: %s"  %\
       fun_application(fun_application(neg,study),"bill")

    print "john does not sleep: %s"  %\
       fun_application(fun_application(neg,sleep),"john")

    ## execute ``it's not the case that bill is not sleeping
    print "it is not the case that bill is not sleeping: %s" %\
        fun_application(fun_application(neg,fun_application(neg,sleep)),"bill")

    ## execute ``(either) john or bill is studying
    print "either john or bill is studying: %s" %\
        fun_application(log_or("john","bill"),study)

    print "bill and john are studying: %s" %\
      fun_application(log_and("john","bill"),study)

