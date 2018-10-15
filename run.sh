kill -9 $(lsof -i:1099 -t) 2> /dev/null 
cd ../Driver/target/classes && java sync.Scheduler $2 $3 & (zunit ./tests/$1.bats --verbose && kill -9 $(lsof -i:1099 -t) 2> /dev/null)

