# Enter "sh readme.txt" at the Linux command prompt to execute
# the following test cases in batch. Read .out files for the detailed
# results from executions.

javac MainAir.java

java MainAir ./public/Hawaiian-airports.txt ./public/Hawaiian-flights.txt ./public/Hawaiian-customers10.txt > tickets01.out
java MainAir ./public/Hawaiian-airports.txt ./public/Hawaiian-flights.txt ./public/Hawaiian-customers100.txt > tickets02.out
java MainAir ./public/Hawaiian-airports.txt ./public/Hawaiian-flights.txt ./public/Hawaiian-customers-bogus.txt > tickets03.out
java MainAir ./public/Hawaiian-airports.txt ./public/Hawaiian-flights-singles.txt ./public/Hawaiian-customers100.txt > tickets04.out

java MainAir ./public/Alaska-airports.txt ./public/Alaska-flights.txt ./public/Alaska-customers200.txt > tickets05.out
java MainAir ./public/Alaska-airports.txt ./public/Alaska-flights.txt ./public/Alaska-customers2000.txt > tickets06.out
java MainAir ./public/Alaska-airports.txt ./public/Alaska-flights.txt ./public/Alaska-customers20000.txt > tickets07.out

