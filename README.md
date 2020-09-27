# Photo Renamer
An application for easily renaming image files based on tags.

## Features
* The user can view the image before renaming
* A log is created of all renaming done
* Tags persist after application is closed

## To run
If you remove the [photo_renamer/TagsTest.java](https://github.com/noraabdelgadir/Photo-Renamer/blob/master/photo_renamer/TagsTest.java) file, then it will compile without JUnit being installed. But if you want to run the tests, JUnit must be installed for the project to compile. See [here](https://junit.org/junit4/faq.html#started_2) for instructions on how to download.

* Compile project by running:
  ```
  javac photo_renamer/*.java
  ```
* Run
  ```
  java photo_renamer/PhotoRenamer
  ```
