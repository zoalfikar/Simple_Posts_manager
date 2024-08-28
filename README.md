# Simple Posts Manager API

Gradle version 8.5

JAVA version 17.0.6

## Integrate API

**Git clone https://github.com/zoalfikar/simple\_posts\_manager.git**

**Cd simple\_posts\_manager**

**Gradle build**   or   **.\\ gradlew build**

## Setup

#### Connecting with Mysql

Run Mysql server **net start mysql**   or   **net start mysql80** according to your Mysql version **Create new database .**

start **redis-server** for caching

Setup the **application.properties**

**gradle :utilities:run --args=migrate**    or     **gradle :utilities:run --args=migrate:refresh**   
or set **spring.jpa.hibernate.ddl-auto=update**


to Create a migration Go to utilities\\src\\main\\java\\migrations\\tables add file {migration\_name}.migration .

## start the application

**Gradle bootrun**

## Usage

**curl http://localhost:7788**

{message:Welcome in simple post manager"}

## APIs

#### Auth

post **api/auth/signup**      request {firstName ,lastName , email .password }     register a new user

post **api/auth/login**     request {email ,password }     response {token}      token type is bearer token

- - -

#### Post

get **api/posts**      retrieve all user posts

get **api/all/posts**     retrieve all saved posts

get **api/posts/{postId}**     retrieve certen post

get **api/posts/cache/{postId}**     retrieve post from cache

post **api/posts**      request {content : string}     add new post

post **api/posts/with-image**     request {content : string , image :file}      add new post with image ,and conent-type is multipart/form-data

put **api/posts/{postId}**      request {content : string}      update post content

put **api/posts/{postId}/image**     request {image :file}      update post image and conent-type is multipart/form-data

delete **api/posts/{postId}**      delete post

- - -

#### Like

post **api/likes**      add like to post , request {postId :number}

delete **api/likes/{likeId}**      remove like from post

- - -

#### Comment

post **/api/Comments**      add comment to a post

request {content : string , postId:number}

put **/api/Comments/commentId**     update comment content

request {content : string , postId:number}

delete **/api/Comments/commentId**     remove comment from post