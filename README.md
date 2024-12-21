# 4413Project

This README is also available on [Github](https://github.com/EthanRei/4413Project)\
The Frontend react source code can be found under the FRONTEND folder.\

# Running the project

### Through a deployed container (Easiest method)
The server is running on google cloud run on the following [link](https://image02-741152878318.us-central1.run.app) during time of writing.\
Note that there will most likely be a cold start on the container on first access which may take a few seconds.\
A postman collection for requests to this server can be imported and are found under `postman/hosted.postman_collection`.

### Pulling image from dockerhub (Requires Docker installed)
Alternatively, you can pull the image through `docker pull polejimyahoo/project4413:02` [Docker Hub Repo](https://hub.docker.com/layers/polejimyahoo/project4413/02/images/sha256-12ca4209a94ae101e0ee60ea5073f81680ac6901290859a4f0a022febcfcd9fb)\
The command used to run the image is `docker run -p 8080:8080 polejimyahoo/project4413:02` will run the server on local machine\
A postman collection for requests to your localmachine can be imported and are found under `postman/localhost.postman_collection`.


### Through running the jar file (Requires Java 17+ installed)
Within this directory, run `java -jar Project4413-0.0.1-SNAPSHOT.jar` to run the server on local machine\
A postman collection for requests to your localmachine can be imported and are found under `postman/localhost.postman_collection`.


# Building the project from scratch

### 1: Integrating the front-end with springboot
- install node js
- navigate to the FRONTEND directory
- run npm install
- run npm run build
- copy all files in the generated build folder in the FRONTEND directory to the springboot static folder
- remove any instance of `/static/` from the index.html file
- move index.html, asset-manifest.json, and manifest.json inside the static folder

### 2: Running the project locally
- Have java and jdk installed (atleast version 17 but worked on 23)
- Have maven installed
- Run `mvn install`
- Run the jar file in the target folder through `java -jar target/Project4413-0.0.1-SNAPSHOT.jar`

### Admin credentials
User: admin\
Pass: 123
