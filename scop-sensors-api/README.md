# server-api project

## Importing a project into Eclipse
Clone the source to a directory of your choice in a git repository
    ````
    git clone https://xxxx.com/xxx.git
    ````
2. make an eclipse project.
   ````
   $ cd ${clone directory}/server-api
   $ . /gradlew eclipse
   ````
3. import to eclipse
    Open Eclipse.
    2. File>Import>Existing Projects into Workspace
    Select ${clone directory}/server-api and Finish

4. doma settings  
An error occurs in the doma configuration, so do the following  
    1. right-click on the server-api project > Build Path > Build Path Configuration Build Bath
    2. open the Java Build Path>Source tab>server-api/.apt Input _generated Output folder to Edit->bin/main and press Reflection.

    Reference  
    http://doma.seasar.org/faq.html#development -environment-5

## How to develop
Modify the source code.
Open jp.co.scop.
3. right-click > Run As > Spring Boot App
4. check the operation in a browser or curl
5. perform source redaction (back to 1)

## How to use Doma2
By introducing the Doma plug-in, it is possible to create a DAO as shown below.
* Reference.  
https://doma.readthedocs.io/en/stable/ getting-started-eclipse/#import-template- project

* Procedure.
    Creating Dao Interface
        ````
        @ConfigAutowireable
        @Dao
        public interface XXXXDao {
        }
        ````
    1. annotate @Select, @Insert, @Update, and @Delete. Create a method that you attach to
        ````
        @Select
        List<Company> selectAll();
        ````
    2. right-click on the annotation point in Eclipse > Doma > Annotate Jump to SQL File
    3. enter a file name (usually the default) and save it
    4. customize the SQL file.


Translated with www.DeepL.com/Translator (free version)
