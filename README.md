# Undergraduate, 679776672, cbeltr4@uic.edu, Carlos, Beltran #

# hw2 video: https://youtu.be/q4zt61kqO2c#

Example of generating coverage report for one test
where test name is TortoiseCardTest and method is setLife
```
cd JAVA-MVC-Swing-Monopoly
gradle clean test --tests "tests.TortoiseCardTest.setLife" jacocoTestReport
```

I put all generated coverage reports in the /reports directory

MapReduce implementation is in src/MapReduce.java

To generate input file, I just ran parseCoverageReports() in the Main method of my MapReduce locally once. Then commented it out. 

To build jar
```
cd JAVA-MVC-Swing-Monopoly
./gradlew jar
```

To run with hadoop (make sure output folder doesnt exist and input file is in input directory)
```
hadoop jar build/libs/JAVA-MVC-Swing-Monopoly.jar ../input/ ../output
```

