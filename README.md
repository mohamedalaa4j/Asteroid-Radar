# FOR API KEY

Go to this directory com.udacity.asteroidradar.utilities.constants
Add your api key in the API_KEY const variable

## Gradle errors
Due to gradle build errors and compatibility issues like
"Kotlin runtime JAR files in the classpath should have the same version"
I have to upgrade ext.kotlin_version to '1.6.0' in build.gradle (project level)
with the help of related topic
https://stackoverflow.com/questions/42569445/warning-kotlin-runtime-jar-files-in-the-classpath-should-have-the-same-version
&
https://stackoverflow.com/questions/67699823/module-was-compiled-with-an-incompatible-version-of-kotlin-the-binary-version-o


## Room Database errors
Due to "Type of the parameter must be a class annotated with @Entity" error
I had to add those dependencies in the gradle (Module level)
    kapt "androidx.room:room-compiler:2.5.0-rc01"
    annotationProcessor "androidx.room:room-compiler:2.5.0-rc01"
with the help of related topic
https://stackoverflow.com/questions/48015280/type-of-the-parameter-must-be-a-class-annotated-with-entity-while-creating-ge

## Getting next days dates
Inspired with the help of
https://stackoverflow.com/questions/22195093/android-how-to-get-tomorrows-date

## SQL Query
For filtering data from dateBase I've figured out SQl queries with the help of those
https://www.w3schools.com/sql/sql_between.asp
https://www.tutorialsteacher.com/sql/sql-orderby
https://www.educba.com/sql-order-by-date/