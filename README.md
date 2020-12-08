# Package Delivery

## How to run
1. Install Java 8 and Maven
2. Go to the root directory in command line
3. `mvn compile`
4. Run using `mvn exec:java -Dexec.mainClass="com.test.Main" -Dexec.args="input.txt"` where the the argument is a file containing test data like in the example file input.txt
5. To run tests run `mvn test`