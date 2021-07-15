# Website Downloader

### Problem Statement: 
Create a console program in Java that can recursively traverse and download www.tretton37.com and
save it to disk while keeping the online file structure. Use asynchronicity and parallelism as
much as possible, and show progress in the console. Don’t focus on html link extraction, keep that
part simple (regex or similar). The focus of the assignment is on asynchronicity, parallelism and threading.

#### Solving base on multiple threads solution:
My solution get url and depth and try to create multiple threads to download the url concurrently.
To providing threads I use ExecutorService and via DownloadList object we can track paths, which path downloaded or not downloaded yet.

#### How to run:
To run you need to maven and you should run this command first:
```
mvn clean compile assembly:single
```

And after building jar file you can execute it :

```
java -jar target\tretton37-websiteDownloader-1.0-SNAPSHOT-jar-with-dependencies.jar
```

without parameters, it tries to download https://tretton37.com/ up to depth=3, but you can set parameter like this 
```
java -jar target\tretton37-websiteDownloader-1.0-SNAPSHOT-jar-with-dependencies.jar https://www.google.com/ 1
```
