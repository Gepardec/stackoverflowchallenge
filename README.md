# Warning
This README.rd contains only technical and licencing related issues and will not describe the application itself and its features! For an indepth description of the app itself, please read our wiki entry.  
Thank you!  

# Development

## Postgres in Container
If you are on a SELinux you need to give Permissions to the <path_to_mount_folder>
chcon -Rt svirt_sandbox_file_t <path_to_mount_folder>
podman run -d --name so-challenge-postgres -e POSTGRES_USER=so-challenge -e POSTGRES_PASSWORD=so-challenge -e POSTGRES_DB=so-challenge -p 5432:5432 -v <path_to_mount_folder>:/var/lib/postgresql/data docker.io/postgres:11.3

## Development
Initial build with "mvn clean compile -Pbuild-angular-app" at parent folder

To start development start the following, both commands are blocking:
1. In so-challenge-frontend: mvn quarkus:dev
2. In so-challenge-frontend: mvn validate -P ng-watch 

Happy Coding :-)

## Native build

To create a native executable run the following at parent folder:

mvn clean package -Pbuild-angular-app,native

## Multistage Image Build Build

To create a container image with the native run the following command at parent folder

mvn -N exec:exec -Pbuild-image

grab a coffee it takes a while -> about 6 minutes

# Installation Old
To installand run this application on your computer, check if you fulfill following prerequisites:
1. Install JavaSE 11 and JavaEE 8, IntelliJ Ultimate Edition, Angular, node.js and NPM.
2. Make sure that you have a PostgreSQL database running on localhost:5432 with the name 'so-challenge'.
   The owner of the database should be 'postgres' with the password 'pgadminplaysdart31'
     
   Note: this is not a production release, so passwords and other private pieces of information can be public for now.
   
3. Install WildFly 17.0.1 JavaEE application server.

4. After that, try to emphasize this package structure:
   ```
   stackoverflowchallenge
          |
           --- git (your repo clone comes here)
               |
                --- so-challenge/ (parent-pom)
                    |
                     --- so-challenge-backend/(child-pom)
                         so-challenge-frontend/(child-pom)
                         .gitignore
                         README.md
                         pom.xml
          | 
           --- wildfly (your unzipped wildfly installation, that we will use later on)
           
          |
           --- workspace (.idea directory that is created by IntelliJ automatically --> it's annoying so we put it there)
   ```        

# Setup
1. Open IntelliJ Ultimate Edition 
2. Click on ```File > New > Module from Existing Sources...```
3. Double click on the parent pom.xml which is under the path: ```stackoverflowchallenge/git/so-challenge/pom.xml```
4. If IntelliJ asks you to choose your workspace folder, just choose ```stackoverflow/workspace``` for that
5. Next click on edit configuration on the top right dropdown (next to the green 'play' arrow)
6. A popup will open; click on the ```+``` sign 
7. A list saying add new configuration will appear
   In this list, search for JBoss server and choose 'Local'
8. After that pass in your path to the WildFly server and add 2 artifacts.
   Choose the exploded frontend and exploded backend.
9. Hit OK, click on the green 'play' button and you are good to go.

# Explanation of the code
We have 3 maven modules:  
      parent  
      backend  
      frontend  
      
The frontend is built with maven frontend plugin (link in wiki)

The backend code is using JPA for OR mapping.
It is using a @Stateless DAO to perform CRUD operations to our entity classes.
When creating an endpoint, just take a sample from an existing one, and put it inside the ```ApplicationConfig.java``` class!

The frontend is using Angular Material and consists of several components.
With the HttpClientModule and HttpClient class we make HTTP calls to the backend (GET, PUT, POST, DELETE). 

There are several endpoints such as challenges/all or participants/add, read trough them to understand what they are doing.
The calls are implemented in a service and the subscribtion to the Observable<T> happens in the caller.

# Things that are working (branch 8_XXX)
1. Searching stackoverflow users by their id and adding them to our own database (global list) with profile ID,name,image and    profile URL.
   deleting participants
2. adding tags to a gobal list by their name.
   deleting tags
3. SnackBar user feedback --> material design
4. showing a list of all challenges and delete them

We store StackOverflow data in our own database to be resource friendly with the limited amount of requests one can perform on the StackExchange API.

# Things that are partly working
1. Editing challenges works partly:
   Adding/Deleting Tags and Users to/from a challenge is not implemented yet
2. however updating fields like status, title, date, etc... works fine
3. Adding challenges works partly.
   
# Things that are not implemented yet
1. Showing the progression graph of the participants of a challenge
2. RBAC (admin vs. read-only)
3. The data model/entity classes must be changed.
   since the tracking begins by the date when a user joins a challenge
   the n:n table challenge_participant needs a new column 'joining date'.
   this has to be translated into JPA.
   right now the join table has no attributes, so a simple @ManyToMany annotation with a Collection is enough,
   however, this will have to change.
4. the format of the datepickers must be changed to yyyy/MM/dd

# Contribute
Everybody is welcome and can contribute to this project, since it is free software.
You can study, modify and dristribute the source code as you wish.
